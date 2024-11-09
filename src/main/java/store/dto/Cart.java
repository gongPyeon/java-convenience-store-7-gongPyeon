package store.dto;

import store.domain.Product;
import store.domain.Promotions;
import store.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 사용자가 구매할 상품 및 개수
 */
public class Cart {
    private final Map<Product, Promotions> cart;

    public Cart(Map<Product, Promotions> cart) {
        this.cart = cart;
    }

    public Map<Product, Promotions> getCart() {
        return cart;
    }
}
