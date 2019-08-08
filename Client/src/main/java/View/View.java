package View;

import Controller.Command.*;
import Model.Location.Coordinate;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class View {

	@FXML
	private AnchorPane page;

	@FXML
	private Canvas mapView;

	@FXML
	private AnchorPane forItem; //used for drawing the items' images.

	@FXML
	private AnchorPane forEntity; // used for drawing the entities' images.


	@FXML
	private ProgressBar energy;


	@FXML
	private Label coin;

	@FXML
	private AnchorPane chatView;

	@FXML
	private Button closeChatView;

	@FXML
	private TextField messageWindow;

	@FXML
	private Button send;

	@FXML
	private VBox messageBox;

	@FXML
	private AnchorPane chatScrolPane;

	@FXML
	private TabPane tabBagView;

	@FXML
	private VBox bagFoodVbox;

	@FXML
	private VBox bagKeysVbox;

	@FXML
	private Button eatFood;

	@FXML
	private Button putDownFood;

	@FXML
	private Button closeFood;

	@FXML
	private Button putDownKey;

	@FXML
	private Button closeKey;

	@FXML
	private Label numOfCoins;

	@FXML
	private Button closeCoins;

	@FXML
	private TabPane newTransaction;

	@FXML
	private Tab user_shop;

	@FXML
	private VBox usershopVbox;

	@FXML
	private Label totalValue;

	@FXML
	private Button closeUserShop;

	@FXML
	private Button buy;

	@FXML
	private VBox myBagVbox;

	@FXML
	private Label numOfMyCoins;

	@FXML
	private Label totalSellValue;

	@FXML
	private Button sell;

	@FXML
	private Button closeMyBag;


	private double tileWidth = 0;

	private double tileHeight = 0;

	private double image_h = 64.0;

	private double image_w = 64.0;

	private MoveCommand moveCommand = null;
	private SaveGameCommand saveGameCommand = null;
	private LogOutCommand logOutCommand= null;
	private OpenDoorCommand openDoorCommand = null;
	private PickUpCommand pickUpCommand= null;
	private ReactToCommand reactToCommand = null;

	private List<String> operations = new ArrayList<String>();


	public AnchorPane getForItem() { return forItem; }


	public ProgressBar getEnergy() { return energy; }


	public Label getCoin() { return coin; }

	public AnchorPane getChatView() { return chatView; }

	public Button getCloseChatView() { return closeChatView; }

	public TextField getMessageWindow() { return messageWindow; }

	public Button getSend() { return send; }

	public VBox getMessageBox() { return messageBox; }

	public AnchorPane getChatScrolPane() { return chatScrolPane; }

	public TabPane getTabBagView() { return tabBagView; }

	public VBox getBagFoodVbox() { return bagFoodVbox; }

	public VBox getBagKeysVbox() { return bagKeysVbox; }

	public Button getEatFood() { return eatFood; }

	public Button getPutDownFood() { return putDownFood; }

	public Button getCloseFood() { return closeFood; }

	public Button getPutDownKey() { return putDownKey; }

	public Button getCloseKey() { return closeKey; }

	public Label getNumOfCoins() { return numOfCoins; }

	public Button getCloseCoins() { return closeCoins; }

	public TabPane getNewTransaction() { return newTransaction; }

	public Tab getUser_shop() { return user_shop; }

	public VBox getUsershopVbox() { return usershopVbox; }

	public Label getTotalValue() { return totalValue; }

	public Button getBuy() { return buy;}

	public Button getCloseUserShop() { return closeUserShop; }

	public VBox getMyBagVbox() { return myBagVbox; }

	public Label getNumOfMyCoins() { return numOfMyCoins; }

	public Label getTotalSellValue() { return totalSellValue; }

	public Button getSell() { return sell; }

	public Button getCloseMyBag() { return closeMyBag; }


	public void showBag() {
		chatView.setVisible(false);
		tabBagView.setVisible(true);
	}

	public void showChat() {
		tabBagView.setVisible(false);
		chatView.setVisible(true);
	}


	public void saveGame() {
		saveGameCommand.execute();
	}

	/**
	 * Bind the mapView's property to the scene's property.
	 * Make sure the size of the canvas changes as the window size changes.
	 * Add the keyEvent listener and timer to the window.
	 *
	 * @variable mapView the canvas used to draw images on.
	 * @param scene represents the window
	 */
	public void bindScene(Scene scene) {
		// The height of the canvas is 100 lower than the height of the window.
		mapView.heightProperty().bind(scene.heightProperty().subtract(100));
		//The width of the canvas is 10 lower than the width of the window.
		mapView.widthProperty().bind(scene.widthProperty().subtract(10));

		initialKeyAction(scene);
		addTimer();

	}

	/**
	 *  Add the user action to the queue.
	 * @param scence represents the window
	 */

	public void initialKeyAction(Scene scence){
		scence.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent k) {
				operations.add(k.getCode().getName());
			}

		});


	}

	/**
	 * Invoke the command according to the name of the key after each fixed time interval.
	 */
	private void addTimer(){
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				int size = operations.size();
				//When there is action in the queue.
				if (size>0){
					// get the first action in the queue.
					String action = operations.get(0);
					try {
						if(action.equals("A") || action.equals("D")||action.equals("W")|| action.equals("S")
								||action.equals("Left") || action.equals("Right")||action.equals("Up")|| action.equals("Down")) {
							// move actions
							moveCommand.excute(action);

							//remove the action from queue.
							operations.remove(action);
						}
						else if(action.equals("Enter")){
							// interactive action
							pickUpCommand.execute();
							openDoorCommand.excute();
							reactToCommand.execute();

							//remove the action from queue.
							operations.remove(action);
						}
						else{
							// meaningless action

							//remove the action from queue.
							operations.remove(action);
						}
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}

				}
			}
		};

		Timer timer = new Timer();
		long delay = 0;
		long intevalPeriod = 30;

		// schedules the task to be run in an interval
		timer.scheduleAtFixedRate(timerTask, delay, intevalPeriod);
	}

	/**
	 * Draw the image on the canvas.
	 * @param fileName the name of the image without the filename extension.
	 * @param position the coordinate of the image need to be drawn.
	 * @return drawed when there is images drawn on the canvas, it is true.
	 */
	public boolean draw(String fileName, Coordinate position) {
		boolean drawed=false;
		GraphicsContext gContext = mapView.getGraphicsContext2D();

		// calculate the height and width of each tiles. The map is 10x10 layout.
		tileWidth=mapView.getWidth()/10;
		tileHeight = mapView.getHeight()/10;

		//create the image
		URL url = this.getClass().getResource("/images/" + fileName + ".png");
		Image image = new Image(url.toString(), image_h, image_w, false, false);

		if(image!= null){
			//Draw the image on the canvas
			gContext.drawImage(image,0, 0,image_h,image_w, position.getYCoordinate()*tileWidth,
					position.getXCoordinate()*tileHeight,tileWidth,tileHeight);
			drawed=true;
		}
		return drawed;

	}

	/**
	 * Draw the items or entities on the pane.
	 *
	 * @param fileName the name of the image without the filename extension.
	 * @param position the coordinate of the image need to be drawn.
	 * @param isItemTile whether the image is the item's image.
	 * @return imgView the created ImageView instance.
	 */
	public ImageView drawInteractive(String fileName, Coordinate position, Boolean isItemTile) {

		// calculate the height and width of each tiles. The map is 10x10 layout.
		tileWidth=mapView.getWidth()/10;
		tileHeight = mapView.getHeight()/10;

		//create the image view, load the image and place it on the correct position.
		ImageView imgView = new ImageView();
		URL url = this.getClass().getResource("/images/" + fileName + ".png");
		imgView.setLayoutX(position.getYCoordinate()*tileWidth);
		imgView.setLayoutY(position.getXCoordinate()*tileHeight);

		if(!isItemTile) {
			//set the image.
			Image image = new Image(url.toString(), image_h, image_w, false, false);
			imgView.setImage(image);
			//add the image view to the pane used for entities.
			forEntity.getChildren().add(imgView);
		}else {
			//set the image with the half size.
			Image image = new Image(url.toString(), image_h/2, image_w/2, false, false);
			imgView.setImage(image);

			//add the image view to the pane used for items.
			forItem.getChildren().add(imgView);

		}

		return imgView;
	}

	/**
	 * Clean the images drawn on the canvas.
	 */
	public void initialCanvas() {
		GraphicsContext gContext = mapView.getGraphicsContext2D();

		gContext.save();

		gContext.setFill(Color.WHITE);

		gContext.clearRect(0, 0, mapView.getWidth(), mapView.getHeight());

		gContext.setStroke(Color.BLACK);
	}

	/**
	 * Clean the image views added on the pane for items.
	 */
	public void initialForItem(){
		forItem.getChildren().clear();

	}

	/**
	 * Clean the image views added on the pane for entities.
	 */
	public void initialForEntity(){
		forEntity.getChildren().clear();
	}


	public void setMoveCommand(Command command) {
		moveCommand = (MoveCommand) command;
	}

	public void setPickUpCommand(Command command){
		pickUpCommand = (PickUpCommand) command;
	}

	public void setReactToCommand(Command command){
		reactToCommand = (ReactToCommand) command;
	}
	public void setSaveGameCommand(Command command) {
		saveGameCommand = (SaveGameCommand) command;
	}

	public void setLogOutCommand(Command command) {
		logOutCommand = (LogOutCommand) command;
	}

	public void setOpenDoorCommand(Command command){openDoorCommand = (OpenDoorCommand)command;}

	/**
	 * Before close the game, show alert to the user.
	 * Let the user choose whether to continue the game or save and exit
	 * @param primaryStage
	 */
	public void setWindowsCloseAction(Stage primaryStage) {
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent w) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Exit game");
				alert.setHeaderText("Are your sure to exit game");
				ButtonType buttonSave = new ButtonType("Exit and save");
				ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
				alert.getButtonTypes().setAll(buttonSave, buttonCancel);
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == buttonSave){
					//save and close this game
					saveGame();
					logOutCommand.execute();
					System.exit(0);

				}else if(result.get() == buttonCancel){
					// continue the game.
					alert.close();
					w.consume();
				}
			}

		});
	}


	/**
	 * Show the alert dialog to the user
	 * @param message The message need to be written in the dialog.
	 */
	public void showAlert(String message){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("");
		alert.setContentText(message);
		alert.show();
	}

}
