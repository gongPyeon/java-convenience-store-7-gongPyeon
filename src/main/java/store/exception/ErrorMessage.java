package store.exception;
import java.util.NoSuchElementException;

public enum ErrorMessage {

    OBJECT_NOT_CREATED("객체가 생성되지 않았습니다.", IllegalStateException.class),

    INVALID_FORMAT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.", IllegalArgumentException.class),
    PRODUCT_NOT_FOUND("존재하지 않는 상품입니다. 다시 입력해 주세요.", IllegalArgumentException.class),
    EXCEED_STOCK("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.", IllegalArgumentException.class),
    INVALID_INPUT("잘못된 입력입니다. 다시 입력해 주세요.", IllegalArgumentException.class),
    DUPLICATE_PRODUCT("중복된 상품이 있습니다. 다시 입력해 주세요.", IllegalArgumentException.class),

    NUMBER_NOT("숫자가 아닙니다. 다시 입력해주세요.", IllegalArgumentException.class),
    NUMBER_NEGATIVE("음수를 입력했습니다. 다시 입력해주세요.", IllegalArgumentException.class);

    private static final String ERROR_PREFIX = "[ERROR] ";

    private final String message;
    private final Class<? extends RuntimeException> exceptionClass;

    ErrorMessage(String message, Class<? extends RuntimeException> exceptionClass) {
        this.message = message;
        this.exceptionClass = exceptionClass;
    }

    public RuntimeException getException() {
        try {
            return exceptionClass.getDeclaredConstructor(String.class).newInstance(ERROR_PREFIX + message);
        } catch (ReflectiveOperationException ignored) {
            throw new RuntimeException(ERROR_PREFIX + "예외를 생성할 수 없습니다.");
        }
    }
}
