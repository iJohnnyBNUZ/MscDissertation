import Controller.GameMediator;

import java.io.IOException;

public class RunClient {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        GameMediator gameMediator = new GameMediator();
        gameMediator.testClient();
    }
}
