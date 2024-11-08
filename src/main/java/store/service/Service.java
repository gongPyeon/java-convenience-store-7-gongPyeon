package store.service;

import store.common.constant.InputConstants;
import store.common.constant.ValidConstatns;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        List<String> productInfoBycart = Arrays.stream(cartInfo.split(ValidConstatns.SEPARATOR))
                .map(String::trim)
                .collect(Collectors.toList());

        List<Product> products = createProduct(productInfoBycart);
        for(int i=0; i<products.size(); i++){
            System.out.println(products.get(i));
        }
        User user = new User(false);
        Cart cart = new Cart(products, user);

        return cart;
    }

    private List<Product> createProduct(List<String> productInfoBycart) {
        List<Product> products = new ArrayList<>();
        for(int i = 0; i< productInfoBycart.size(); i++){
            Product product = getProduct(productInfoBycart.get(i));
            products.add(product);
        }

        return products;
    }

    private static Product getProduct(String productInfoBycart) {
        productInfoBycart = productInfoBycart.substring(1, productInfoBycart.length()-1); // 대괄호 없애기
        List<String> product = Arrays.stream(productInfoBycart.split("-"))
                .map(String::trim)
                .collect(Collectors.toList());
        return new Product(product.get(0), product.get(1));
    }
}
