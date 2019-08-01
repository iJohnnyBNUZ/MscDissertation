package Controller;

public class PostController implements Controller{

    private GameMediator gameMediator;

    public PostController(GameMediator gameMediator){
        this.gameMediator = gameMediator;
    }

    public void addPostMessage(String postMessage){
        gameMediator.getWorld().addMessage(postMessage);
    }
}
