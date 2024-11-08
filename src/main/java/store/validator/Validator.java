package store.validator;

import store.common.constant.ValidConstatns;
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

    public void validateFormat(String string) {
        if (!string.matches(ValidConstatns.VALID_INPUT_PATTERN)) {
            throw new IllegalArgumentException(ERROR_PREFIX + "입력받은 형식이 정해진 형식이 아닙니다.");
        }
    }
}
