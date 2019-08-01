package View;


import java.util.Map;

import Controller.Command.Command;
import Controller.Command.ReactToCommand;
import Model.Location.Coordinate;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;

public class EntityView {
	
	private View view = null;
	private ProgressBar energy = null;
	private Label coin = null;
	private ReactToCommand communication= null;
	private int row=10;
	private int column=10;
	
	public EntityView(View view) {
		energy = view.getEnergy();
		coin = view.getCoin();
		this.view = view;
		
	}

	public void initialBeforeDraw(){
		view.initialForEntity();
	}

	public void updateNPC(final Map<String,Coordinate> npcs) {

		if(npcs.size() <= 100) {
			for(String name: npcs.keySet()) {
				ImageView imgView = view.drawClickable("npc", npcs.get(name), false);
				imgView.setId(name);
			}
		}else {
			System.out.println("Wrong tiles size");
		}
		
	}
	
	public void updateUser(Map<String,Coordinate> users) {
		System.out.println("update usersssssssssssssa");
		if(users.size() <= 100) {
			for(String name: users.keySet()) {
				ImageView imgView = view.drawClickable("player", users.get(name), false);
				imgView.setId(name);
				System.out.println(imgView.getId());
			}
				
		}else {
			System.out.println("Wrong tiles size");
		}
	}
		
		
	
	public void updateStore(Map<String,Coordinate> stores) {
		
		if(stores.size() <= 100) {
			for(String name: stores.keySet()) {

				ImageView imgView = view.drawClickable("store", stores.get(name), false);
				imgView.setId(name);
				
			}
		}else {
			System.out.println("Wrong tiles size");
		}
		
	}
	
	public void updateEnergy(int energyPoints) {
		
		if(energyPoints <= 100 && energyPoints >= 0) {
			energy.setProgress((double)energyPoints/100);
			
		}else {
			System.out.println("Wrong energy value!");
		}
		
	}
	
	public void updateCoin(int coins) {
		
		if(coins > 0) {
			coin.setText(""+coins);
			
		}else {
			System.out.println("Wrong coin value!");
		}
		
	}
	
	public void setCommunicationCommand(Command command) {
		communication = (ReactToCommand) command;
	}
	
}
