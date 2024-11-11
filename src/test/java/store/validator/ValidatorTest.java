package store.validator;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;
import store.AppConfig;
import store.Application;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest extends NsTest {
    Validator validator = new Validator();
    @Test
    void 응답_형식과_다를_경우(){
        assertThatThrownBy(()->validator.validateResponseFormat("NO"))
                .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
    }

    @Test
    void 응답_형식과_다를_경우2(){
        assertThatThrownBy(()->validator.validateResponseFormat("Y "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
    }

    @Override
    protected void runMain() {
        Application.main(new String[]{});
    }
}