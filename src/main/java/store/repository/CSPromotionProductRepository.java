package store.repository;

import store.domain.Product;

import java.util.HashMap;
import java.util.Map;

public class CSPromotionProductRepository implements PromotionProductRepository{
    private static Map<String, Product> promotionStork = new HashMap<>();
    @Override
    public void save(Product product) {
        promotionStork.put(product.getName(), product);
    }

    @Override
    public Product findByName(String productName) {
        return promotionStork.get(productName);
    }

    @Override
    public void print() {
        promotionStork.entrySet().stream()
                .forEach(entry -> {
                    String key = entry.getKey();
                    Product product = entry.getValue();
                    System.out.println("키: " + key + ", 상품: " + product);
                });
    }
}
