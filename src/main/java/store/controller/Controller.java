package store.controller;

import store.common.constant.InputConstants;
import store.domain.Product;
import store.domain.Promotions;
import store.domain.User;
import store.dto.Cart;
import store.dto.oneCart;
import store.service.Service;
import store.validator.Validator;
import store.view.InputView;
import store.view.OutputView;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Controller {
    private final Service service;
    private final InputView inputView;
    private final OutputView outputView;
    private final Validator validator;

    private final User user;

    public Controller(Service service, InputView inputView, OutputView outputView, Validator validator, User user) {
        this.service = service;
        this.inputView = inputView;
        this.outputView = outputView;
        this.validator = validator;
        this.user = user;
    }

    public void run(){
        outputView.welcome();

        service.storeProductAndPromotionsListByFile(InputConstants.PRODUCT_FILE, InputConstants.PROMOION_FILE);
        Cart cart = InputProductNameAndNum();
        //Cart checkCart = service.checkProductIsPromotion(cart);

        for (Map.Entry<Product, Promotions> entry : cart.getCart().entrySet()) {
            // value가 1인 경우만 처리 (프로모션이 가능한 경우)
            Product product = entry.getKey();
            Promotions promotions = entry.getValue();
            if (!validator.checkIsNull(Optional.of(promotions))) {
                // 각 product에 대해 필요한 작업을 수행
                InputAddProductByPromotion(new oneCart(product, promotions));
                InputCheckPromotionStock();
            }
        }

        InputCheckMemberShip();

    }

    private void InputCheckMemberShip() {
    }

    private void InputCheckPromotionStock() {

    }

    private void InputAddProductByPromotion(oneCart onecart) { // 수정하기
        while(true){
            try{
                String response = inputView.addProduct(onecart);
                validator.validateResponseFormat(response);
                return service.updateCart(onecart);
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private Cart InputProductNameAndNum() {
        while(true){
            try{
                String cartInfo = inputView.purchaseProduct();
                validator.validateFormat(cartInfo);
                return service.InputCart(cartInfo);
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
