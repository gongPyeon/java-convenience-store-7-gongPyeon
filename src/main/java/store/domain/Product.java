package store.domain;

public class Product {
    private final String name;
    private final int price; // null일 경우 Integer
    private final int quantity;
    private  final String promotion;

    public Product(String name, String price, String quantity, String promotion) {
        this.name = name;
        this.price = validateNumber(price);
        this.quantity = validateNumber(quantity);
        this.promotion = promotion;

        validateNumberRange(this.price);
        validateNumberRange(this.quantity);
    }


    public Product(String name, String quantity) {
        this.name = name;
        this.quantity = validateNumber(quantity);
        this.price = 0;
        this.promotion = "";

        validateNumberRange(this.quantity);
    }

    private int validateNumber(String string) {
        try{
            return Integer.parseInt(string);
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("숫자가 아닙니다");
        }
    }

    private void validateNumberRange(int num){
        if(num <= 0){
            throw new IllegalArgumentException("음수입니다");
        }
    }

    public String getName() {
        return name;
    }

    public String getPromotion() {
        return promotion;
    }

    public int getQuantity() {
        return quantity;
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
