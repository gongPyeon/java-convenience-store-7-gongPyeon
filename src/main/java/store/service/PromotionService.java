package store.service;

import store.domain.Product;
import store.domain.Promotions;
import store.dto.Cart;
import store.dto.OneCart;
import store.repository.ProductRepository;
import store.repository.PromotionProductRepository;
import store.repository.PromotionRepository;
import store.validator.Validator;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PromotionService {
    private final Validator validator;
    private final ProductRepository productRepository;
    private final PromotionProductRepository promotionProductRepository;

    private final PromotionRepository promotionRepository;

    public PromotionService(Validator validator, ProductRepository productRepository, PromotionProductRepository promotionProductRepository, PromotionRepository promotionRepository) {
        this.validator = validator;
        this.productRepository = productRepository;
        this.promotionProductRepository = promotionProductRepository;
        this.promotionRepository = promotionRepository;
    }

    public List<OneCart> filterPromotableItems(Cart cart) {
        List<OneCart> promotableItems = new LinkedList<>();
        for (Product product : cart.getCart().keySet()) {
            Promotions promotion = cart.getCart().get(product);

            if (validator.validatePromotion(promotion)) {
                promotableItems.add(new OneCart(product, promotion));
            }
        }
        return promotableItems;
    }

    public void divideBuyAndGet(OneCart oneCart) {
        Product product = oneCart.getProduct();
        Promotions promotions = oneCart.getPromotions();

        int userQuantity = product.getQuantity(); // 사용자가 담은 개수
        int get = userQuantity / (promotions.getBuy() + promotions.getGet()); // 증정품 계산

        product.setPromotionCount(get);
    }

    public boolean isProductQuantityInsufficient(OneCart oneCart) {
        Product product = oneCart.getProduct();
        Promotions promotions = oneCart.getPromotions();

        boolean isQuantitySufficient = isProductQuantitySufficient(product.getQuantity(), promotions.getBuy());
        if (isQuantitySufficient) {
            boolean isPromotionValid = isPromotionSatisfied(product.getQuantity(), promotions.getBuy(), promotions.getGet());
            return isPromotionValid;
        }

        return true;
        // 상품 수량이 프로모션 조건을 만족하지 않는데, 증정품을 가져온 경우  -> 발생하지 않음
        // 상품 수량이 프로모션 조건을 만족하는데, 증정품을 가져오지 않은 경우 -> 2+1인데 2개만 가져왔을 경우 -> 물어보고 진행
        // 상품 수량이 프로모션 조건을 만족하지 않고, 증정품도 가져오지 않는 경우 -> 2+1인데 1개만 가져올 경우 -> 물어보지 않고 진행
    }

    // 상품 수량이 프로모션 조건을 만족하는지 확인하는 함수
    public boolean isProductQuantitySufficient(int userQuantity, int requiredQuantity) {
        return userQuantity >= requiredQuantity;
    }

    // 프로모션 조건에 맞게 증정품을 가져왔는지 확인하는 함수
    private boolean isPromotionSatisfied(int userQuantity, int requiredQuantity, int get) {
        if (userQuantity % (requiredQuantity + get) == 0) {
            return true;
        }
        return false;
    }

    public boolean isPromotionStockInsufficient(OneCart oneCart) {
        Product product = oneCart.getProduct();
        int userPromotionQuantity = product.getPromotionCount();
        int promotionStockQuantity = promotionProductRepository.findQuantityByName(product.getName());

        return (promotionStockQuantity >= userPromotionQuantity);
    }

    public int calculateMissingPromotionQuantity(OneCart oneCart) {
        Product product = oneCart.getProduct();
        int userPromotionCount = product.getPromotionCount();
        int promotionStockQuantity = promotionProductRepository.findQuantityByName(product.getName());

        return (userPromotionCount - promotionStockQuantity);
    }

    public void updatePromotionCount(OneCart oneCart, int miss) {
        Product product = oneCart.getProduct();
        product.updatePromotionCount(miss);
    }

    public void updateQuantity(OneCart oneCart, int miss) {
        Product product = oneCart.getProduct();
        product.updatePromotionCount(miss);
        product.updateQunatity(miss);
    }

}
