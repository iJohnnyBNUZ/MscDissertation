package GUI;


import Model.Location.Coordinate;

import java.util.Map;

public class LocationView{
	
	private View view;
	public LocationView(View view) {
		this.view = view;
	}

	/**
	 * Draw the image of each tile on the canvas to update the view of a location.
	 * @param tiles a hashmap contains the names and the coordinates of the tiles.
	 * @return the number of the images successfully drew.
	 */
	public int update(Map<Coordinate, String> tiles) {
		int count=0;
		if(tiles.size() != 0) {
			view.initialCanvas();

			//iterate the key of the map
			for (Coordinate cor : tiles.keySet()) {
				if(view.draw(tiles.get(cor), cor))
					count++;
			}
		} else {
			view.showAlert("Wrong tiles size",null);
		}
		return count;
	}
}
