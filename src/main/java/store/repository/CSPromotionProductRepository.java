package store.repository;

import store.domain.Product;

import java.util.LinkedHashMap;
import java.util.Map;

public class CSPromotionProductRepository implements PromotionProductRepository{
    private static Map<String, Product> promotionStork = new LinkedHashMap<>();
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
                    Product product = entry.getValue();
                    System.out.println(product);
                });
    }

    @Override
    public int findQuantityByName(String productName) {
        Product product = findByName(productName);
        if(product == null) return 0;
        return product.getQuantity();
    }

    @Override
    public void update(Product product) {
        promotionStork.put(product.getName(), product);
    }
}
