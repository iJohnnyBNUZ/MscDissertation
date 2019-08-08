package Controller;

import Model.Entity.User;
import Model.Location.Coordinate;
import Model.Location.Location;
import Network.Events.LoginEvent;
import javafx.concurrent.Task;

import java.io.IOException;
import java.util.Objects;

public class UserController implements Controller {

	private ClientMediator clientMediator;
	private String type;
	private String uName;
	
	public UserController(ClientMediator clientMediator){
		
	    this.clientMediator = clientMediator;
	}

	public ClientMediator getClientMediator() {
		return clientMediator;
	}

	public void setClientMediator(ClientMediator clientMediator) {
		this.clientMediator = clientMediator;
	}


	/**
	 * Connect to the server and request the world instance for server.
	 * @param type selected game type
	 * @param uName input username
	 * @param IP input IP address
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void startGame(String type, String uName, String IP) throws IOException, ClassNotFoundException {
		this.type = type;
		this.uName = uName;
		if (clientMediator.getClientUpdater().getisConnectReady()!=2){
			// connect to server using the input IP address
			if (clientMediator.getClientUpdater().connectToServer(IP)) {
				// request the world instance
				clientMediator.getClientUpdater().getWorld();

			} else {
				clientMediator.getIndexView().showMessage("Cannot Connect to the server");
			}
		}else{
			// this client have connected to the server before.
			// request world instance
			clientMediator.getClientUpdater().getWorld();
		}

	}

	/**
	 * Do username checking, online statue checking.
	 * If all of these checking passed, show the game interface.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void enterGame() throws IOException, ClassNotFoundException{
		Task<Void> progressTask = new Task<Void>(){
			boolean result = false;
			@Override
			protected Void call() throws Exception {
				// username checking
				result = isUserExist(uName);
				return null;
			}

			@Override
			protected void succeeded() {
				if(type.equals("new")) {
					if(!result){
						// username have not been used
						clientMediator.setUserName(uName);
						try {
							// request login
							clientMediator.getClientUpdater().login(uName);

							//load the game interface
							clientMediator.enterGame();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}else{
						clientMediator.getIndexView().showMessage("Username is exist!");
					}
				}else if (type.equals("continue")) {
					if (result){
						//username is in record.

						// online status checking
						if (((User)clientMediator.getWorld().getEntity(uName)).getOnline())
							clientMediator.getIndexView().showMessage("this user is already online");
						else{
							clientMediator.setUserName(uName);
							try {
								//request login
								clientMediator.getClientUpdater().login(uName);

								//load the game interface
								clientMediator.enterGame();
							} catch (IOException e) {
								e.printStackTrace();
							} catch (ClassNotFoundException e) {
								e.printStackTrace();
							}
						}
					}else{
						clientMediator.getIndexView().showMessage("Username is not exist!");
					}
				}
			}
		};
		new Thread(progressTask).start();
	}

	/**
	 * Check whether the username have been used.
	 * @param uName input username
	 * @return the result of the checking
	 */
	public Boolean isUserExist(String uName) {
		boolean result = false;
		if(clientMediator.getWorld().getEntity(uName)instanceof User){
			result = true;
		}else
			System.out.println("User "+uName+" is not exist!");

		return result;
	}
}
