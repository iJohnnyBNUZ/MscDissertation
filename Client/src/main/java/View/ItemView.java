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
	/*
	public void test() {
		// TODO Auto-generated method stub
		Map<String,Coordinate> tmp = new HashMap<String,Coordinate>();
		int num=0;
		for(int i=0;i<row/2;i++) {
			for(int j=0;j<column/5;j++) {
				Coordinate tmp_cor = new Coordinate(i, j);
				if(j%2 == 0 && i%2 == 0) {
					tmp.put("orange"+num, tmp_cor);
				}else {
					tmp.put("apple"+num, tmp_cor);
				}
				
				num++;
			}
		}
		System.out.println("tiles size"+ tmp.size());
		update(tmp);
	}
	
	public void test2() {
		// TODO Auto-generated method stub
		Map<String,Coordinate> tmp = new HashMap<String,Coordinate>();
		int num=0;
		for(int i=0;i<row/5;i++) {
			for(int j=0;j<column/2;j++) {
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
	}*/
	
	public void update(Map<String,Coordinate> items) {
		
		if(items.size() <= 100) {
			for(String name: items.keySet()) {
				
				String fileName = name.replaceAll("[0-9]", "");
				ImageView imgView = view.drawClickable(fileName, items.get(name), true);
				
				imgView.setId(name);
				imgView.setOnMouseClicked(new EventHandler<MouseEvent>() {

					public void handle(MouseEvent m) {
						// TODO Auto-generated method stub
						System.out.println("Item ID: " + imgView.getId());
						
						//Coordinate sCoordinate = new Coordinate(positionX,positionY);
						System.out.println("Position: "+ items.get(name).getxPostion()+" "
						+items.get(name).getyPosition());
						pickUp.execute(imgView.getId());
					}
					
				});
				
			}
		}else {
			System.out.println("Wrong tiles size");
		}
	}
	
	public void setPickUpCommand(Command command) {
		pickUp = (PickUpCommand) command;
	}
}
