package Controller.Command;

import Controller.ClientMediator;
import Controller.Controller;
import Controller.PostController;
import Network.Events.PostEvent;

import java.util.List;

public class PostCommand implements Command {

    private PostController postController;
    private ClientMediator clientMediator;

    public PostCommand(Controller postController, ClientMediator clientMediator){
        this.postController = (PostController) postController;
        this.clientMediator = clientMediator;
    }

    /**
     * Invoke the addPostMessage method in the postController.
     * Add the PostEvent to the queue if this action is processed
     * @param sentence The message that the user posts
     * @param time The time when the user posts the message
     * @param atUser A list that contains the users who are @ by the current user
     */
    public void execute(String sentence, String time, List<String> atUser){
        //add messages to messageList in World
        String userID = this.clientMediator.getUserName();
        String message = userID + " : "+sentence + "  " + time;
        postController.addPostMessage(message);
        clientMediator.getEventQueue().add(new PostEvent(clientMediator.getUserName(), message, atUser));
    }
}
