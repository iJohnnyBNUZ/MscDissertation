package Controller;

import Controller.Command.StartGameCommand;

import Controller.Network.Client;
import Model.World;
import View.IndexView;
import View.View;

public class GameMediator {
	private World world;
	private Client client = null;
	private IndexView indexView= null;
	private View view = null;
	private LocationController locationController =null;
	private ItemController itemControler= null;
	private CommunicationController CommunicationController= null;
	private UserController userController= null;
	private StartGameCommand startGameCommand = null;
	
	public GameMediator() {
		this.world = new World();
	}

	public void testClient(GameMediator gameMediator) throws Exception{
		client = new Client(gameMediator);
		
	}

	
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World newWorld) {
		this.world = newWorld;
	}

	public IndexView getIndexView() {
		return indexView;
	}

	public void setIndexView(IndexView indexView) {
		this.indexView = indexView;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}
	
	
	public void initialController() {
		this.userController = new UserController(this);
		this.startGameCommand = new StartGameCommand(userController);
		bindIndexCommand();
	}
	
	
	public void bindIndexCommand() {
		indexView.setStartGame(startGameCommand);
	}
	
	public void bindViewCommand() {
		
	}

}
