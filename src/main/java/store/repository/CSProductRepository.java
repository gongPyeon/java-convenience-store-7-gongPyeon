package store.repository;

import store.domain.Product;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * CS mean ConvenienceStore
 */
public class CSProductRepository implements ProductRepository{

    private static Map<String, Product> generalStock = new LinkedHashMap<>();
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
        generalStock.put(product.getName(), product);
    }

    @Override
    public Set<String> getKey(){ return generalStock.keySet();}
}
