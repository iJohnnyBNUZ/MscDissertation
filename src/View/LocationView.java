package View;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import Controller.Command.Command;
import Controller.Command.OpenDoorCommand;
import Model.Location.Coordinate;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class LocationView{
	
	private Canvas mapView = null;
	private GraphicsContext gContext;
	private OpenDoorCommand openDoor= null;
	private int row=10;
	private int column=10;
	private double image_h = 32.0;
	private double image_w = 32.0;
	private double tileWidth= 0;
	private double tileHeight = 0;

	
	public LocationView(View view) {
		// TODO Auto-generated constructor stub
		mapView = view.getMapView();
		gContext = mapView.getGraphicsContext2D();
	}



	public void test() {
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
	
	

	/** If the parameter can be a matrix, it will be easy to implemented!
	 *  E.g. use the number to represent the tiles.
	 *  	 use the String(specific Id) to represent the tiles.
	 *  Picture need to be refined after the functions are implemented.
	 * */
	 
	public void update(Map<String,Coordinate> tiles) {

		
		gContext.save();

		gContext.setFill(Color.WHITE);

		gContext.clearRect(0, 0, mapView.getWidth(), mapView.getHeight());

		gContext.setStroke(Color.BLACK);
		tileWidth=mapView.getWidth()/10;
		tileHeight = mapView.getHeight()/10;
		System.out.println("tile width: "+mapView.getWidth()/10);
		
		if(tiles.size() == 100) {
			for(String name: tiles.keySet()) {
				String filename = name.substring(0,5);
				
				//create Image to each of the items
				URL url = this.getClass().getResource("/images/" + filename + ".png");
				Image image = new Image(url.toString(), image_h, image_w, false, false);
				
				gContext.drawImage(image,0, 0,image_h,image_w, tiles.get(name).getyPosition()*tileWidth,
						 tiles.get(name).getxPostion()*tileHeight,tileWidth,tileHeight);
			
			}
		}else {
			System.out.println("Wrong tiles size");
		}
		
		
	}
	
	
	
	public void setOpenDoorCommand(Command command) {
		openDoor = (OpenDoorCommand) command;
	}
}
