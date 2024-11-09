package store.view;

import store.common.constant.InputConstants;
import store.domain.Product;
import store.domain.Promotions;
import store.dto.OneCart;
import store.message.MessageManager;

public class InputView {
    private final MessageManager manager;

    public InputView(MessageManager manager) {
        this.manager = manager;
    }

    public String purchaseProduct(){
        manager.printMessage(InputConstants.PRODUCT_NAME_NUMBER_INPUT);
        return manager.inputMessage();
    }

    public String addProductByPromotion(OneCart onecart) {
        Product product = onecart.getProduct();
        Promotions promotions = onecart.getPromotions();
        manager.printMessage(String.format(InputConstants.PRODUCT_ADD_INPUT, product.getName(), promotions.getGet()));
        return manager.inputMessage();
    }

    public String checkPromotion(OneCart onecart, int stock) {
        Product product = onecart.getProduct();
        manager.printMessage(String.format(InputConstants.PROMOTION_CHECK_INPUT, product.getName(), stock));
        return manager.inputMessage();
    }

    public String checkMemberShip() {
        manager.printMessage(InputConstants.MEMBERSHIP_CHECK_INPUT);
        return manager.inputMessage();
    }

    public String askForAdditionalPurchase(){
        manager.printMessage(InputConstants.);
    }
}
