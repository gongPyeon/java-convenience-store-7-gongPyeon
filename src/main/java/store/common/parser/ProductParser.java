package store.common.parser;

import store.common.format.Format;
import store.domain.Product;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static store.common.format.Format.SEPARATOR_INNER;

public class ProductParser {
    public static Product parseProduct(String productLineByFile) {
        String[] productLine = productLineByFile.split(Format.SEPARATOR);
        return new Product(
                productLine[0],
                productLine[1],
                productLine[2],
                productLine[3]);
    }

    public static List<String> parseProductInfo(String productInfo) {
        productInfo = removeBrackets(productInfo);
        return Arrays.stream(productInfo.split(SEPARATOR_INNER))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    private static String removeBrackets(String productInfo) {
        return productInfo.substring(1, productInfo.length() - 1);
    }

}
