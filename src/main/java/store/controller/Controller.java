package store.controller;

import store.common.constant.InputConstants;
import store.common.format.Format;
import store.domain.Product;
import store.domain.Promotions;
import store.domain.User;
import store.dto.Cart;
import store.dto.Receipt;
import store.dto.OneCart;
import store.service.Service;
import store.service.StockService;
import store.validator.Validator;
import store.view.InputView;
import store.view.OutputView;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Controller {
    private final Service service;
    private final StockService stockService;
    private final InputView inputView;
    private final OutputView outputView;
    private final Validator validator;

    private final User user;

    public Controller(Service service, StockService stockService, InputView inputView, OutputView outputView, Validator validator, User user) {
        this.service = service;
        this.stockService = stockService;
        this.inputView = inputView;
        this.outputView = outputView;
        this.validator = validator;
        this.user = user;
    }

    public void run(){
        while(true) {
            outputView.welcome();

            stockService.storeProductAndPromotionsListByFile(Format.PRODUCT_FILE, Format.PROMOION_FILE);
            outputView.printProduct(service.getProductSet());
            Cart cart = InputProductNameAndNum();

            List<OneCart> promotableItems = service.filterPromotableItems(cart); // 유효한 프로모션 아이템만 가져오기

            for (OneCart oneCart : promotableItems) {
                service.divideBuyAndGet(oneCart); // 증정품이 무엇인지 카운트
                if (!service.isProductQuantityInsufficient(oneCart)) // 프로모션 목록인데, 증정품을 누락할 경우
                    InputAddProductByPromotion(oneCart);
                if (!service.isPromotionStockInsufficient(oneCart)) // 프로모션 재고가 부족할 경우
                    InputCheckPromotionStock(oneCart);
            }

            boolean checkMemberShip = InputCheckMemberShip();
            Receipt receipt = service.calculator(cart, checkMemberShip);
            service.updateStock(cart);
            outputView.print(receipt);
            if(!InputAddionalPurchase())
                break;
        }
    }

    private boolean InputAddionalPurchase() {
        while(true){
            try{
                String response = inputView.askForAdditionalPurchase(); // 그러면 증정품을 더 가져올거냐고 물어봐
                validator.validateResponseFormat(response); // 응답 조건이 맞아?
                if(response.equals("Y"))
                    return true;
                return false;
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private boolean InputCheckMemberShip() {
        while(true){
            try{
                if(user.isMembership()) {
                    String response = inputView.checkMemberShip();
                    validator.validateResponseFormat(response);
                    return user.checkMemberShip(response);
                }
                return false;
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private void InputCheckPromotionStock(OneCart oneCart) {
        while(true){
            try{
                int miss = service.calculateMissingPromotionQuantity(oneCart);
                String response = inputView.checkPromotion(oneCart, miss); // miss개는 프로모션 적용이 안되는데 계산할거냐고 물어봐
                validator.validateResponseFormat(response);

                if(response.equals("Y"))
                    service.updatePromotionCount(oneCart, miss);
                if(response.equals("N"))
                    service.updateQuantity(oneCart, miss);
                return;
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private void InputAddProductByPromotion(OneCart onecart) {
        while(true){
            try{
                String response = inputView.addProductByPromotion(onecart); // 그러면 증정품을 더 가져올거냐고 물어봐
                validator.validateResponseFormat(response); // 응답 조건이 맞아?
                if(response.equals("Y")) // Y이면
                    service.updateCart(onecart); // 증정품 추가 -> 총 개수 늘리기
                return;
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private Cart InputProductNameAndNum() {
        while(true){
            try{
                String cartInfo = inputView.purchaseProduct();
                validator.validateFormat(cartInfo);
                return service.InputCart(cartInfo);
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
