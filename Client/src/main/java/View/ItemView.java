package View;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import Controller.Command.Command;
import Controller.Command.PickUpCommand;
import Model.Location.Coordinate;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
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

	
	public void update(Map<String,Coordinate> items) {
		System.out.println("update item!");
		if(items!=null && items.size() <= 100) {
			Platform.runLater(new Runnable() {
				@Override public void run() {
					view.initialForItem();
					for(String name: items.keySet()) {

						String fileName = name.replaceAll("[0-9]", "");
						ImageView imgView = view.drawClickable(fileName, items.get(name), true);
						imgView.setId(name);
						imgView.setOnMouseClicked(new EventHandler<MouseEvent>() {

							public void handle(MouseEvent m) {
								System.out.println("Item ID: " + imgView.getId());

								//Coordinate sCoordinate = new Coordinate(positionX,positionY);
								System.out.println("Position: "+ items.get(name).getxPostion()+" "
										+items.get(name).getyPosition());
								pickUp.execute();
							}

						});


					}
				}
			});

		}else {
			System.out.println("Wrong tiles size");
		}
	}
	
	public void setPickUpCommand(Command command) {
		pickUp = (PickUpCommand) command;
	}
}
