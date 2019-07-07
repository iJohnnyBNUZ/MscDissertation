package View;

import javafx.fxml.FXML;
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
    private AnchorPane mapView;
	
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
    
    private int tmp = 70;
    
    public int getTmp() {
    	return tmp;
    }
    
    
    // This method is automatically invoked by the FXMLLoader - it's magic
    // This method must be public
    public void initialize() {
    	System.out.println("initializeeeeeeeeeeeeeeeeeeee!!!!!!");
    	LocationView location = new LocationView(this);
    	BagView bag = new BagView(this);
    	ChatView chat = new ChatView(this);
    	EntityView entity = new EntityView(this);
    	NPCView nps = new NPCView(this);
    	TransactionView tansaction = new TransactionView(this);
    }
    
    
    public AnchorPane getPage() {
		return page;
	}
    
	public AnchorPane getMapView() {
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
    	System.out.println("saveeeeeeeeeeeeeeeeeeeeeeeeeee!");
    }
}
