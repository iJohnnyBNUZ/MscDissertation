package View;

import java.net.URL;
import java.util.Map;

import Controller.Command.Command;
import Controller.Command.CommunicationCommand;
import Controller.Command.MoveCommand;
import Model.Location.Coordinate;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class EntityView{
	
	private AnchorPane mapView = null;
	private ProgressBar energy = null;
	private Label coin = null;
	private CommunicationCommand communication= null;
	private MoveCommand move = null;
	private int row=10;
	private int column=10;
	private double image_h = 16.0;
	private double image_w =16.0;
	
	public EntityView(View view) {
		mapView= view.getMapView();
		energy = view.getEnergy();
		coin = view.getCoin();
		
	}
	
	public void updateNPC(Map<String,Coordinate> npcs) {
		//something wrong, may be need another Gridpane.
		if(npcs.size() > 100) {
			for(String name: npcs.keySet()) {
				//create ImageView to each of the items
				final ImageView imagView = new ImageView();
				URL url = this.getClass().getResource("/images/" + name + ".png");
				final Image image = new Image(url.toString(), image_h, image_w, false, false);
				imagView.setImage(image);
				//map.add(imagView, npcs.get(name).getxPostion(), npcs.get(name).getyPosition());
			}
		}else {
			System.out.println("Wrong tiles size");
		}
		
	}
	
	public void updateUser(Map<String,Coordinate> users) {
		//something wrong, may be need another Gridpane.
		if(users.size() > 100) {
			for(String name: users.keySet()) {
				//create ImageView to each of the items
				final ImageView imagView = new ImageView();
				URL url = this.getClass().getResource("/images/" + name + ".png");
				final Image image = new Image(url.toString(), image_h, image_w, false, false);
				imagView.setImage(image);
				//map.add(imagView, users.get(name).getxPostion(), users.get(name).getyPosition());
			}
		}else {
			System.out.println("Wrong tiles size");
		}
		
	}
	
	public void updateStore(Map<String,Coordinate> stores) {
		//something wrong, may be need another Gridpane.
		if(stores.size() > 100) {
			for(String name: stores.keySet()) {
				//create ImageView to each of the items
				final ImageView imagView = new ImageView();
				URL url = this.getClass().getResource("/images/" + name + ".png");
				final Image image = new Image(url.toString(), image_h, image_w, false, false);
				imagView.setImage(image);
				//map.add(imagView, stores.get(name).getxPostion(), stores.get(name).getyPosition());
			}
		}else {
			System.out.println("Wrong tiles size");
		}
		
	}
	
	public void updateEnergy(int energyPoints) {
		//something wrong, may be need another Gridpane.
		if(energyPoints > 100 || energyPoints < 0) {
			energy.setProgress(energyPoints);
		}else {
			System.out.println("Wrong energy value!");
		}
		
	}
	
	public void updateCoin(int coins) {
		//something wrong, may be need another Gridpane.
		if(coins < 0) {
			coin.setText(""+coins);
		}else {
			System.out.println("Wrong coin value!");
		}
		
	}
	
	public void setCommunicationCommand(Command command) {
		communication = (CommunicationCommand) command;
	}
	
	public void setMoveCommand(Command command) {
		move = (MoveCommand) command;
	}
}
