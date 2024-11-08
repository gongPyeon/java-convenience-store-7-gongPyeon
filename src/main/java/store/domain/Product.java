package store.domain;

public class Product {
    private final String name;
    private final String price; // null일 경우 Integer
    private final String quantity;
    private  final String promotion;

    public Product(String name, String price, String quantity, String promotion) {
        validateNumber(price);
        validateNumber(quantity);
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }


    public Product(String name, String quantity) {
        validateNumber(quantity);
        this.name = name;
        this.quantity = quantity;
        this.price = "";
        this.promotion = "";
    }

    private void validateNumber(String string) {
        try{
            Integer.parseInt(string);
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("숫자가 아닙니다");
        }
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
