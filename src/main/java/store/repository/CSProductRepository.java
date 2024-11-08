package store.repository;

import store.domain.Product;

import java.util.HashMap;
import java.util.Map;

/**
 * CS mean ConvenienceStore
 */
public class CSProductRepository implements ProductRepository{

    private static Map<String, Product> generalStock = new HashMap<>();
    @Override
    public void save(Product product) {
        generalStock.put(product.getName(), product);
    }

    @Override
    public Product findByName(String productName) {
        return generalStock.get(productName);
    }

    @Override
    public void print() {
        generalStock.entrySet().stream()
                .forEach(entry -> {
                    String key = entry.getKey();
                    Product product = entry.getValue();
                    System.out.println("키: " + key + ", 상품: " + product);
                });
    }
}
