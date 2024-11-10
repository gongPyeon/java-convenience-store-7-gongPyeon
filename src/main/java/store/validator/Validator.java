package store.validator;

import camp.nextstep.edu.missionutils.DateTimes;
import store.common.format.Format;
import store.domain.Product;
import store.domain.Promotions;
import store.repository.ProductRepository;
import store.repository.PromotionProductRepository;
import store.repository.PromotionRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

public class Validator {

    private static final String ERROR_PREFIX = "[ERROR] ";

    public void validateIsNull(Object object) {
        if(object == null){
            throw new IllegalArgumentException(ERROR_PREFIX + "객체가 생성되지 않았습니다.");
        }
    }

    public void validateFormat(String string) {
        if (!string.matches(Format.VALID_INPUT_PATTERN)) {
            throw new IllegalArgumentException(ERROR_PREFIX + "올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    public void validateProduct(Map<Product, Promotions> products, ProductRepository productRepository,  PromotionProductRepository promotionProductRepository) {
//        productRepository.print();
        for (Product product : products.keySet()) {
            if (productRepository.findByName(product.getName()) == null && promotionProductRepository.findByName(product.getName()) == null) {
                throw new IllegalArgumentException(ERROR_PREFIX + "존재하지 않는 상품입니다. 다시 입력해 주세요.");
            }
        }
    }

    public void validateProductNum(Map<Product, Promotions> products,
                                   ProductRepository productRepository,
                                   PromotionProductRepository promotionProductRepository) {
        for(Product product : products.keySet()){
            int totalQuantity = productRepository.findQuantityByName(product.getName())
                    + promotionProductRepository.findQuantityByName(product.getName());
            if(totalQuantity < product.getQuantity())
                throw new IllegalArgumentException(ERROR_PREFIX + "재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
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

    public void validateResponseFormat(String response) {
        if(!(response.matches("Y") || response.matches("N"))){
            throw new IllegalArgumentException(ERROR_PREFIX + "응답 형식이 일치하지 않습니다. 다시 입력해주세요.");
        }
    }

    public boolean checkIsNull(Optional optional) {
        if(optional.isEmpty()){
            return true;
        }
        return false;
    }

    public void validatePromotionStock(int userQuantity, int promotionStock) {
        if(userQuantity > promotionStock){
            throw new IllegalArgumentException(ERROR_PREFIX + "재고 상품이 부족합니다. 다시 입력해주세요.");
        }
    }
}
