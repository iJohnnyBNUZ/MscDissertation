package Controller;

public class PostController implements Controller{

    private GameMediator gameMediator;

    public PostController(GameMediator gameMediator){
        this.gameMediator = gameMediator;
    }

    /**
     * Add the message to the messageList of world
     * @param postMessage Message that the user posts
     */
    public void addPostMessage(String postMessage){
        gameMediator.getWorld().addMessage(postMessage);
    }
}
