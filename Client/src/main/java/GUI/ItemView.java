package GUI;

import Model.Location.Coordinate;
import javafx.scene.image.ImageView;

import java.util.Map;
public class ItemView{
	
	private View view;
	
	public ItemView(View view) {
		this.view= view;
	}


	/**
	 * * Draw the image of each item on the user interface.
	 * @param items a hashmap contains the names and the coordinates of the items.
	 * @return count the number of the images successfully drew.
	 */
	public int update(Map<String,Coordinate> items) {
		int count=0;
		if(items!=null && items.size() <= 100) {
			view.initialForItem();
			//iterate the key of the map
			for(String name: items.keySet()) {

				String fileName = name.replaceAll("[0-9]", "");
				ImageView imgView = view.drawInteractive(fileName, items.get(name), true);
				count++;
			}

		}else {
			view.showAlert("Wrong items size",null);
		}
		return count;
	}

}
