package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.common.constant.InputConstants;
import store.domain.Product;
import store.domain.Promotions;
import store.dto.OneCart;

public class InputView {

    public String purchaseProduct(){
        System.out.println(InputConstants.PRODUCT_NAME_NUMBER_INPUT);
        return Console.readLine();
    }

    public String addProductByPromotion(OneCart onecart) {
        Product product = onecart.getProduct();
        Promotions promotions = onecart.getPromotions();
        System.out.println(String.format(InputConstants.PRODUCT_ADD_INPUT, product.getName(), promotions.getGet()));
        return Console.readLine();
    }

    public String checkPromotion(OneCart onecart, int stock) {
        Product product = onecart.getProduct();
        System.out.println(String.format(InputConstants.PROMOTION_CHECK_INPUT, product.getName(), stock));
        return Console.readLine();
    }

    public String checkMemberShip() {
        System.out.println(InputConstants.MEMBERSHIP_CHECK_INPUT);
        return Console.readLine();
    }

    public String askForAdditionalPurchase(){
        System.out.println(InputConstants.ADDITIONAL_INPUT);
        return Console.readLine();
    }
}
