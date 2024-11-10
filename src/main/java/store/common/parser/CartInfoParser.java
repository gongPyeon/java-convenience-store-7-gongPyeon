package store.common.parser;

import store.common.format.Format;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CartInfoParser {
    public static List<String> parseCartInfo(String cartInfo) {
        return Arrays.stream(cartInfo.split(Format.SEPARATOR))
                .map(String::trim)
                .collect(Collectors.toList());
    }

}
