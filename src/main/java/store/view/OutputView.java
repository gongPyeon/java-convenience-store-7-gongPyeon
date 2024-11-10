package store.view;

import store.common.constant.OutputConstants;
import store.domain.Product;
import store.dto.Receipt;
import store.io.IOManager;

import java.text.NumberFormat;
import java.util.List;
import java.util.Set;

public class OutputView {

    private final IOManager manager;

    public OutputView(IOManager manager) {
        this.manager = manager;
    }

    public void welcome(){
        manager.printMessage(OutputConstants.WERLCOME_OUTPUT);
    }

    public void print(Receipt receipt) {
        printWelcomeMessage();
        printProductList(receipt.getProductList());
        printPromotionList(receipt.getPromotionList());
        printSummary(receipt);
    }

    private void printWelcomeMessage() {
        manager.printMessage(OutputConstants.CS_OUTPUT + OutputConstants.CS_RECEIPT_OUTPUT);
    }

    private void printProductList(List<Product> productList) {
        for (Product product : productList) {
            manager.printMessage(String.format(OutputConstants.CS_RECEIPT_FORMAT_OUTPUT,
                    product.getName(), product.getQuantity(), (product.getQuantity() *product.getPrice())));
        }
    }

    private void printPromotionList(List<Product> promotionList) {
        manager.printMessage(OutputConstants.CS_PROMOTION_OUTPUT);
        for (Product product : promotionList) {
            manager.printMessage(String.format(OutputConstants.CS_PROMOTION_FORMAT_OUTPUT,
                    product.getName(), product.getPromotionCount()));
        }
    }

    private void printSummary(Receipt receipt) {
        int total = receipt.getTotal();
        int totalNum = receipt.getTotalNum();
        int promotionDiscount = receipt.getPromotionDiscount();
        int membershipDiscount = receipt.getMembershipDiscount();
        int money = receipt.getMoney();

        manager.printMessage(OutputConstants.CS_OURPUT +
                String.format(OutputConstants.CS_TOTAL_FORMAT_OUTPUT, totalNum, NumberFormat.getInstance().format(total)) +
                String.format(OutputConstants.CS_P_DISCOUNT_FORMAT_OUTPUT, NumberFormat.getInstance().format(promotionDiscount)) +
                String.format(OutputConstants.CS_M_DISCOUNT_FORMAT_OUTPUT, NumberFormat.getInstance().format(membershipDiscount)) +
                String.format(OutputConstants.CS_MONEY_FORMAT_OUTPUT, NumberFormat.getInstance().format(money)));
    }

    public void printProduct(List<Product> productNames) { // domain param
        for(Product product : productNames){
            if(product != null) {
                manager.printMessage(product);
            }
        }
    }
}
