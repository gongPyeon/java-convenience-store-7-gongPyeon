package store.service;

import store.common.format.Format;
import store.domain.Product;
import store.dto.Cart;
import store.dto.Receipt;
import store.repository.ProductRepository;
import store.repository.PromotionProductRepository;
import store.repository.PromotionRepository;
import store.validator.Validator;

import java.util.*;
import java.util.stream.Collectors;

public class ReceiptService {

    private final Validator validator;
    private final ProductRepository productRepository;
    private final PromotionProductRepository promotionProductRepository;

    private final PromotionRepository promotionRepository;

    public ReceiptService(Validator validator, ProductRepository productRepository, PromotionProductRepository promotionProductRepository, PromotionRepository promotionRepository) {
        this.validator = validator;
        this.productRepository = productRepository;
        this.promotionProductRepository = promotionProductRepository;
        this.promotionRepository = promotionRepository;
    }

    public List<Product> getProductSet(){
        Set<String> productNames = productRepository.getKey();
        List<Product> products = new LinkedList<>();

        for(String name : productNames){
            Product product = productRepository.findByName(name);
            if(product != null) {
                products.add(product);
            }
            products.add(promotionProductRepository.findByName(name));
        }
        return products;
    }


    public Receipt calculator(Cart cart, boolean checkMemberShip) {
        List<Product> productList = calculateProductList(cart);
        List<Product> promotionList = calculatePromotionList(productList);
        int total = calculateTotal(productList);
        int totalNum = calculateTotalNum(productList);
        int promotionDiscount = calculatePromotion(promotionList);
        int membershipDiscount = calculateMembership(total - promotionDiscount, checkMemberShip);
        int money = calculateMoney(total, promotionDiscount, membershipDiscount);

        return new Receipt(productList, promotionList, total, totalNum, promotionDiscount, membershipDiscount, money);
    }

    private int calculateTotalNum(List<Product> productList) {
        int sum = 0;
        for(int i=0; i<productList.size(); i++){
            int quantity = productList.get(i).getQuantity();
            sum += quantity;
        }
        return sum;
    }

    private int calculateMoney(int total, int promotionDiscount, int membershipDiscount) {
        return total - (promotionDiscount + membershipDiscount);
    }

    private int calculateMembership(int total, boolean checkMemberShip) {
        int membershipDiscount = 0;
        if(checkMemberShip) {
            membershipDiscount = (int) (total * 0.3);
            if(membershipDiscount > 8000) // 상수처리
                membershipDiscount = 8000;
        }
        return membershipDiscount;
    }

    private int calculatePromotion(List<Product> promotionList) {
        int sum = 0;
        for(int i=0; i<promotionList.size(); i++){
            int price = promotionList.get(i).getPrice();
            int quantity = promotionList.get(i).getPromotionCount();
            sum += (price * quantity);
        }

        return sum;
    }

    private int calculateTotal(List<Product> productList) {
        int sum = 0;
        for(int i=0; i<productList.size(); i++){
            int price = productList.get(i).getPrice();
            int quantity = productList.get(i).getQuantity();
            sum += (price * quantity);
        }

        return sum;
    }

    private List<Product> calculatePromotionList(List<Product> productList) {
        return productList.stream()
                .filter(product -> !product.getPromotion().equals(Format.NULL))
                .collect(Collectors.toList());
    }

    private List<Product> calculateProductList(Cart cart) {
        return cart.getCart().keySet().stream().toList();
    }

}
