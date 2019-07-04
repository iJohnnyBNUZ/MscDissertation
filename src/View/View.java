package View;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class View {
	
	@FXML
    private AnchorPane page;
	
	
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
    
    
    
    public AnchorPane getPage() {
		return page;
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
