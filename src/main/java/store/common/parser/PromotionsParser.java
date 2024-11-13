package store.common.parser;

import store.common.format.Format;
import store.domain.Promotions;

public class PromotionsParser {
    public static Promotions parsPromotions(String promotionLineByFile) {
        String[] productLine = promotionLineByFile.split(Format.SEPARATOR);
        return new Promotions(
                productLine[0],
                Integer.parseInt(productLine[1]),
                Integer.parseInt(productLine[2]),
                productLine[3],
                productLine[4]
        );
    }
}
