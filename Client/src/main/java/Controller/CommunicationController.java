package Controller;

import View.ChatView;

import java.util.List;

public class CommunicationController implements Controller {

    private ClientMediator clientMediator;
    private List<String> messageList;
    private ChatView chatView;

    public CommunicationController(ClientMediator clientMediator){
        this.clientMediator = clientMediator;
        //this.messageList = clientMediator.getView().getMessageListData();
    }

    public void addMessages(String message){
        messageList.add(message);
        chatView = clientMediator.getChatView();
        chatView.updateChat(messageList);
    }

	public void communicateWith(String id) {
		// TODO Auto-generated method stub
		String name = id.replaceAll("[0-9]", "");
		switch(name) {
		case "npc": System.out.println("Talk with NPC:" + id );withNPC(id);break;
		case "store": System.out.println("Talk with STORE:" + id );withStore(id);break;
		case "user" : System.out.println("Talk with USER:" + id );withUser(id);break;
		}
	}

	private void withUser(String id) {
		// TODO Auto-generated method stub
		
	}

	private void withStore(String id) {
		// TODO Auto-generated method stub
		
	}

	private void withNPC(String id) {
		// TODO Auto-generated method stub
		
	}
}

