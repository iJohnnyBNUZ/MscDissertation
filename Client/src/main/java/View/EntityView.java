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

	/**
	 * Draw the image of each npc on the user interface
	 * @param npcs a hashmap contains the names and the coordinates of the npcs.
	 * @return count the number of the images successfully drew.
	 */
	public int updateNPC(Map<String,Coordinate> npcs) {
		int count=0;
		if(npcs.size() <= 100) {

			//iterate the key of the map
			for(String name: npcs.keySet()) {
				ImageView imgView = view.drawInteractive("npc", npcs.get(name), false);
				count++;
			}
		}else {
			view.showAlert("Wrong npcs size",null);
		}
		return count;
	}

	/**
	 * Draw the image of each user on the user interface
	 * @param users a hashmap contains the names and the coordinates of the users.
	 * @return count the number of the images successfully drew.
	 */
	public int updateUser(Map<String,Coordinate> users) {
		int count = 0;
		if(users.size() <= 100) {
			//iterate the key of the map
			for(String name: users.keySet()) {
				ImageView imgView = view.drawInteractive(name, users.get(name), false);
				count++;
			}
				
		}else {
			view.showAlert("Wrong tiles size",null);
		}
		return count;
	}


	/**
	 * Draw the image of each store on the user interface
	 * @param stores a hashmap contains the names and the coordinates of the stores.
	 * @return count the number of the images successfully drew.
	 */
	public int updateStore(Map<String,Coordinate> stores) {
		int count=0;
		if(stores.size() <= 100) {
			//iterate the key of the map
			for(String name: stores.keySet()) {

				ImageView imgView = view.drawInteractive("store", stores.get(name), false);
				count++;
				
			}
		}else {
			view.showAlert("Wrong tiles size",null);
		}
		return count;
	}

	/**
	 * Change the value of the energy bar
	 * @param energyPoints the energy points owned by the user
	 */
	public void updateEnergy(int energyPoints) {
		
		if(energyPoints <= 100 && energyPoints >= 0) {

			//convert quantity to percentage
			energy.setProgress((double)energyPoints/100);
			
		}else {
			view.showAlert("Wrong energy value!",null);
		}
		
	}

	/**
	 * Change the text of the label used to show the number of coins.
	 * @param coins the number of coins owned by the user.
	 */
	public void updateCoin(int coins) {
		if(coins > 0) {
			coin.setText(""+coins);
		}else {
			view.showAlert("Wrong coin value!",null);
		}
		
	}
	
}
