package store.service;

import store.common.parser.FileParser;
import store.domain.Product;
import store.repository.ProductRepository;
import store.repository.PromotionProductRepository;
import store.repository.PromotionRepository;
import store.validator.Validator;

import java.util.List;

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

    }

    private void storeProductListByFile(String productFile) {
        List<String> productListByFile = FileParser.readMarkdownFile(productFile);
        for(int i=1; i<productListByFile.size(); i++){
            Product product = splitProductList(productListByFile.get(i));
            System.out.println(product);
            checkAndstoreProduct(product);
        }
    }

    private void checkAndstoreProduct(Product product) {
        if(product.getPromotion().equals("null")){
            productRepository.save(product);
            return;
        }
        promotionProductRepository.save(product);
    }

    private Product splitProductList(String productLineByFile) {
        try {
            String[] productLine = productLineByFile.split(",");
            return new Product(
                    productLine[0],
                    Integer.parseInt(productLine[1]),
                    Integer.parseInt(productLine[2]),
                    productLine[3]);
        } catch (NumberFormatException e) {
            //throw IOException.CANNOT_PARSE_TO_INTEGER.getException();
        }
        return null;
    }
}
