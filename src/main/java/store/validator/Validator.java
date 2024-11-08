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
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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

    public void validateProduct(Map<Product, Integer> products, ProductRepository productRepository) {
        productRepository.print();
        for (Product product : products.keySet()) {
            if (productRepository.findByName(product.getName()) == null) {
                throw new IllegalArgumentException(ERROR_PREFIX + "존재하지 않는 상품입니다. 다시 입력해 주세요");
            }
        }
    }

    public void validateProductNum(Map<Product, Integer> products,
                                   ProductRepository productRepository,
                                   PromotionProductRepository promotionProductRepository) {
        for(Product product : products.keySet()){
            int totalQuantity = productRepository.findQuantityByName(product.getName())
                    + promotionProductRepository.findQuantityByName(product.getName());
            if(totalQuantity < product.getQuantity())
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

    public void validateResponseFormat(String response) {
        if(!response.matches("Y") || !response.matches("N")){
            throw new IllegalArgumentException(ERROR_PREFIX + "응답 형식이 일치하지 않습니다. 다시 입력해주세요.");
        }
    }
}
