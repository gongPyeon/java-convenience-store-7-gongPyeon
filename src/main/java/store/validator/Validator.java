package store.validator;

import camp.nextstep.edu.missionutils.DateTimes;
import store.common.constant.ValidConstatns;
import store.domain.Product;
import store.domain.Promotions;
import store.repository.CSProductRepository;
import store.repository.ProductRepository;
import store.repository.PromotionProductRepository;
import store.repository.PromotionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class Validator {

    private static final String ERROR_PREFIX = "[ERROR] ";

    public void validateIsNull(Optional optional) {
        if(optional.isEmpty()){
            // null exception
            throw new IllegalArgumentException(ERROR_PREFIX + "객체가 생성되지 않았습니다.");
        }
    }

    public void validateFormat(String string) {
        if (!string.matches(ValidConstatns.VALID_INPUT_PATTERN)) {
            throw new IllegalArgumentException(ERROR_PREFIX + "올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요");
        }
    }

    public void validateProduct(List<Product> products, ProductRepository productRepository) {
        productRepository.print();
        for(int i=0; i<products.size(); i++){
            if(productRepository.findByName(products.get(i).getName()) == null)
                throw new IllegalArgumentException(ERROR_PREFIX + "존재하지 않는 상품입니다. 다시 입력해 주세요");
        }
    }

    public void validateProductNum(List<Product> products,
                                   ProductRepository productRepository,
                                   PromotionProductRepository promotionProductRepository) {
        for(int i=0; i<products.size(); i++){
            int totalQuantity = productRepository.findQuantityByName(products.get(i).getName())
                    + promotionProductRepository.findQuantityByName(products.get(i).getName());
            if(totalQuantity < products.get(i).getQuantity())
                throw new IllegalArgumentException(ERROR_PREFIX + "상품 수량이 부족합니다. 다시 입력해 주세요");
        }
    }

    public boolean validatePromotion(Product product, PromotionRepository promotionRepository) {
        Promotions promotion = promotionRepository.findByName(product.getName());
        if(promotion == null)
            return false;

        if(!validatePromotionDate(promotion, promotionRepository))
            return false;
        return true;
    }

    public boolean validatePromotionDate(Promotions promotion, PromotionRepository promotionRepository) {
        LocalDate currentDate = DateTimes.now().toLocalDate();
        System.out.println(currentDate);

        LocalDate startDate = LocalDate.parse(promotion.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endDate = LocalDate.parse(promotion.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        if(startDate.isBefore(currentDate) || endDate.isBefore(currentDate))
            return false;

        return true; // 행사기간 내 존재
    }

}
