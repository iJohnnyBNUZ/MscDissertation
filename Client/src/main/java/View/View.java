package View;

import java.net.URL;

import Controller.Command.Command;
import Controller.Command.MoveCommand;
import Model.World;
import Model.Location.Coordinate;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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
    private TitledPane bagView;

    @FXML
    private GridPane inBag;

    @FXML
    private Button eat;

    @FXML
    private Button putDown;

    @FXML
    private Button close;
    
    private LocationView location = null;
    
    private BagView bag = null;
    
    private ChatView chat = null;

    private EntityView entity = null;
    
    private ItemView item = null;
    
    private NPCView nps = null;
    
    private TransactionView tansaction = null;
    
    private double tileWidth = 0;
    
    private double tileHeight = 0;
    
    private double image_h = 64.0;
    
	private double image_w = 64.0;
	
	private MoveCommand move = null;
    // This method is automatically invoked by the FXMLLoader - it's magic
    // This method must be public
    public void initialize() {
    	System.out.println("initializeeeeeeeeeeeeeeeeeeee!!!!!!");
    	setCoinImage();
    	location = new LocationView(this);
    	bag = new BagView(this);
    	chat = new ChatView(this);
    	entity = new EntityView(this);
    	item = new ItemView(this);
    	nps = new NPCView(this);
    	tansaction = new TransactionView(this);
    	setMoveCommand(new MoveCommand(World.getInstance()));
    }
    
    
    public AnchorPane getPage() {
		return page;
	}
    
	public Canvas getMapView() {
		return mapView;
	}

	

	public AnchorPane getForImage() {
		return forImage;
	}


	public ImageView getUserImage() {
		return userImage;
	}

	public ProgressBar getEnergy() {
		return energy;
	}

	public ImageView getCoinIcon() {
		return coinIcon;
	}

	public Label getCoin() {
		return coin;
	}

	public TitledPane getBagView() {
		return bagView;
	}

	public GridPane getInBag() {
		return inBag;
	}

	public Button getEat() {
		return eat;
	}

	public Button getPutDown() {
		return putDown;
	}

	public Button getClose() {
		return close;
	}

	public void showBag() {
    	bagView.setVisible(true);
    }
    
    public void saveGame() {
    	System.out.println("saveeeeeeeeeeeeeeeeeeeeeeeeeee!");
    }


	public void bindScene(Scene scene) {
		// TODO Auto-generated method stub
		mapView.heightProperty().bind(scene.heightProperty().subtract(100));
		mapView.widthProperty().bind(scene.widthProperty().subtract(10));
		System.out.println(scene.getHeight());
		System.out.println(mapView.getHeight());
		location.test();
		item.test();
		entity.testStore();
		entity.testNPC();
		entity.testUsers();
		initialForImage();
		
	}
	
	public void setCoinImage() {
		URL url = this.getClass().getResource("/images/coin.png");
		Image image = new Image(url.toString(), coinIcon.getFitWidth(), coinIcon.getFitHeight() , false, false);
		coinIcon.setImage(image);
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
	
	public void initialCancas() {
		GraphicsContext gContext = mapView.getGraphicsContext2D();
		
		gContext.save();

		gContext.setFill(Color.WHITE);

		gContext.clearRect(0, 0, mapView.getWidth(), mapView.getHeight());

		gContext.setStroke(Color.BLACK);
		
		
	}
	
	
	public void initialForImage() {
		forImage.requestFocus();
		forImage.setOnKeyPressed(new EventHandler<KeyEvent>() {
		double moveX = 0;
		double moveY = 0;
			@Override
			public void handle(KeyEvent k) {
				// TODO Auto-generated method stub
				System.out.println(k.getCode().getName());
				move.excute(k.getCode().getName());
				//This method will not be used in the final project, it just used for present demo.
				tileWidth=mapView.getWidth()/10;
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
				moveY=0;
			}
			
		});
	}
	
	public void setMoveCommand(Command command) {
		move = (MoveCommand) command;
	}
}
