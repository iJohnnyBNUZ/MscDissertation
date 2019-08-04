package Controller.Observer;

import Controller.ClientMediator;
import Utils.Observer;
import javafx.concurrent.Task;

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
				clientMediator.getPostView().updatePost(messageList);
			}

		};

		new Thread(progressTask).start();
	}
}