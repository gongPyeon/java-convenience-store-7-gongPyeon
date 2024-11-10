package store.service;

import store.common.format.Format;
import store.common.parser.CartInfoParser;
import store.common.parser.ProductParser;
import store.domain.Product;
import store.domain.Promotions;
import store.dto.Cart;
import store.dto.OneCart;
import store.repository.ProductRepository;
import store.repository.PromotionProductRepository;
import store.repository.PromotionRepository;
import store.validator.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CartService {
    private final Validator validator;
    private final ProductRepository productRepository;
    private final PromotionProductRepository promotionProductRepository;

    private final PromotionRepository promotionRepository;

    public CartService(Validator validator, ProductRepository productRepository, PromotionProductRepository promotionProductRepository, PromotionRepository promotionRepository) {
        this.validator = validator;
        this.productRepository = productRepository;
        this.promotionProductRepository = promotionProductRepository;
        this.promotionRepository = promotionRepository;
    }

    public Cart InputCart(String cartInfo) {
        validator.validateFormat(cartInfo);

        List<String> productInfoByCart = CartInfoParser.parseCartInfo(cartInfo);
        Map<Product, Promotions> products = createProduct(productInfoByCart);
        validator.validateProduct(products, productRepository, promotionProductRepository);
        validator.validateProductNum(products, productRepository, promotionProductRepository);

        return new Cart(products);
    }

    private Map<Product, Promotions> createProduct(List<String> productInfoBycart) {
        Map<Product, Promotions> products = new HashMap<>();

        for (int i = 0; i < productInfoBycart.size(); i++) {
            Product product = createProductFromInfo(productInfoBycart, i);
            Promotions promotion = createPromotionForProduct(product);
            products.put(product, promotion);
        }
        return products;
    }

    private Promotions createPromotionForProduct(Product product) {
        Promotions promotion = getPromotion(product.getPromotion());
        return promotion;
    }

    private Product createProductFromInfo(List<String> productInfoBycart, int i) {
        Product product = getProduct(productInfoBycart.get(i));
        validator.validateIsNull(product);
        return product;
    }

    private Promotions getPromotion(String promotionName) {
        return promotionRepository.findByName(promotionName);
    }

    private Product getProduct(String productInfoBycart) {
        List<String> product = ProductParser.parseProductInfo(productInfoBycart);
        String name = product.get(0);
        String price = getPrice(name);
        String quantity = product.get(1);
        String promotion = getPromotions(name);

        return new Product(name, price, quantity, promotion);
    }

    private String getPromotions(String name) {
        return Optional.ofNullable(promotionProductRepository.findByName(name))
                .map(Product::getPromotion) // findByName이 null이 아니면 getPromotion() 호출
                .orElse(Format.NULL); // findByName이 null일 경우 "null" 할당
    }

    private String getPrice(String name) {
        return String.valueOf(Optional.ofNullable(productRepository.findByName(name))
                .map(Product::getPrice)
                .orElse(0));
    }

    public void updateCart(OneCart oneCart) {
        int get = oneCart.getPromotions().getGet();
        int buy = oneCart.getPromotions().getBuy();

        int userQuantity = oneCart.getProduct().getQuantity();

        // 2+1인 품목을 7개 가져올 경우, 7 % 3 = 1
        // 2+1인 품목을 2개 가져올 경우, 3 % 2
        // N + 1이 아닐때 고려하기
        int promotionCount = 1;

        oneCart.getProduct().addPromotionCount();
        oneCart.getProduct().addQunatity(promotionCount);
    }
}
