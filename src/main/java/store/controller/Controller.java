package store.controller;

import store.common.constant.InputConstants;
import store.domain.Product;
import store.dto.Cart;
import store.service.Service;
import store.validator.Validator;
import store.view.InputView;
import store.view.OutputView;

import java.util.List;

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
        service.checkProductIsPromotion(cart);
        //service.checkUserIsMemberShip(cart);

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
