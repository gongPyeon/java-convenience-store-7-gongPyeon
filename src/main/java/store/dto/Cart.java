package store.dto;

import store.domain.Product;
import store.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 사용자가 구매할 상품 및 개수
 */
public class Cart {
    private final Map<Product, Integer> cart;
    private final User user;


    public Cart(Map<Product, Integer> cart, User user) {
        this.cart = cart;
        this.user = user;
    }

    public Map<Product, Integer> getCart() {
        return cart;
    }

    public User getUser() {
        return user;
    }
}
