package store.dto;

import store.domain.Product;
import store.domain.User;

import java.util.List;

/**
 * 사용자가 구매할 상품 및 개수
 */
public class Cart {
    private final List<Product> cart;
    private final User user;

    public Cart(List<Product> cart, User user) {
        this.cart = cart;
        this.user = user;
    }
}
