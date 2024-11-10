package store.validator;

import camp.nextstep.edu.missionutils.DateTimes;
import store.common.format.Format;
import store.domain.Product;
import store.domain.Promotions;
import store.exception.ErrorMessage;
import store.repository.ProductRepository;
import store.repository.PromotionProductRepository;
import store.repository.PromotionRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

public class Validator {

    public void validateIsNull(Object object) {
        if(object == null){
            throw ErrorMessage.OBJECT_NOT_CREATED.getException();
        }
    }

    public void validateFormat(String string) {
        if (!string.matches(Format.VALID_INPUT_PATTERN)) {
            throw ErrorMessage.INVALID_FORMAT.getException();
        }
    }

    public void validateProduct(Map<Product, Promotions> products, ProductRepository productRepository,  PromotionProductRepository promotionProductRepository) {
        for (Product product : products.keySet()) {
            if (productRepository.findByName(product.getName()) == null && promotionProductRepository.findByName(product.getName()) == null) {
                throw ErrorMessage.PRODUCT_NOT_FOUND.getException();
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
               throw ErrorMessage.EXCEED_STOCK.getException();
        }
    }

    public boolean validatePromotion(Promotions promotion) {
        if(promotion == null)
            return false;

        if(!validatePromotionDate(promotion))
            return false;
        return true;
    }

    public boolean validatePromotionDate(Promotions promotion) {
        LocalDate currentDate = DateTimes.now().toLocalDate();

        LocalDate startDate = LocalDate.parse(promotion.getStartDate(), DateTimeFormatter.ofPattern(Format.LOCAL_DATE));
        LocalDate endDate = LocalDate.parse(promotion.getStartDate(), DateTimeFormatter.ofPattern(Format.LOCAL_DATE));

        if(startDate.isBefore(currentDate) || endDate.isAfter(currentDate))
            return false;

        return true; // 행사기간 내 존재
    }

    public void validateResponseFormat(String response) {
        if(!(response.matches(Format.YES) || response.matches(Format.NO))){
            throw ErrorMessage.INVALID_INPUT.getException();
        }
    }
}
