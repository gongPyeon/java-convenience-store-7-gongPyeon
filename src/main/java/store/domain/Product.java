package store.domain;

public class Product {
    private final String name;
    private final int price; // null일 경우 Integer
    private final int quantity;
    private  final String promotion;

    public Product(String name, int price, int quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = nullToEmpty(promotion);
    }

    public String getName() {
        return name;
    }

    public String getPromotion() {
        return promotion;
    }

    private String nullToEmpty(String promotion) {
        if(promotion.equals("null"))
            return "";
        return promotion;
    }

    @Override
    public String toString() {
        return "- " + name + " " +
                price + "원 "
                + quantity + "개 "
                + promotion;
    }
}
