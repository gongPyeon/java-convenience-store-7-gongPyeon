package store.repository;

import store.domain.Product;

import java.util.HashMap;
import java.util.Map;

/**
 * CS mean ConvenienceStore
 */
public class CSProductRepository implements ProductRepository{

    private static Map<String, Product> store = new HashMap<>();
    @Override
    public void save(Product product) {
        store.put(product.getName(), product);
    }

    @Override
    public Product findByName(String productName) {
        return store.get(productName);
    }
}
