package store.service;

import store.common.parser.FileParser;
import store.domain.Product;
import store.domain.Promotions;
import store.domain.User;
import store.dto.Cart;
import store.repository.ProductRepository;
import store.repository.PromotionProductRepository;
import store.repository.PromotionRepository;
import store.validator.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Service {

    private final Validator validator;
    private final ProductRepository productRepository;
    private final PromotionProductRepository promotionProductRepository;
    private final PromotionRepository promotionRepository;

    public Service(Validator validator, ProductRepository productRepository, PromotionProductRepository promotionProductRepository, PromotionRepository promotionRepository) {
        this.validator = validator;
        this.productRepository = productRepository;
        this.promotionProductRepository = promotionProductRepository;
        this.promotionRepository = promotionRepository;
    }

    public void storeProductAndPromotionsListByFile(String productFile, String promotionsFile) {
        storeProductListByFile(productFile);
        storePromotionsListByFile(promotionsFile);
    }

    private void storePromotionsListByFile(String promotionsFile) {
        List<String> promotionListByFile = FileParser.readMarkdownFile(promotionsFile);
        for (int i = 1; i < promotionListByFile.size(); i++) {
            Promotions promotion = splitPromotionList(promotionListByFile.get(i));
            validator.validateIsNull(Optional.of(promotion));
            promotionRepository.save(promotion);
        }
    }

    private Promotions splitPromotionList(String promotionLineByFile) {
        String[] productLine = promotionLineByFile.split(",");
        return new Promotions(
                productLine[0],
                Integer.parseInt(productLine[1]),
                Integer.parseInt(productLine[2]),
                productLine[3],
                productLine[4]
                );
    }

    private void storeProductListByFile(String productFile) {
        List<String> productListByFile = FileParser.readMarkdownFile(productFile);
        for (int i = 1; i < productListByFile.size(); i++) {
            Product product = splitProductList(productListByFile.get(i));
            validator.validateIsNull(Optional.of(product));
            System.out.println(product); // 어떻게 할지 생각해보기
            checkAndstoreProduct(product);
        }
    }

    private void checkAndstoreProduct(Product product) {
        if (product.getPromotion().isEmpty()) {
            productRepository.save(product);
            return;
        }
        promotionProductRepository.save(product);
    }

    private Product splitProductList(String productLineByFile) {
        String[] productLine = productLineByFile.split(",");
        return new Product(
                productLine[0],
                productLine[1],
                productLine[2],
                productLine[3]);
    }

    public Cart InputCart(String cartInfo) {
        String[] productInfoBycart = cartInfo.split(",");
        List<Product> products = createProduct(productInfoBycart);
        User user = new User(false);
        Cart cart = new Cart(products, user);

        return cart;
    }

    private List<Product> createProduct(String[] productInfoBycart) {
        List<Product> products = new ArrayList<>();
        for(int i = 0; i< productInfoBycart.length; i++){
            Product product = getProduct(productInfoBycart[i]);
            products.add(product);
        }

        return products;
    }

    private static Product getProduct(String productInfoBycart) {
        String[] split = productInfoBycart.split("-");
        return new Product(split[0], split[1]);
    }
}
