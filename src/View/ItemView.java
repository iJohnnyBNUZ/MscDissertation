package View;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import Controller.Command.Command;
import Controller.Command.PickUpCommand;
import Model.Location.Coordinate;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class ItemView{
	
	private Canvas mapView = null;
	private PickUpCommand pickUp= null;
	private GraphicsContext gContext= null;
	private int row=10;
	private int column=10;
	private double image_h = 32.0;
	private double image_w =32.0;
	private double tileWidth= 0;
	private double tileHeight = 0;
	
	public ItemView(View view) {
		mapView= view.getMapView();
		gContext = mapView.getGraphicsContext2D();
	}
	
	public void test() {
		// TODO Auto-generated method stub
		Map<String,Coordinate> tmp = new HashMap<String,Coordinate>();
		int num=0;
		for(int i=0;i<row/2;i++) {
			for(int j=0;j<column/5;j++) {
				Coordinate tmp_cor = new Coordinate(i, j);
				if(j%2 == 0 && i%2 == 0) {
					tmp.put("lemon"+num, tmp_cor);
				}else {
					tmp.put("apple"+num, tmp_cor);
				}
				
				num++;
			}
		}
		System.out.println("tiles size"+ tmp.size());
		update(tmp);
	}
	
	public void update(Map<String,Coordinate> items) {
		tileWidth=mapView.getWidth()/10;
		tileHeight = mapView.getHeight()/10;
		System.out.println("tile width: "+mapView.getWidth()/10);
		
		//something wrong, may be need another Gridpane.
		if(items.size() <= 100) {
			for(String name: items.keySet()) {
				// only the item's name consisted by 5 characters can be used by this method.
				String filename = name.substring(0,5);
				
				//create ImageView to each of the items
				URL url = this.getClass().getResource("/images/" + filename + ".png");
				final Image image = new Image(url.toString(), image_h, image_w, false, false);
				
				
				gContext.drawImage(image,0, 0,image_h,image_w, items.get(name).getyPosition()*tileWidth,
						 items.get(name).getxPostion()*tileHeight,image_h,image_w);
			}
		}else {
			System.out.println("Wrong tiles size");
		}
		
	}
	
	public void setPickUpCommand(Command command) {
		pickUp = (PickUpCommand) command;
	}
}
