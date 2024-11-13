package store.domain;

import store.common.constant.OutputConstants;
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

    public String getName() {
        return name;
    }

    public String getPromotion() {
        return promotion;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPromotionCount() {
        return promotionCount;
    }
    public int getPrice() {
        return price;
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

    public void updatePromotion() {
        this.promotion = Format.NULL;
    }

    public void updatePromotionCount(int num){
        promotionCount = promotionCount - num;
    }

    private String printQuantity(int quantity){
        if (quantity > 0) {
            return Integer.toString(quantity)+"개 ";
        }
        return Format.OUT_OF_STOCK;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(String.format(OutputConstants.PRODUCT_OUTPUT, name,
                NumberFormat.getInstance().format(price),
                printQuantity(quantity)));

        if (!Format.NULL.equals(promotion)) {
            result.append(promotion);
        }
        return result.toString();
    }


}
