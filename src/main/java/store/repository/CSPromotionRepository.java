package store.repository;

import store.domain.Product;
import store.domain.Promotions;

import java.util.HashMap;
import java.util.Map;

public class CSPromotionRepository implements PromotionRepository{

    private static Map<String, Promotions> promotionList = new HashMap<>();

    @Override
    public void save(Promotions promotions) {
        promotionList.put(promotions.getName(), promotions);
    }

    @Override
    public Promotions findByName(String promotionName) {
        return promotionList.get(promotionName);
    }

    @Override
    public void print() {
        promotionList.entrySet().stream()
                .forEach(entry -> {
                    Promotions promotion = entry.getValue();
                    System.out.println(promotion);
                });
    }
}
