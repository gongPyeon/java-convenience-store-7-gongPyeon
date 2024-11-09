package store.service;

import store.common.constant.ValidConstatns;
import store.common.parser.FileParser;
import store.domain.Product;
import store.domain.Promotions;
import store.dto.Cart;
import store.dto.Receipt;
import store.dto.OneCart;
import store.repository.ProductRepository;
import store.repository.PromotionProductRepository;
import store.repository.PromotionRepository;
import store.validator.Validator;

import java.util.*;
import java.util.stream.Collectors;

public class Service {

    private final Validator validator;
    private final ProductRepository productRepository;
    private final PromotionProductRepository promotionProductRepository;
    private final PromotionRepository promotionRepository;

    public Service(Validator validator, ProductRepository productRepository, PromotionProductRepository promotionProductRepository, PromotionRepository promotionRepository) {
        this.validator = validator;
        this.productRepository = productRepository;
        this.promotionProductRepository = promotionProductRepository;
        this.promotionRepository = promotionRepository;
    }

    public void storeProductAndPromotionsListByFile(String productFile, String promotionsFile) {
        storeProductListByFile(productFile);
        storePromotionsListByFile(promotionsFile);
    }

    private void storePromotionsListByFile(String promotionsFile) {
        List<String> promotionListByFile = FileParser.readMarkdownFile(promotionsFile);
        for (int i = 1; i < promotionListByFile.size(); i++) {
            Promotions promotion = splitPromotionList(promotionListByFile.get(i));
            validator.validateIsNull(Optional.of(promotion));
            promotionRepository.save(promotion);
        }
    }

    private Promotions splitPromotionList(String promotionLineByFile) {
        String[] productLine = promotionLineByFile.split(",");
        return new Promotions(
                productLine[0],
                Integer.parseInt(productLine[1]),
                Integer.parseInt(productLine[2]),
                productLine[3],
                productLine[4]
        );
    }

    private void storeProductListByFile(String productFile) {
        List<String> productListByFile = FileParser.readMarkdownFile(productFile);
        for (int i = 1; i < productListByFile.size(); i++) {
            Product product = splitProductList(productListByFile.get(i));
            validator.validateIsNull(Optional.of(product));
            System.out.println(product); // 어떻게 할지 생각해보기
            checkAndstoreProduct(product);
        }
    }

    private void checkAndstoreProduct(Product product) {
        if (product.getPromotion().equals("null")) {
            productRepository.save(product);
            return;
        }
        promotionProductRepository.save(product);
    }

    private Product splitProductList(String productLineByFile) {
        String[] productLine = productLineByFile.split(",");
        return new Product(
                productLine[0],
                productLine[1],
                productLine[2],
                productLine[3]);
    }

    public Cart InputCart(String cartInfo) {
        List<String> productInfoBycart = Arrays.stream(cartInfo.split(ValidConstatns.SEPARATOR))
                .map(String::trim)
                .collect(Collectors.toList());

        Map<Product, Promotions> products = createProduct(productInfoBycart);
        validator.validateProduct(products, productRepository);
        validator.validateProductNum(products, productRepository, promotionProductRepository);

//        for(int i=0; i<products.size(); i++){
//            System.out.println(products.get(i));
//        }
        return new Cart(products);
    }

    private Map<Product, Promotions> createProduct(List<String> productInfoBycart) {
        Map<Product, Promotions> products = new HashMap<>();
        for (int i = 0; i < productInfoBycart.size(); i++) {
            Product product = getProduct(productInfoBycart.get(i));
//            System.out.println("product = " + product.getPromotion());
            Promotions promotion = getPromotion(product.getPromotion());
            products.put(product, promotion);
        }

        return products;
    }

    private Promotions getPromotion(String promotionName) {
        return promotionRepository.findByName(promotionName);
    }

    private Product getProduct(String productInfoBycart) {
        productInfoBycart = productInfoBycart.substring(1, productInfoBycart.length() - 1); // 대괄호 없애기
        List<String> product = Arrays.stream(productInfoBycart.split("-"))
                .map(String::trim)
                .collect(Collectors.toList());

        String name = product.get(0);
        String price = Integer.toString(productRepository.findByName(name).getPrice());
        String quantity = product.get(1);
        String promotion = promotionProductRepository.findByName(name).getPromotion();

        return new Product(name, price, quantity, promotion);
    }

    public void updateCart(OneCart oneCart) {
        int get = oneCart.getPromotions().getGet();
        int buy = oneCart.getPromotions().getBuy();

        int userQuantity = oneCart.getProduct().getQuantity();

        int promotionCount;
        if (userQuantity > (buy + get)) { // 2+1인 품목을 7개 가져올 경우, 7 % 3 = 1
            // promotionCount = userQuantity % (buy + get);
            oneCart.getProduct().addPromotionCount();
            System.out.println("promotionCount =  " + oneCart.getProduct().getPromotionCount() + " quantity =  " + oneCart.getProduct().getQuantity());
            return;
        }
        // promotionCount = (buy + get) % userQuantity; // 2+1인 품목을 2개 가져올 경우, 3 % 2
        oneCart.getProduct().addPromotionCount();
        System.out.println("promotionCount =  " + oneCart.getProduct().getPromotionCount() + " quantity =  " + oneCart.getProduct().getQuantity());
    }

