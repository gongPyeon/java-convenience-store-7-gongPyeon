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

import java.util.*;
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
        if (product.getPromotion().equals("null")) {
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

        Map<Product, Integer> products = createProduct(productInfoBycart);
        validator.validateProduct(products, productRepository);
        validator.validateProductNum(products, productRepository, promotionProductRepository);

//        for(int i=0; i<products.size(); i++){
//            System.out.println(products.get(i));
//        }
        return new Cart(products, new User(false));
    }

    private Map<Product, Integer> createProduct(List<String> productInfoBycart) {
        Map<Product, Integer> products = new HashMap<>();
        for(int i = 0; i< productInfoBycart.size(); i++){
            Product product = getProduct(productInfoBycart.get(i));
            products.put(product, 0);
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

    public Cart checkProductIsPromotion(Cart cart) {
        Map<Product, Integer> products = cart.getCart();
        for(Product product : products.keySet()) {
            if(validator.validatePromotion(product, promotionRepository)){
                products.put(product, 1);
            }
        }
        return new Cart(products, new User(false)); // 사용자 정보가 계속 왔다갔다 하고 있다
    }
}
