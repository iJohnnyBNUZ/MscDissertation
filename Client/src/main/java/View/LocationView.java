package View;

import java.util.HashMap;
import java.util.Map;

import Controller.Command.Command;
import Controller.Command.OpenDoorCommand;
import Model.Location.Coordinate;
import javafx.scene.canvas.GraphicsContext;

public class LocationView{
	
	private View view = null;
	private OpenDoorCommand openDoor= null;
	private int row=10;
	private int column=10;

	
	public LocationView(View view) {
		// TODO Auto-generated constructor stub
		this.view = view;
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

		view.initialCancas();
		if(tiles.size() == 100) {
			for(String name: tiles.keySet()) {
				String fileName = name.substring(0,5);
				
				view.draw(fileName, tiles.get(name));
				//create Image to each of the items
				/*URL url = this.getClass().getResource("/images/" + filename + ".png");
				Image image = new Image(url.toString(), image_h, image_w, false, false);
				
				gContext.drawImage(image,0, 0,image_h,image_w, tiles.get(name).getyPosition()*tileWidth,
						 tiles.get(name).getxPostion()*tileHeight,tileWidth,tileHeight);
			*/
			}
		}else {
			System.out.println("Wrong tiles size");
		}
		
	}

	public Coordinate chooseItemPosition() {
		//set the location to GaussianBlur effect
		//accept the user's click and get a position Coordinate
		Coordinate position = null;
		return position;
	}
	
	
	
	public void setOpenDoorCommand(Command command) {
		openDoor = (OpenDoorCommand) command;
	}
}
