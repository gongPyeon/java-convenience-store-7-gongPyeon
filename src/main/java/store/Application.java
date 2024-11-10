package store;

import store.controller.Controller;
import store.domain.User;
import store.io.IOManager;
import store.io.IOMessage;
import store.repository.*;
import store.service.Service;
import store.service.StockService;
import store.validator.Validator;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현

        IOManager manager = new IOMessage();
        InputView inputView = new InputView(manager);
        OutputView outputView = new OutputView(manager);
        Validator validator = new Validator();
        ProductRepository productRepository = new CSProductRepository();
        PromotionProductRepository promotionProductRepository = new CSPromotionProductRepository();
        PromotionRepository promotionRepository = new CSPromotionRepository();
        User user = new User(true);

        Service service = new Service(validator, productRepository, promotionProductRepository, promotionRepository);
        StockService stockService = new StockService(validator, productRepository, promotionProductRepository, promotionRepository);
        Controller controller = new Controller(service, stockService, inputView, outputView, validator, user);
        controller.run();
    }
}
