package store.controller;

import store.service.Service;
import store.validator.Validator;
import store.view.InputView;
import store.view.OutputView;

public class Controller {
    private final Service service;
    private final InputView inputView;
    private final OutputView outputView;
    private final Validator validator;

    public Controller(Service service, InputView inputView, OutputView outputView, Validator validator) {
        this.service = service;
        this.inputView = inputView;
        this.outputView = outputView;
        this.validator = validator;
    }

    public void run(){
        service.storeProductAndEventListByFile();
    }
}
