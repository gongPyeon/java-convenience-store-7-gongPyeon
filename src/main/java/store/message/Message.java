package store.message;

import camp.nextstep.edu.missionutils.Console;

public class Message implements MessageManager{
    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public String inputMessage() {
        String message = Console.readLine();
        return message;
    }
}
