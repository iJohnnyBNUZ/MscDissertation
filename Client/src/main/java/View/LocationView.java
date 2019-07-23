package View;

import java.util.HashMap;
import java.util.Map;

import Controller.Command.Command;
import Model.Location.Coordinate;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class LocationView{
	
	private View view = null;
	private int row=10;
	private int column=10;

	
	public LocationView(View view) {
		// TODO Auto-generated constructor stub
		this.view = view;
	}



	
	

	/** If the parameter can be a matrix, it will be easy to implemented!
	 *  E.g. use the number to represent the tiles.
	 *  	 use the String(specific Id) to represent the tiles.
	 *  Picture need to be refined after the functions are implemented.
	 * */
	 
	public void update(Map<String,Coordinate> tiles) {
		if(tiles.size() != 0) {
			Platform.runLater(new Runnable() {
                @Override public void run() {
                	view.initialBeforeDraw();
        			for(String name: tiles.keySet()) {
        				String fileName = name.replaceAll("[0-9]", "");
        				view.draw(fileName, tiles.get(name));	
        			}
    			}
			});
			
		}else {
			System.out.println("Wrong tiles size");
		}
		
	}

	/*
	 *
	 * public void test() {
		// TODO Auto-generated method stub
		Map<String,Coordinate> tmp = new HashMap<String,Coordinate>();
		int num=0;
		for(int i=0;i<row;i++) {
			for(int j=0;j<column;j++) {
				Coordinate tmp_cor = new Coordinate(i, j);
				if(j%2 == 0 && i%2 == 0) {
					tmp.put("water"+num, tmp_cor);
				}else {
					tmp.put("grass"+num, tmp_cor);
				}
				
				num++;
			}
		}
		System.out.println("tiles size"+ tmp.size());
		update(tmp);
	}
	public Coordinate chooseItemPosition() {
		//set the location to GaussianBlur effect
		//accept the user's click and get a position Coordinate
		Coordinate position = null;
		
		return position;
	}
	
	public void showAvaliable(Coordinate cor) {
		AnchorPane forImage = view.getForImage();
		forImage.setMouseTransparent(true);
		
		Map<String,Double>boundary =view.drawRectangle(cor);
		Canvas mapView = view.getMapView();
		mapView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent m) {
				// TODO Auto-generated method stub
				if(m.getX()>boundary.get("beginX")&& m.getX()<boundary.get("endX")&&
						m.getY()>boundary.get("beginY")&& m.getY()<boundary.get("endY")) {
					System.out.println("put down item at :" + m.getX()+m.getY());
					mapView.setOnMouseClicked(null);
					forImage.setMouseTransparent(false);
				}else {
					System.out.println("the place is not avaliable");
					System.out.println("position is: "+ m.getX()+m.getY());
				}
			}
			
		});
	}*/
	
	
}
