package View;

import Model.Location.Coordinate;
import javafx.scene.image.ImageView;

import java.util.Map;
public class ItemView{
	
	private View view;
	
	public ItemView(View view) {
		this.view= view;
	}


	public void update(Map<String,Coordinate> items) {
		System.out.println("update item!");
		if(items!=null && items.size() <= 100) {
			view.initialForItem();
			for(String name: items.keySet()) {

				String fileName = name.replaceAll("[0-9]", "");
				ImageView imgView = view.drawInteractive(fileName, items.get(name), true);
				imgView.setId(name);

			}

		}else {
			System.out.println("Wrong tiles size");
		}
	}

}
