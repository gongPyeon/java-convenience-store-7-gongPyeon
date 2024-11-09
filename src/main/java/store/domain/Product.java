package store.domain;

public class Product {
    private final String name;
    private final int price; // null일 경우 Integer
    private int quantity;
    private  final String promotion;

    private int promotionCount = 0;

    public Product(String name, String price, String quantity, String promotion) {
        this.name = name;
        this.price = validateNumber(price);
        this.quantity = validateNumber(quantity);
        this.promotion = promotion;

        validateNumberRange(this.price);
        validateNumberRange(this.quantity);
    }

    public void updatePromotionCount(int num){
        promotionCount = promotionCount - num;
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

    @Override
    public String toString() {
        return "- " + name + " " +
                price + "원 "
                + quantity + "개 "
                + promotion;
    }

    public void setPromotionCount(int promotionCount) {
        this.promotionCount = promotionCount;
    }

    public void addPromotionCount() {
        this.promotionCount ++;
    }

    public void updateQunatity(int miss) {
        quantity -= miss;
    }

    public int getPromotionCount() {
        return promotionCount;
    }

    public int getPrice() {
        return price;
    }
}
