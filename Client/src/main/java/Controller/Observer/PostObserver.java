package Controller.Observer;

import Controller.ClientMediator;
import Model.Entity.Entity;
import Model.Entity.User;
import Utils.Observer;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PostObserver implements Observer {

	private ClientMediator clientMediator;

	public PostObserver(ClientMediator clientMediator) {
		this.clientMediator = clientMediator;
	}


	//I don't know when to use that !
	@Override
	public void update() {
		Task<Void> progressTask = new Task<Void>(){
			List<String> messageList = new LinkedList<String>();

			@Override
			protected Void call() throws Exception {
				messageList = clientMediator.getWorld().getMessageList();
				return null;
			}

			@Override
			protected void succeeded() {
				super.succeeded();
				ArrayList<String> atUserList = new ArrayList<String>();
                for(Entity entity: clientMediator.getWorld().getEntities()){
                	if(entity instanceof User && ((User) entity).getOnline()==true && !entity.getEntityID().equals(clientMediator.getUserName())){
						atUserList.add(((User) entity).getUserId());
					}
				}
				clientMediator.getPostView().updatePost(messageList,atUserList);
				if(clientMediator.getAtUser()!=null){
					if(clientMediator.getAtUser().contains(clientMediator.getUserName())){
						String message = clientMediator.getMessage().substring(clientMediator.getAtFrom().length()+2);
						String header = "You got a message from " + clientMediator.getAtFrom() + " : ";
						clientMediator.getView().showAlert(message,header);
					}
					clientMediator.setAtFrom(null);
					clientMediator.setAtUser(null);
					clientMediator.setMessage(null);
				}
			}

		};

		new Thread(progressTask).start();
	}
}
