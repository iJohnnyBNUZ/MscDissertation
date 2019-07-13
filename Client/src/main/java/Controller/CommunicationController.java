package Controller;

import Model.World;
import View.View;
import View.ChatView;

import java.util.LinkedList;
import java.util.List;

public class CommunicationController implements Controller {

    private GameMediator gameMediator;
    private List<String> messageList;
    private ChatView chatView;

    public CommunicationController(GameMediator gameMediator){
        this.gameMediator = gameMediator;
        //this.messageList = gameMediator.getView().getMessageListData();
    }

    public void addMessages(String message){
        messageList.add(message);
        chatView = gameMediator.getChatView();
        chatView.updateChat(messageList);
    }

	public void communicateWith(String id) {
		// TODO Auto-generated method stub
		String name = id.replaceAll("[0-9]", "");
		switch(name) {
		case "npc": System.out.println("Talk with NPC:" + id );break;
		case "store": System.out.println("Talk with STORE:" + id );break;
		case "user" : System.out.println("Talk with USER:" + id );break;
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}

