package store.service;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.AppConfig;
import store.Application;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class CartServiceTest extends NsTest {
    AppConfig appConfig = new AppConfig();
    CartService cartService = new CartService(appConfig.validator(), appConfig.productRepository(), appConfig.promotionProductRepository(), appConfig.promotionRepository());

    @Test
    void 대괄호가_없으면_예외처리한다(){
        assertThatThrownBy(()->cartService.InputCart("콜라-1"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상품명에_공백을_포함한_경우_예외처리한다(){
        assertThatThrownBy(()->cartService.InputCart("[콜 라-1]"))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    void 상품명이_없을_경우_예외처리한다(){
        assertThatThrownBy(()->cartService.InputCart("[제로콜라-1]"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 수량이_숫자가_아닐_경우_예외처리한다(){
        assertThatThrownBy(()->cartService.InputCart("[콜라-콜라]"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 수량이_음수일_경우_예외처리한다(){
        assertThatThrownBy(()->cartService.InputCart("[콜라- -3]"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상품명이_중복일_경우_예외처리한다(){
        assertThatThrownBy(()->cartService.InputCart("[콜라-3],[콜라-1]"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 수량이_부족할_경우_예외처리한다(){
        assertSimpleTest(() -> {
            runException("[컵라면-100]", "N", "N");
            assertThat(output()).contains("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        });
    }

    @Test
    void 공백을_포함할_경우(){
        assertSimpleTest(() -> {
            run("[ 비타민워터- 3],[물 -2],[ 정식도시락-2 ]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈18,300");
        });
    }


    @Override
    protected void runMain() {
        Application.main(new String[]{});
    }
}