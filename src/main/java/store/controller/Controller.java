package store.controller;

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
        String productFile = "products.md";
        String promotionFile = "promotions.md";
        service.storeProductAndPromotionsListByFile(productFile, promotionFile);
        Cart cart = InputProductNameAndNum();

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
