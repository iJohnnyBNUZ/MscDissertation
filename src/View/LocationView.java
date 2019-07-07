package View;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import Controller.Command.Command;
import Controller.Command.OpenDoorCommand;
import Model.Location.Coordinate;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class LocationView{
	
	private AnchorPane mapView = null;
	private OpenDoorCommand openDoor= null;
	private int row=10;
	private int column=10;
	private double image_h = 46.0;
	private double image_w = 46.0;

	
	public LocationView(View view) {
		// TODO Auto-generated constructor stub
		mapView = view.getMapView();
	}



	public void test() {
		// TODO Auto-generated method stub
		Map<String,Coordinate> tmp = new HashMap<String,Coordinate>();
		int num=0;
		for(int i=0;i<row;i++) {
			for(int j=0;j<column/2;j++) {
				Coordinate tmp_cor = new Coordinate(i, j);
				tmp.put("grass"+num, tmp_cor);
				num++;
			}
			for(int j=column/2;j<column;j++) {
				Coordinate tmp_cor = new Coordinate(i, j);
				tmp.put("water"+num, tmp_cor);
				num++;
			}
		}
		System.out.println("tiles size"+ tmp.size());
		update(tmp);
	}

	public void update(Map<String,Coordinate> tiles) {
		mapView.getChildren().clear();
		if(tiles.size() == 100) {
			for(String name: tiles.keySet()) {
				String filename = name.substring(0,5);
				//create ImageView to each of the items
				final ImageView imagView = new ImageView();
				URL url = this.getClass().getResource("/images/" + filename + ".png");
				final Image image = new Image(url.toString(), image_h, image_w, false, false);
				imagView.setImage(image);
				mapView.getChildren().add(imagView);
			}
		}else {
			System.out.println("Wrong tiles size");
		}
		ImageView imagView = new ImageView();
		URL url = this.getClass().getResource("/images/" + "door" + ".png");
		final Image image = new Image(url.toString(), image_h, image_w, false, false);
		imagView.setImage(image);
		mapView.getChildren().add(imagView);
	}
	
	public void setOpenDoorCommand(Command command) {
		openDoor = (OpenDoorCommand) command;
	}
}
