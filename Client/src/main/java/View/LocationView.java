package View;


import Model.Location.Coordinate;

import java.util.Map;

public class LocationView{
	
	private View view;
	
	public LocationView(View view) {
		this.view = view;
	}



	public int update(Map<Coordinate, String> tiles) {
		int count=0;
		System.out.println("update location");
		if(tiles.size() != 0) {
			view.initialCanvas();
			for (Coordinate cor : tiles.keySet()) {
				if(view.draw(tiles.get(cor), cor))
					count++;
			}
		}else {
			System.out.println("Wrong tiles size");
		}
		return count;
	}
	
	
}