    public void updateQuantity(OneCart oneCart, int miss) {
        Product product = oneCart.getProduct();
        product.updatePromotionCount(miss);
        product.updateQunatity(miss);
        System.out.println("promotionCount =  " + product.getPromotionCount() + " quantity =  " + product.getQuantity());

    }

    public void updatePromotionCount(OneCart oneCart, int miss) {
        Product product = oneCart.getProduct();
        product.updatePromotionCount(miss); // miss 제외
        System.out.println("product.getPromotionCount() = " + product.getPromotionCount());
    }

//    public int checkPromotionStock(OneCart onecart) {
//        Product product = onecart.getProduct();
//        int userQuantity = product.getQuantity();
//
//        int promotionStock = promotionProductRepository.findQuantityByName(product.getName());
//        Product productByStock = promotionProductRepository.findByName(product.getName());
//        int generalStock = productRepository.findQuantityByName(product.getName());
//        Product productByGeneral = productRepository.findByName(product.getName());
//
//        if(promotionStock > userQuantity) {
//            productByStock.updateQuantity(userQuantity);
//            promotionProductRepository.update(productByStock);
//            return 0;
//        }
//
//        int num = userQuantity - promotionStock;
//
//        productByStock.updateQuantity(promotionStock);
//        promotionProductRepository.update(productByStock);
//        productByGeneral.updateQuantity(num);
//        productRepository.update(productByGeneral);
//
//
//        return num;
//    }

    public Receipt calculator(Cart cart, boolean checkMemberShip) {
        List<Product> productList = calculateProductList(cart);
        List<Product> promotionList = calculatePromotionList(productList);
        int total = calculateTotal(productList);
        int promotionDiscount = calculatePromotion(promotionList);
        int membershipDiscount = calculateMembership(total, checkMemberShip);
        int money = calculateMoney(total, promotionDiscount, membershipDiscount);

        return new Receipt(productList, promotionList, total, promotionDiscount, membershipDiscount, money);
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
                .filter(product -> !product.getPromotion().equals("null"))
                .collect(Collectors.toList());
    }

    private List<Product> calculateProductList(Cart cart) {
        return cart.getCart().keySet().stream().toList();
    }

    public List<OneCart> filterPromotableItems(Cart cart) {
        List<OneCart> promotableItems = new ArrayList<>();
        for (Map.Entry<Product, Promotions> entry : cart.getCart().entrySet()) {
            Product product = entry.getKey();
            Promotions promotions = entry.getValue();
            if (!validator.checkIsNull(Optional.of(promotions))) {
//                System.out.println(product + " / " + promotions);
                promotableItems.add(new OneCart(product, promotions));
            }
        }

        return promotableItems;
    }

    // 상품 수량이 프로모션 조건을 만족하는지 확인하는 함수
    public boolean isProductQuantitySufficient(int userQuantity, int requiredQuantity) {
        return userQuantity >= requiredQuantity;
    }

    // 프로모션 조건에 맞게 증정품을 가져왔는지 확인하는 함수
    public boolean isPromotionSatisfied(int userQuantity, int requiredQuantity, int get) {
        if (userQuantity % (requiredQuantity + get) == 0 || (requiredQuantity + get) % userQuantity == 0) {
            return true;
        }
        return false;
    }

    // 기존 isProductQuantityInsufficient 함수 개선
    public boolean isProductQuantityInsufficient(OneCart oneCart) {
        Product product = oneCart.getProduct();
        Promotions promotions = oneCart.getPromotions();

        boolean isQuantitySufficient = isProductQuantitySufficient(product.getQuantity(), promotions.getBuy());
        if (isQuantitySufficient) {
            boolean isPromotionValid = isPromotionSatisfied(product.getQuantity(), promotions.getBuy(), promotions.getGet());
            return isPromotionValid;
        }

        return true;
        // 상품 수량이 프로모션 조건을 만족하지 않는데, 증정품을 가져온 경우  -> 발생하지 않음
        // 상품 수량이 프로모션 조건을 만족하는데, 증정품을 가져오지 않은 경우 -> 2+1인데 2개만 가져왔을 경우 -> 물어보고 진행
        // 상품 수량이 프로모션 조건을 만족하지 않고, 증정품도 가져오지 않는 경우 -> 2+1인데 1개만 가져올 경우 -> 물어보지 않고 진행
    }

    public boolean isPromotionStockInsufficient(OneCart oneCart) {
        Product product = oneCart.getProduct();
        int userPromotionQuantity = product.getPromotionCount();
        int promotionStockQuantity = promotionProductRepository.findQuantityByName(product.getName());

        return (promotionStockQuantity >= userPromotionQuantity);
    }

    public int calculateMissingPromotionQuantity(OneCart oneCart) {
        Product product = oneCart.getProduct();
        int userPromotionCount = product.getPromotionCount();
        int promotionStockQuantity = promotionProductRepository.findQuantityByName(product.getName());

        return (userPromotionCount - promotionStockQuantity);
    }

    public void divideBuyAndGet(OneCart oneCart) {
        Product product = oneCart.getProduct();
        Promotions promotions = oneCart.getPromotions();

        int userQuantity = product.getQuantity();
        int get = userQuantity / (promotions.getBuy() + promotions.getGet());
//        System.out.println("get = " + get);

        product.setPromotionCount(get);
    }


}
