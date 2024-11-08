package store.repository;

import store.domain.Promotions;

public interface PromotionRepository {
    void save(Promotions promotions);

    Promotions findByName(String promotionName);
}
