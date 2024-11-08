package store.repository;

import store.domain.Product;

public interface ProductRepository {
    void save(Product product);

    Product findByName(String productName);
}
