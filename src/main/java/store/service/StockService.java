package store.service;

import store.common.format.Format;
import store.common.parser.FileParser;
import store.common.parser.ProductParser;
import store.common.parser.PromotionsParser;
import store.domain.Product;
import store.domain.Promotions;
import store.repository.ProductRepository;
import store.repository.PromotionProductRepository;
import store.repository.PromotionRepository;
import store.validator.Validator;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class StockService {

    private final Validator validator;
    private final ProductRepository productRepository;
    private final PromotionProductRepository promotionProductRepository;

    private final PromotionRepository promotionRepository;

    private static final String OUT_OF_STOCK = "재고 없음";
    private static final String ZERO = "0";

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
        return new Product(product.getName(), Integer.toString(product.getPrice()), ZERO, OUT_OF_STOCK);
    }


}
