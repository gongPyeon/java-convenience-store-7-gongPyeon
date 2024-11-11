package store.domain;

import store.common.format.Format;
import store.exception.ErrorMessage;

import java.text.NumberFormat;

public class Product {
    private final String name;
    private final int price; // null일 경우 Integer
    private int quantity;
    private String promotion;

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
            throw ErrorMessage.NUMBER_NOT.getException();
        }
    }

    private void validateNumberRange(int num){
        if(num < 0){
            throw ErrorMessage.NUMBER_NEGATIVE.getException();
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
        StringBuilder result = new StringBuilder("- " + name + " " +
                NumberFormat.getInstance().format(price) + "원 ");

        if (quantity > 0) {
            result.append(quantity).append("개 ");
        }else{
            result.append(Format.OUT_OF_STOCK);
        }

        if (!Format.NULL.equals(promotion)) {  // promotion이 "null"이 아닐 때만 추가
            result.append(promotion);
        }

        return result.toString();
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

    public void addQunatity(int add) {
        quantity += add;
    }

    public int getPromotionCount() {
        return promotionCount;
    }

    public int getPrice() {
        return price;
    }

    public void updatePromotion() {
        this.promotion = Format.NULL;
    }
}
