package store.service;

import store.common.format.Format;
import store.common.parser.FileParser;
import store.common.parser.ProductParser;
import store.common.parser.PromotionsParser;
import store.domain.Product;
import store.domain.Promotions;
import store.dto.Cart;
import store.repository.ProductRepository;
import store.repository.PromotionProductRepository;
import store.repository.PromotionRepository;
import store.validator.Validator;
import java.util.List;

public class StockService {

    private final Validator validator;
    private final ProductRepository productRepository;
    private final PromotionProductRepository promotionProductRepository;

    private final PromotionRepository promotionRepository;

    public StockService(Validator validator, ProductRepository productRepository, PromotionProductRepository promotionProductRepository, PromotionRepository promotionRepository) {
        this.validator = validator;
        this.productRepository = productRepository;
        this.promotionProductRepository = promotionProductRepository;
        this.promotionRepository = promotionRepository;
    }

    public void storeProductAndPromotionsListByFile(String productFile, String promotionsFile) {
        storeProductListByFile(productFile);
        storePromotionsListByFile(promotionsFile);
    }

    private void storeProductListByFile(String productFile) {
        List<String> productListByFile = FileParser.readMarkdownFile(productFile);

        for (int i = 1; i < productListByFile.size(); i++) {
            Product product = ProductParser.parseProduct(productListByFile.get(i));
            validator.validateIsNull(product); // Optional.ofNullalbe 고려

            checkAndStoreProducts(product);
        }
    }


    private void storePromotionsListByFile(String promotionsFile) {
        List<String> promotionListByFile = FileParser.readMarkdownFile(promotionsFile);
        for (int i = 1; i < promotionListByFile.size(); i++) {
            Promotions promotion = PromotionsParser.parsPromotions(promotionListByFile.get(i));
            validator.validateIsNull(promotion);
            promotionRepository.save(promotion);
        }
    }

    // 파일에 하나만 적혀있어도(예를들어, 오렌지주스 반짝할인)
    // productRepository와 promotionProductRepository에 동일한 상품이 존재해야한다
    private void checkAndStoreProducts(Product product) {
        if (product.getPromotion().equals(Format.NULL)) {
            productRepository.save(product);
            checkAndStorePromotionProduct(product);
            return;
        }

        promotionProductRepository.save(product);
        checkAndStoreProduct(product);
    }

    private void checkAndStoreProduct(Product product) {
        if(productRepository.findByName(product.getName()) == null){
            productRepository.save(createDefaultProduct(product));
        }
    }

    private void checkAndStorePromotionProduct(Product product) {
        if(promotionProductRepository.findByName(product.getName()) == null) {
            promotionProductRepository.save(createDefaultProduct(product));
        }
    }

    private Product createDefaultProduct(Product product) {
        return new Product(product.getName(), Integer.toString(product.getPrice()), Format.ZERO, Format.NULL);
    }

    private List<Product> calculateProductList(Cart cart) {
        return cart.getCart().keySet().stream().toList();
    }
    public void updateStock(Cart cart) {
        List<Product> products = calculateProductList(cart);

        for(int i=0; i<products.size(); i++){
            Product product = products.get(i);
            updateProductRepository(product);
            updatePromotionProductRepository(product);
        }
    }

    private void updatePromotionProductRepository(Product product) {
        Product promotionByStock = promotionProductRepository.findByName(product.getName());
        if(promotionByStock != null){
            promotionByStock.updateQunatity(product.getPromotionCount()); // 기존 - 프로모션 개수
            if(promotionByStock.getQuantity() <= 0){
                promotionByStock.updatePromotion();
            }
            promotionProductRepository.update(promotionByStock);
        }
    }

    private void updateProductRepository(Product product) {
        Product productByStock = productRepository.findByName(product.getName());
        if(productByStock != null){
            int quantity = product.getQuantity() - product.getPromotionCount();
            productByStock.updateQunatity(quantity); // 기존 - 프로모션 제외한 개수
            productRepository.update(productByStock);
        };
    }

}
