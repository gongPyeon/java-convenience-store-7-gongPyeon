package store.common.parser;

import store.common.format.Format;
import store.domain.Product;

public class ProductParser {
    public static Product parseProduct(String productLineByFile) {
        String[] productLine = productLineByFile.split(Format.SEPARATOR);
        return new Product(
                productLine[0],
                productLine[1],
                productLine[2],
                productLine[3]);
    }
}
