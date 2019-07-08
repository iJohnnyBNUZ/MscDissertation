package View;

import java.net.URL;
import java.util.Map;

import Controller.Command.Command;
import Controller.Command.PickUpCommand;
import Model.Location.Coordinate;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class ItemView{
	
	private Canvas mapView = null;
	private PickUpCommand pickUp= null;
	private int row=10;
	private int column=10;
	private double image_h = 16.0;
	private double image_w =16.0;
	
	public ItemView(View view) {
		mapView= view.getMapView();
	}
	
	public void update(Map<String,Coordinate> items) {
		//something wrong, may be need another Gridpane.
		if(items.size() > 100) {
			for(String name: items.keySet()) {
				//create ImageView to each of the items
				final ImageView imagView = new ImageView();
				URL url = this.getClass().getResource("/images/" + name + ".png");
				final Image image = new Image(url.toString(), image_h, image_w, false, false);
				imagView.setImage(image);
				//map.add(imagView, items.get(name).getxPostion(), items.get(name).getyPosition());
			}
		}else {
			System.out.println("Wrong tiles size");
		}
		
	}
	
	public void setPickUpCommand(Command command) {
		pickUp = (PickUpCommand) command;
	}
}
