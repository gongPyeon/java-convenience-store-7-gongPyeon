package store.controller;

import store.common.constant.InputConstants;
import store.domain.Product;
import store.dto.Cart;
import store.service.Service;
import store.validator.Validator;
import store.view.InputView;
import store.view.OutputView;

import java.util.List;
import java.util.Map;

public class Controller {
    private final Service service;
    private final InputView inputView;
    private final OutputView outputView;
    private final Validator validator;

    public Controller(Service service, InputView inputView, OutputView outputView, Validator validator) {
        this.service = service;
        this.inputView = inputView;
        this.outputView = outputView;
        this.validator = validator;
    }

    public void run(){
        outputView.welcome();

        service.storeProductAndPromotionsListByFile(InputConstants.PRODUCT_FILE, InputConstants.PROMOION_FILE);
        Cart cart = InputProductNameAndNum();
        Cart checkCart = service.checkProductIsPromotion(cart);

        for (Map.Entry<Product, Integer> entry : checkCart.getCart().entrySet()) {
            // value가 1인 경우만 처리
            if (entry.getValue() == 1) {
                Product product = entry.getKey();

                // 각 product에 대해 필요한 작업을 수행
                InputAddProductByPromotion(product);
                InputCheckPromotionStock();
            }
        }

    }

    private void InputCheckPromotionStock() {

    }

    private void InputAddProductByPromotion(Product product) {
        while(true){
            try{
                String response = inputView.addProduct(product);
                validator.validateResponseFormat(response);
                return service.updateCart(response);
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
