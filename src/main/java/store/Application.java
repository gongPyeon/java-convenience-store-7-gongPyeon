package store;

import store.controller.Controller;
import store.repository.*;
import store.service.Service;
import store.validator.Validator;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현

        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        Validator validator = new Validator();
        ProductRepository productRepository = new CSProductRepository();
        PromotionProductRepository promotionProductRepository = new CSPromotionProductRepository();
        PromotionRepository promotionRepository = new CSPromotionRepository();

        Service service = new Service(validator, productRepository, promotionProductRepository, promotionRepository);
        Controller controller = new Controller(service, inputView, outputView, validator);
        controller.run();
    }
}
