package Controller;

import Controller.Command.*;
import Controller.Network.ClientListener;
import Controller.Network.ClientUpdater;
import Controller.Observer.*;
import Model.Entity.Entity;
import Model.Location.Coordinate;
import Model.Location.Location;
import Model.World;
import Network.Events.Event;
import Utils.Observer;
import View.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ClientMediator implements GameMediator {
	private World world = new World();
	private ClientUpdater clientUpdater;
	private ClientListener clientListener;
	private String userName = null;
	private String reactTo = null;
	private String reactResult = null;

	private Boolean isInit = Boolean.FALSE;

	private Set<Observer> observerSet = new HashSet<>();
	private LinkedList<Event> eventQueue = new LinkedList<>(); //User actions waiting to be send to the sever.
	
	
	private IndexView indexView= null;
	private View view = null;
	private LocationView locationView = null;
    private BagView bagView = null;
    private PostView postView = null;
    private EntityView entityView = null;
    private ItemView itemView = null;
    private NPCView npsView = null;
    private TransactionView transactionView = null;
    
	private Stage primaryStage = null;

	private LocationController locationController =null;
	private ItemController itemController= null;
	private UserController userController= null;
	private PostController postController = null;
	private ReactToController reactToController = null;
	
	private LocationObserver locationObserver = null;
	private ItemObserver itemObserver= null;
	private PostObserver postObserver = null;
	private EntityObserver entityObserver = null;
	private BagObserver bagObserver = null;
	private ReactToObserver reactToObserver = null;
	
	private MoveCommand moveCommand = null;   
	private PickUpCommand pickUpCommand = null;
	private PutDownCommand putDownCommand = null;
	private EatCommand eatCommand = null;
	private TransactionCommand transactionCommand = null;
	private CloseReactToCommand closeReactToCommand = null;
	private ReactToCommand reactToCommand = null;
	private PostCommand postCommand = null;
	private StartGameCommand startGameCommand = null;
	private SaveGameCommand saveGameCommand = null;
	private LogOutCommand logOutCommand = null;
	private OpenDoorCommand openDoorCommand = null;

	private boolean haveObservers = false;

	public ClientMediator() {
		this.world = new World();
	}

	public void createClient() throws Exception{
		clientUpdater = new ClientUpdater(this);
	}

	public ClientUpdater getClientUpdater() {
		return clientUpdater;
	}

	public UserController getUserController(){return userController;}

	public World getWorld() {
		return world;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getReactTo() {
		return reactTo;
	}

	public void setReactTo(String reactTo) {
		this.reactTo = reactTo;
	}

	public String getReactResult() { return reactResult; }

	public void setReactResult(String reactResult) { this.reactResult = reactResult; }


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


	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	
	public LocationView getLocationView() {
		return locationView;
	}

	public BagView getBagView() {
		return bagView;
	}


	public PostView getPostView() {
		return postView;
	}


	public EntityView getEntityView() {
		return entityView;
	}

	public ItemView getItemView() {
		return itemView;
	}


	public NPCView getNPCView() {
		return npsView;
	}


	public TransactionView getTransactionView() {
		return transactionView;
	}

	public LocationController getLocationController() {
		return locationController;
	}

	public ReactToController getReactToController(){return  reactToController;}



	public ItemController getItemController() {
		return itemController;
	}


	public PostController getPostController() {
		return postController;
	}

	public LinkedList<Event> getEventQueue() {
		return eventQueue;
	}


	/**
	 *  Set each values retrieved form the newWorld to the current world.
	 *  Bind the observers to the observable object in the world.
	 * @param newWorld the world instance received from the server.
	 */
	public void initWorld(World newWorld){
		this.world.setObserverSet(observerSet);
		for(Location location: this.world.getLocations()){
			location.setObserverSet(observerSet);
			for(Entity entity: location.getEntities().keySet()){
				entity.setObserverSet(observerSet);
			}
		}
		System.out.println("initial finished!!!!!!!!!!!!!!!!");
	}

	/**
	 * If the world is changed, the observers will be notified to update views.
	 * If the user have not been created, the view won't be updated. The received world is used for username checking.
	 * If the world contain the user, bind the observers to all observable classes.
	 * @param newWorld the world received from the server
	 */
	public void setWorld(World newWorld) {
		if(newWorld.getEntityLocation(userName)!= null){
			this.world = newWorld;
			initWorld(newWorld);
			this.notifyObservers();
		}else{
			this.world = newWorld;
		}
	}

	/**
	 * Require all observers to update views.
	 */
	public void notifyObservers() {
		for(Observer observer: observerSet) {
			observer.update();
		}
	}

	/**
	 * Create the instances of controllers, observers and commands.
	 * The controller is the parameter of the Command's constructor.
	 */
	public void initialController() {
		// create controllers
		this.locationController = new LocationController(this);
		this.itemController = new ItemController(this);
		this.userController = new UserController(this);
		this.postController = new PostController(this);
		this.reactToController = new ReactToController(this);

		// create observers
		this.locationObserver = new LocationObserver(this);
		this.itemObserver = new ItemObserver(this);
		this.postObserver = new PostObserver(this);
		this.entityObserver = new EntityObserver(this);
		this.bagObserver =  new BagObserver(this);
		this.reactToObserver = new ReactToObserver(this);

		// add the observers to the observer set
		observerSet.add(this.locationObserver);
		observerSet.add(this.itemObserver);
		observerSet.add(this.postObserver);
		observerSet.add(this.entityObserver);
		observerSet.add(this.bagObserver);
		observerSet.add(this.reactToObserver);

		// create the commands
		this.moveCommand = new MoveCommand(locationController,this);
		this.openDoorCommand = new OpenDoorCommand(locationController,this);
		this.startGameCommand = new StartGameCommand(userController);
		this.putDownCommand = new PutDownCommand(itemController,this);
		this.eatCommand = new EatCommand(itemController,this);
		this.pickUpCommand = new PickUpCommand(itemController,this);
		this.reactToCommand = new ReactToCommand(reactToController,this);
		this.transactionCommand = new TransactionCommand(itemController,this);
		this.closeReactToCommand = new CloseReactToCommand(this);
		this.postCommand = new PostCommand(postController,this);
		this.saveGameCommand = new SaveGameCommand(this);
		this.logOutCommand = new LogOutCommand(this);

	}
	
	/**
	 * Bind the start game command with the index view.
	 */
	public void bindIndexCommand() {
		indexView.setStartGame(startGameCommand);
	}
	
	
	/**
	 * Create the views used after the user enter a game.
	 */
	public void initialGameView() {
		locationView = new LocationView(this.view);
    	bagView = new BagView(this.view);
    	postView = new PostView(this.view);
    	entityView = new EntityView(this.view);
    	itemView = new ItemView(this.view);
    	npsView = new NPCView(this.view);
    	transactionView = new TransactionView(this.view);

	}
	
	/**
	 * Bind the commands to the views.
	 */
	public void bindViewCommand() {
		view.setMoveCommand(moveCommand);
		view.setPickUpCommand(pickUpCommand);
		view.setReactToCommand(reactToCommand);
		view.setOpenDoorCommand(openDoorCommand);
		view.setSaveGameCommand(saveGameCommand);
		view.setLogOutCommand(logOutCommand);
		bagView.setEatCommand(eatCommand);
		bagView.setPutDownCommand(putDownCommand);
		postView.setPostCommand(postCommand);
		transactionView.setTransactionCommand(transactionCommand);
		transactionView.setCloseReactToCommand(closeReactToCommand);
		npsView.setCloseReactToCommand(closeReactToCommand);
	}
	
	/**
	 * Load the fxml file used for the game, and show it on the interface to substitute the index page.
	 * @throws IOException
	 */
	
	public void	enterGame() throws IOException {
		URL location = View.class.getResource("/view/main.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        this.primaryStage.setScene(new Scene(root, 900, 720));
        this.view = (View) fxmlLoader.getController();

        // Enable the size of the canvas is changed depends on the size of the window.
		// Bind the keyEvent listener to the window.
        this.view.bindScene(this.primaryStage.getScene());

        // Show alter when the close button is clicked.
        this.view.setWindowsCloseAction(this.primaryStage);

        this.primaryStage.show();
        
        initialGameView();
        bindViewCommand();
	}
}
