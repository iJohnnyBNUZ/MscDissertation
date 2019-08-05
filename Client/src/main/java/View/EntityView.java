package View;


import Model.Location.Coordinate;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;

import java.util.Map;

public class EntityView {
	
	private View view ;
	private ProgressBar energy;
	private Label coin;
	
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
				ImageView imgView = view.drawInteractive("npc", npcs.get(name), false);
				imgView.setId(name);
			}
		}else {
			view.showAlert("Wrong tiles size");
		}
		
	}
	
	public void updateUser(Map<String,Coordinate> users) {
		System.out.println("update usersssssssssssssa");
		if(users.size() <= 100) {
			for(String name: users.keySet()) {
				ImageView imgView = view.drawInteractive(name, users.get(name), false);
				imgView.setId(name);
				System.out.println(imgView.getId());
			}
				
		}else {
			view.showAlert("Wrong tiles size");
		}
	}
		
		
	
	public void updateStore(Map<String,Coordinate> stores) {
		
		if(stores.size() <= 100) {
			for(String name: stores.keySet()) {

				ImageView imgView = view.drawInteractive("store", stores.get(name), false);
				imgView.setId(name);
				
			}
		}else {
			view.showAlert("Wrong tiles size");
		}
		
	}
	
	public void updateEnergy(int energyPoints) {
		
		if(energyPoints <= 100 && energyPoints >= 0) {
			energy.setProgress((double)energyPoints/100);
			
		}else {
			view.showAlert("Wrong energy value!");
		}
		
	}
	
	public void updateCoin(int coins) {
		
		if(coins > 0) {
			coin.setText(""+coins);
			
		}else {
			view.showAlert("Wrong coin value!");
		}
		
	}
	
}
