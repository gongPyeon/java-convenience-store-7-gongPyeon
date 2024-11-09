package store;

import store.controller.Controller;
import store.domain.User;
import store.message.Message;
import store.message.MessageManager;
import store.repository.*;
import store.service.Service;
import store.validator.Validator;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현

        MessageManager manager = new Message();
        InputView inputView = new InputView(manager);
        OutputView outputView = new OutputView(manager);
        Validator validator = new Validator();
        ProductRepository productRepository = new CSProductRepository();
        PromotionProductRepository promotionProductRepository = new CSPromotionProductRepository();
        PromotionRepository promotionRepository = new CSPromotionRepository();
        User user = new User(false);

        Service service = new Service(validator, productRepository, promotionProductRepository, promotionRepository);
        Controller controller = new Controller(service, inputView, outputView, validator, user);
        controller.run();
    }
}
