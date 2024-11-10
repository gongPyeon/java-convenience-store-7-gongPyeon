package store.repository;

import store.domain.Product;

import java.util.Set;

public interface ProductRepository {
    void save(Product product);

    Product findByName(String productName);

    void print();

    int findQuantityByName(String productName);

    void update(Product product);

    Set<String> getKey();
}
