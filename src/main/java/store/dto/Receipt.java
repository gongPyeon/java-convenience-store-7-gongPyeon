package store.dto;

import store.domain.Product;

import java.util.List;

public class Receipt {
    private final List<Product> productList;
    private final List<Product> promotionList;
    private final int total;
    private final int promotionDiscount;
    private final int membershipDiscount;
    private final int money;

    public Receipt(List<Product> productList, List<Product> promotionList, int total, int promotionDiscount, int membershipDiscount, int money) {
        this.productList = productList;
        this.promotionList = promotionList;
        this.total = total;
        this.promotionDiscount = promotionDiscount;
        this.membershipDiscount = membershipDiscount;
        this.money = money;
    }
}
