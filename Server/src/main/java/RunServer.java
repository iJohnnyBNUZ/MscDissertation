import Controller.GameMediator;


public class RunServer {
    public static void main(String[] args) throws Exception{
        GameMediator gameMediator = new GameMediator();
        gameMediator.startServer();
    }
}
