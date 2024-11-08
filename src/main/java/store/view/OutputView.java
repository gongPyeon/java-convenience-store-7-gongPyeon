package store.view;

import store.common.constant.OutputConstants;
import store.message.MessageManager;

public class OutputView {

    private final MessageManager manager;

    public OutputView(MessageManager manager) {
        this.manager = manager;
    }

    public void welcome(){
        manager.printMessage(OutputConstants.WERLCOME_OUTPUT);
    }
}
