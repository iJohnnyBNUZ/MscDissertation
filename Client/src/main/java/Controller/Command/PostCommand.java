package Controller.Command;

import Controller.PostController;
import Controller.Controller;
import Controller.ClientMediator;
import Network.Events.PostEvent;

public class PostCommand implements Command {

    private PostController postController = null;
    private ClientMediator clientMediator=null;

    public PostCommand(Controller postController, ClientMediator clientMediator){
        this.postController = (PostController) postController;
        this.clientMediator = clientMediator;
    }

    public void execute(String sentence, String time){
        //add messages to messageList in World
        String userID = this.clientMediator.getUserName();
        String message = userID + " : "+sentence + "  " + time;
        postController.addPostMessage(message);
        System.out.println(message+" is posting");
        clientMediator.getEventQueue().add(new PostEvent(clientMediator.getUserName(),message));
    }
}
