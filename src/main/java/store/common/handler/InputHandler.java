package store.common.handler;

import store.common.format.Format;
import store.validator.Validator;
import store.view.InputView;

import java.util.NoSuchElementException;
import java.util.function.Supplier;

public class InputHandler {
    private final InputView inputView;
    private final Validator validator;

    public InputHandler(InputView inputView, Validator validator) {
        this.inputView = inputView;
        this.validator = validator;
    }

    public boolean handleYesNoInput(Supplier<String> inputSupplier) {
        while (true) {
            try {
                String response = inputSupplier.get(); // 필요한 입력 메서드를 호출
                validator.validateResponseFormat(response); // 응답 형식 검증
                return response.equalsIgnoreCase(Format.YES); // 응답이 'YES'면 true 반환
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

