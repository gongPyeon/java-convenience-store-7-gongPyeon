package store.view;

import store.common.constant.InputConstants;
import store.domain.Product;
import store.dto.oneCart;
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

    public String addProduct(oneCart onecart) {
        Product product = onecart.getProduct();
        manager.printMessage(String.format(InputConstants.PRODUCT_ADD_INPUT, product.getName(), product.getQuantity()));
        return manager.inputMessage();
    }

    public String checkPromotion(oneCart onecart, int stock) {
        Product product = onecart.getProduct();
        manager.printMessage(String.format(InputConstants.PROMOTION_CHECK_INPUT, product.getName(), stock));
        return manager.inputMessage();
    }

    public String checkMemberShip() {
        manager.printMessage(InputConstants.MEMBERSHIP_CHECK_INPUT);
        return manager.inputMessage();
    }
}
