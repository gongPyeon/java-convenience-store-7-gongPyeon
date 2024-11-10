package store.io;

import camp.nextstep.edu.missionutils.Console;

public class IOMessage implements IOManager{
    @Override
    public void printMessage(Object message) {
        System.out.println(message);
    }

    @Override
    public String inputMessage() {
        String message = Console.readLine();
        return message;
    }
}
