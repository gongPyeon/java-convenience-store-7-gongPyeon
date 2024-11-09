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
                oneCart oneCart = new oneCart(product, promotions);
                InputAddProductByPromotion(oneCart);
                InputCheckPromotionStock(oneCart);
            }
        }

        InputCheckMemberShip();

    }

    private void InputCheckMemberShip() {
        while(true){
            try{
                String response = inputView.checkMemberShip();
                validator.validateResponseFormat(response);
                // 멤버쉽을 받습니다를 어떻게 알리지?
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private void InputCheckPromotionStock(oneCart onecart) {
        while(true){
            try{
                // 프로모션 재고가 없을 경우인지 check (update는 나중ㅇㅔ_)
                int stock = service.checkPromotionStock(onecart);
                String response = inputView.checkPromotion(onecart, stock);
                validator.validateResponseFormat(response);
                service.updateCartByStock(onecart, response, stock);
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private void InputAddProductByPromotion(oneCart onecart) {
        while(true){
            try{
                // 부족하게 가져왔을 경우인지 check
                String response = inputView.addProduct(onecart);
                validator.validateResponseFormat(response);
                if(response.equals("Y"))
                    service.updateCart(onecart);
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
