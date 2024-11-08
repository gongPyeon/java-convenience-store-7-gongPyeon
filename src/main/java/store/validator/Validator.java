package store.validator;

import store.common.constant.ValidConstatns;
import store.domain.Product;
import store.repository.CSProductRepository;
import store.repository.ProductRepository;

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
}
