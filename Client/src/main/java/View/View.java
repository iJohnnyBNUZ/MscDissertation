package View;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class View {
	
	@FXML
    private AnchorPane page;
	
	@FXML
    private Canvas mapView;
	
	@FXML
	private GridPane map;
	

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
    
    @FXML
    private BagView bag = null;
    
    private ChatView chat = null;

    private EntityView entity = null;
    
    private ItemView item = null;
    
    private NPCView nps = null;
    
    private TransactionView tansaction = null;
    
    // This method is automatically invoked by the FXMLLoader - it's magic
    // This method must be public
    public void initialize() {
    	System.out.println("Initializing View");
    	location = new LocationView(this);
    	bag = new BagView(this);
    	chat = new ChatView(this);
    	entity = new EntityView(this);
    	item = new ItemView(this);
    	nps = new NPCView(this);
    	tansaction = new TransactionView(this);
    }
    
    
    public AnchorPane getPage() {
		return page;
	}
    
	public Canvas getMapView() {
		return mapView;
	}


	public GridPane getMap() {
		return map;
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
    	System.out.println("Save");
    }


	public void bindScene(Scene scene) {
		// TODO Auto-generated method stub
		mapView.heightProperty().bind(scene.heightProperty().subtract(100));
		mapView.widthProperty().bind(scene.widthProperty().subtract(10));
		System.out.println(scene.getHeight());
		System.out.println(mapView.getHeight());
		location.test();
	}
}
