package View;

import java.util.HashMap;
import java.util.Map;

import Controller.Command.Command;
import Controller.Command.PickUpCommand;
import Model.Location.Coordinate;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
public class ItemView{
	
	private View view = null;
	private PickUpCommand pickUp= null;
	private int row=10;
	private int column=10;
	
	public ItemView(View view) {
		this.view= view;
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
		
		if(items.size() <= 100) {
			for(String name: items.keySet()) {
				// only the item's name consisted by 5 characters can be used by this method.
				final String fileName = name.substring(0,5);
				ImageView imgView = view.drawClickable(fileName, items.get(name), true);
				
				imgView.setOnMouseClicked(new EventHandler<MouseEvent>() {

					public void handle(MouseEvent m) {
						// TODO Auto-generated method stub
						System.out.println(fileName);
					}
					
				});
				
				//create ImageView to each of the items
				/*URL url = this.getClass().getResource("/images/" + filename + ".png");
				final Image image = new Image(url.toString(), image_h, image_w, false, false);
				
				
				gContext.drawImage(image,0, 0,image_h,image_w, items.get(name).getyPosition()*tileWidth,
						 items.get(name).getxPostion()*tileHeight,image_h,image_w);*/
			}
		}else {
			System.out.println("Wrong tiles size");
		}
		System.out.println("for Image +   " + view.getForImage().getChildren().size());
	}
	
	public void setPickUpCommand(Command command) {
		pickUp = (PickUpCommand) command;
	}
}
