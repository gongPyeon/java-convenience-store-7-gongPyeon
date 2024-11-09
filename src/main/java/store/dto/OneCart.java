package store.dto;

import store.domain.Product;
import store.domain.Promotions;

public class OneCart {

    private final Product product;
    private final Promotions promotions;

    public OneCart(Product product, Promotions promotions) {
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
