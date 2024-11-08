package store.view;

import store.common.constant.InputConstants;
import store.domain.Product;
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

    public String addProduct(Product product) {
        manager.printMessage(String.format(InputConstants.PRODUCT_ADD_INPUT, product.getName(), product.getQuantity()));
        return manager.inputMessage();
    }
}
