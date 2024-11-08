package store.validator;

import store.domain.Product;

import java.util.Optional;

public class Validator {

    private static final String ERROR_PREFIX = "[ERROR] ";

    public void validate(){
        return;
    }

    public void validateIsNull(Optional optional) {
        if(optional.isEmpty()){
            // null exception
            throw new IllegalArgumentException(ERROR_PREFIX + "객체가 생성되지 않았습니다.");
        }
    }
}
