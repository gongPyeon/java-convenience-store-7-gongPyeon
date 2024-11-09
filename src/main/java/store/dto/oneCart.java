package store.dto;

import store.domain.Product;
import store.domain.Promotions;

public class oneCart {

    private final Product product;
    private final Promotions promotions;

    public oneCart(Product product, Promotions promotions) {
        this.product = product;
        this.promotions = promotions;
    }

    public Product getProduct() {
        return product;
    }

    public Promotions getPromotions() {
        return promotions;
    }
}
