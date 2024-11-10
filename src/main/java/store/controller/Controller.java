package store.controller;

import store.common.handler.InputHandler;
import store.common.format.Format;
import store.domain.User;
import store.dto.Cart;
import store.dto.Receipt;
import store.dto.OneCart;
import store.service.CartService;
import store.service.PromotionService;
import store.service.ReceiptService;
import store.service.StockService;
import store.view.InputView;
import store.view.OutputView;

import java.util.List;

public class Controller {
    private final ReceiptService receiptService;
    private final StockService stockService;
    private final CartService cartService;
    private final PromotionService promotionService;
    private final InputView inputView;
    private final OutputView outputView;
    private final InputHandler inputHandler;

    private final User user;

    public Controller(ReceiptService receiptService, StockService stockService, CartService cartService, PromotionService promotionService, InputView inputView, OutputView outputView, InputHandler inputHandler, User user) {
        this.receiptService = receiptService;
        this.stockService = stockService;
        this.cartService = cartService;
        this.promotionService = promotionService;
        this.inputView = inputView;
        this.outputView = outputView;
        this.inputHandler = inputHandler;
        this.user = user;
    }

    public void run(){
        stockService.storeProductAndPromotionsListByFile(Format.PRODUCT_FILE, Format.PROMOION_FILE);
        while(true) {
            process();
            if(!inputAdditionalPurchase()) break;
        }
    }

    private void process() {
        outputView.welcome();
        outputView.printProduct(receiptService.getProductSet());
        Cart cart = inputPurchaseProduct(); // 사용자가 바구니에 넣은 product(promotion)

        List<OneCart> promotableItems = promotionService.filterPromotableItems(cart); // 바구니에서 유효한 프로모션 아이템만 가져오기
        handlePromotions(promotableItems);

        Receipt receipt = receiptService.calculator(cart, inputCheckMemberShip());
        stockService.updateStock(cart);
        outputView.print(receipt);
    }

    private void handlePromotions(List<OneCart> promotableItems) {
        for (OneCart oneCart : promotableItems) {
            promotionService.divideBuyAndGet(oneCart); // 아이템의 개수를 보고 증정품이 무엇인지 카운트
            if (!promotionService.isProductQuantityInsufficient(oneCart)) // 프로모션 목록인데, 증정품을 누락할 경우
                inputAddProductByPromotion(oneCart);
            if (!promotionService.isPromotionStockInsufficient(oneCart)) // 프로모션 재고가 부족할 경우
                inputCheckPromotionStock(oneCart);
        }
    }

    private boolean inputAdditionalPurchase() {
        return inputHandler.handleYesNoInput(inputView::askForAdditionalPurchase);
    }

    private boolean inputCheckMemberShip() {
        if (user.isMembership()) {
            return inputHandler.handleYesNoInput(inputView::checkMemberShip);
        }
        return false;
    }

    private void inputCheckPromotionStock(OneCart oneCart) {
        int miss = promotionService.calculateMissingPromotionQuantity(oneCart);
        if(inputHandler.handleYesNoInput(() -> inputView.checkPromotion(oneCart, miss))){
            promotionService.updatePromotionCount(oneCart, miss);
            return;
        }
        promotionService.updateQuantity(oneCart, miss);
    }

    private void inputAddProductByPromotion(OneCart onecart) {
        if (inputHandler.handleYesNoInput(() -> inputView.addProductByPromotion(onecart))) {
            cartService.updateCart(onecart); // 증정품 추가 -> 총 개수 늘리기
        }
    }

    private Cart inputPurchaseProduct() { // 핸들러로 빼기 실패
        while(true){
            try{
                String cartInfo = inputView.purchaseProduct();
                return cartService.InputCart(cartInfo);
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
