package View;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import Controller.Command.Command;
import Controller.Command.MoveCommand;
import Controller.Command.PostCommand;
import Controller.Command.SaveGameCommand;
import Controller.CommunicationController;
import Controller.LocationController;
import Model.Item.Food;
import Model.Item.Item;
import Model.Item.Key;
import Model.World;
import Model.Location.Coordinate;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class View {

	@FXML
	private AnchorPane page;

	@FXML
	private Canvas mapView;

	@FXML
	private AnchorPane forImage;

	@FXML
	private ImageView userImage;

	@FXML
	private ProgressBar energy;

	@FXML
	private ImageView coinIcon;

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
	private Label notifyWindow;

	@FXML
	private TabPane tabBagView;

	@FXML
	private GridPane bagFood;

	@FXML
	private GridPane bagKeys;

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
	
	
    // This method is automatically invoked by the FXMLLoader - it's magic
    // This method must be public
    public void initialize() {
    	System.out.println("initializeeeeeeeeeeeeeeeeeeee!!!!!!");
		setCoinImage();
		setUserImage();
    }
	public AnchorPane getPage() { return page; }

	public Canvas getMapView() { return mapView; }


	public AnchorPane getForImage() { return forImage; }
	

	public ImageView getUserImage() { return userImage; }

	public ProgressBar getEnergy() { return energy; }

	public ImageView getCoinIcon() { return coinIcon; }

	public Label getCoin() { return coin; }

	public AnchorPane getChatView() { return chatView; }

	public Button getCloseChatView() { return closeChatView; }

	public TextField getMessageWindow() { return messageWindow; }

	public Button getSend() { return send; }

	public VBox getMessageBox() { return messageBox; }

	public Label getNotifyWindow() { return notifyWindow; }

	public TabPane getTabBagView() { return tabBagView; }

	public GridPane getBagFood() { return bagFood; }

	public GridPane getBagKeys() { return bagKeys; }

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


	
	public double getTileWidth() {
		return tileWidth;
	}


	public double getTileHeight() {
		return tileHeight;
	}


	public void showBag() {
    	tabBagView.setVisible(true);
    }

	public void showTransaction() { newTransaction.setVisible(true);}

	public void showChat() { chatView.setVisible(true);}
    
	
    public void saveGame() {
    	saveGameCommand.excute();
    }


	public void bindScene(Scene scene) {
		// TODO Auto-generated method stub
		mapView.heightProperty().bind(scene.heightProperty().subtract(100));
		mapView.widthProperty().bind(scene.widthProperty().subtract(10));
		System.out.println(scene.getHeight());
		System.out.println(mapView.getHeight());
		initialBeforeDraw();
		
	}
	
	
	public void setCoinImage() {
		URL url = this.getClass().getResource("/images/coin.png");
		Image image = new Image(url.toString(), coinIcon.getFitWidth(), coinIcon.getFitHeight() , false, false);
		coinIcon.setImage(image);
	}
	
	public void setUserImage() {
		URL url = this.getClass().getResource("/images/player.png");
		Image image = new Image(url.toString(), userImage.getFitWidth(), userImage.getFitHeight() , false, false);
		userImage.setImage(image);
	}
	
	public void draw(String fileName, Coordinate position) {
		GraphicsContext gContext = mapView.getGraphicsContext2D();
		tileWidth=mapView.getWidth()/10;
		tileHeight = mapView.getHeight()/10;
		//create Image to each of the items
		URL url = this.getClass().getResource("/images/" + fileName + ".png");
		Image image = new Image(url.toString(), image_h, image_w, false, false);
		gContext.drawImage(image,0, 0,image_h,image_w, position.getyPosition()*tileWidth,
				position.getxPostion()*tileHeight,tileWidth,tileHeight);
		
	}
	
	public ImageView drawClickable(String fileName, Coordinate position, Boolean isItemTile) {
		tileWidth=mapView.getWidth()/10;
		tileHeight = mapView.getHeight()/10;
		
		//create ImageView  to each of the items
		ImageView imgView = new ImageView();
		URL url = this.getClass().getResource("/images/" + fileName + ".png");
		Image image = new Image(url.toString(), image_h, image_w, false, false);
		if(!isItemTile) {
			image = new Image(url.toString(), image_h, image_w, false, false);
		}else {
			image = new Image(url.toString(), image_h/2, image_w/2, false, false);

		}
		imgView.setImage(image);
		imgView.setLayoutX(position.getyPosition()*tileWidth);
		imgView.setLayoutY(position.getxPostion()*tileHeight);
		forImage.getChildren().add(imgView);
		return imgView;
	}
	
	public void initialBeforeDraw() {
		initialCanvas();
		initialForImage();
	}
	
	public void initialCanvas() {
		GraphicsContext gContext = mapView.getGraphicsContext2D();
		
		gContext.save();

		gContext.setFill(Color.WHITE);

		gContext.clearRect(0, 0, mapView.getWidth(), mapView.getHeight());

		gContext.setStroke(Color.BLACK);
		
		
	}
	
	
	public void initialForImage() {
		forImage.getChildren().clear();
		forImage.requestFocus();
		forImage.setOnKeyPressed(new EventHandler<KeyEvent>() {
		//double moveX = 0;
		//double moveY = 0;
			@Override
			public void handle(KeyEvent k) {
				// TODO Auto-generated method stub
				System.out.println(k.getCode().getName());
				moveCommand.excute(k.getCode().getName());
				
				
				//This method will not be used in the final project, it just used for present demo.
				/*tileWidth=mapView.getWidth()/10;
				tileHeight = mapView.getHeight()/10;
				switch(k.getCode().getName()) {
				case "Right": moveX= tileWidth;break;
				case "Left" : moveX = 0- tileWidth;break;
				case "Up" : moveY = 0-tileHeight;break;
				case "Down" : moveY = tileHeight;break;
				}
				
				ImageView moved = (ImageView) forImage.lookup("#user0");
				moved.setLayoutX(moved.getLayoutX()+moveX);
				moved.setLayoutY(moved.getLayoutY()+moveY);
				moveX=0;
				moveY=0;*/
				
			}
			
		});
	}
	
	public void setMoveCommand(Command command) {
		moveCommand = (MoveCommand) command;
	}


	
	
	/**
	 * This method is used to draw a rectangle to show where is available to drop the item.
	 * @param cor the position of the current user
	 * @return square vertex coordinates
	 */
	/*public Map<String,Double> drawRectangle(Coordinate cor) {
	
		
		// TODO Auto-generated method stub
		GraphicsContext gContext = mapView.getGraphicsContext2D();
		tileWidth=mapView.getWidth()/10;
		tileHeight = mapView.getHeight()/10;
		gContext.beginPath();
		double beginX=(cor.getxPostion()-1)*tileWidth;
		double beginY=(cor.getyPosition()-1)*tileHeight;
		
		gContext.setStroke(Color.RED);
		gContext.setLineWidth(2.0);
		gContext.strokeRect(beginX, beginY, 3*tileWidth, 3*tileHeight);
		Map<String,Double> boundary = new HashMap<String,Double>();
		boundary.put("beginX", beginX);
		boundary.put("beginY", beginY);
		boundary.put("endX", beginX + 3*tileWidth);
		boundary.put("endY", beginY+ 3*tileHeight);
		System.out.println(beginX+"   "+beginY+"   "+3*tileWidth+"   "+3*tileHeight);
		return boundary;
	}*/
	/*
	public void update() {
		initialBeforeDraw();
		location.test();
		item.test2();
		entity.testStore();
		entity.testNPC();
		entity.testUsers();
		
		System.out.println("finish updating!!!!!!!!1");
	}*/
}
