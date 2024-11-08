package store.service;

import store.validator.Validator;

public class Service {

    private final Validator validator;

    public Service(Validator validator) {
        this.validator = validator;
    }

    public void storeProductAndEventListByFile() {
        storeProductListByFile();
        storeEventListByFile();
    }

    private void storeEventListByFile() {
    }

    private void storeProductListByFile() {

    }
}
