package store;

import store.controller.Controller;

public class Application {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        Controller controller = new Controller(
                appConfig.receiptService(), appConfig.stockService(), appConfig.cartService(), appConfig.promotionService(),
                appConfig.inputView(), appConfig.outputView(), appConfig.inputHandler(), appConfig.user());

        controller.run();
    }
}
