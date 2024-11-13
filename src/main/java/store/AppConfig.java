package store;

import store.common.handler.InputHandler;
import store.domain.User;
import store.repository.*;
import store.service.CartService;
import store.service.PromotionService;
import store.service.ReceiptService;
import store.service.StockService;
import store.validator.Validator;
import store.view.InputView;
import store.view.OutputView;

public class AppConfig {

    public InputView inputView() {
        return new InputView();
    }

    public OutputView outputView() {
        return new OutputView();
    }

    public Validator validator() {
        return new Validator();
    }

    public ProductRepository productRepository() {
        return new CSProductRepository();
    }

    public PromotionProductRepository promotionProductRepository() {
        return new CSPromotionProductRepository();
    }

    public PromotionRepository promotionRepository() {
        return new CSPromotionRepository();
    }

    public User user() { // membership은 처음에 이렇게 정해진게 맞나?
        return new User(true);
    }

    public StockService stockService() {
        return new StockService(this.validator(),
                this.productRepository(),
                this.promotionProductRepository(),
                this.promotionRepository());
    }

    public CartService cartService() {
        return new CartService(this.validator(),
                this.productRepository(),
                this.promotionProductRepository(),
                this.promotionRepository());
    }

    public ReceiptService receiptService() {
        return new ReceiptService(this.validator(),
                this.productRepository(),
                this.promotionProductRepository(),
                this.promotionRepository());
    }

    public PromotionService promotionService() {
        return new PromotionService(this.validator(),
                this.productRepository(),
                this.promotionProductRepository(),
                this.promotionRepository());
    }

    public InputHandler inputHandler() {
        return new InputHandler(this.inputView(), this.validator());
    }
}
