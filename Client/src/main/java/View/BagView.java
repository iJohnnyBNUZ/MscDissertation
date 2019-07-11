package View;

import Controller.Command.Command;
import Controller.Command.EatCommand;
import Controller.Command.PutDownCommand;
import Model.Item.Item;
import Model.Location.Coordinate;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.*;

public class BagView{

	private Button eat = null;
	private Button putDown = null;
	private Button close = null;
	private TitledPane bagView = null;
	private GridPane inBag = null;

	private Label numOfCoins = null;
	private TabPane tabBagView;
	private GridPane bagFood;
	private GridPane bagKeys;
	private Button eatFood;
	private Button putDownFood;
	private Button closeFood;
	private Button putDownKey;
	private Button closeKey;

	private LocationView locationView = null;

	private int row = 3;
	private int column = 3;
	private double image_h = 50.0;
	private double image_w =50.0;
	private EatCommand eatCommand = new EatCommand();
	private PutDownCommand putDownCommand = new PutDownCommand();
	private String selectedItemId = null;
	private Coordinate position;


	public BagView(View view) {
		// TODO Auto-generated constructor stub
		this.eat = view.getEat();
		this.putDown = view.getPutDown();
		this.close = view.getClose();
		this.bagView = view.getBagView();
		this.inBag = view.getInBag();
		this.numOfCoins = view.getNumOfCoins();
		this.tabBagView = view.getTabBagView();
		this.eatFood = view.getEatFood();
		this.putDownFood = view.getPutDownFood();
		this.closeFood = view.getCloseFood();
		this.bagFood = view.getBagFood();
		this.putDownKey = view.getPutDownKey();
		this.closeKey= view.getCloseKey();
		this.bagKeys = view.getBagKeys();

		this.locationView = view.getLocationView();

		close.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				bagView.setVisible(false);
			}
		});

		closeFood.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				tabBagView.setVisible(false);
			}
		});

		closeKey.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				tabBagView.setVisible(false);
			}
		});

	}

	public void updateBag(List<Item> bag, int money){

		numOfCoins.setText(String.valueOf(money));
		inBag.getChildren().clear();
		List<String> bagItems = new LinkedList<String>();
		for(Item item: bag){
			String[] itemName = item.getItemID().split("0|1|2|3|4|5|6|7|8|9");
			StringBuffer itemType = new StringBuffer();
			for (String s : itemName) {
				itemType.append(s);
			}
			bagItems.add(itemType.toString());
		}

		int r = 0;
		int c = 0;

		if (bagItems != null) {
			//convert the list to set.
			Set<String> uniqueSet = new HashSet<String>(bagItems);
			for (final String tmp_name : uniqueSet) {
				System.out.println(tmp_name);

				//Item item_bag = null;
				if (r > row - 1) {
					System.out.println("ERROR");
					break;
				}

				//create border pane for each item
				final BorderPane item = new BorderPane();

				//create label to show the number of item
				Label item_num = new Label("" + Collections.frequency(bagItems, tmp_name));

				item.setBottom(item_num);
				System.out.println(item_num.getText());

				//create ImageView to each of the items
				final ImageView item_img = new ImageView();
				URL url = this.getClass().getResource("/images/" + tmp_name + ".png");

				final Image image = new Image(url.toString(), image_h, image_w, false, false);
				//final String style = "-fx-background-color:  #ffffff";
				final GaussianBlur effect = new GaussianBlur();

				//set image on-click effect
				item_img.setOnMouseClicked(new EventHandler<MouseEvent>() {

					public void handle(MouseEvent arg0) {

						// TODO Auto-generated method stub

						if(item_img.getEffect() == effect){
							item_img.setEffect(null);
							selectedItemId = null;
						}
						else{
							//清除其他选中的item的效果 cleanSelect();
							cleanSelect();
							item_img.setEffect(effect);
							for(int i=0; i<bag.size();i++){
								String[] name = bag.get(i).getItemID().split("0|1|2|3|4|5|6|7|8|9");
								StringBuffer type = new StringBuffer();
								for (String s : name) {
									type.append(s);
								}
								if(type.toString() == tmp_name){
									selectedItemId = bag.get(i).getItemID();
									break;
								}
							}
						}
					}



				});


				//create item's image.
				item_img.setImage(image);

				item.setCenter(item_img);
				//set the item's position.
				inBag.add(item, c, r);
				if (c < column - 1) {
					c++;
				} else {
					c = 0;
					r++;
				}

			}
		}

		eat.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				if(selectedItemId != null){
					cleanSelect();
					eatCommand.execute(selectedItemId);
				}
			}
		});

		putDown.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				if(selectedItemId != null){
					cleanSelect();
					Coordinate position = locationView.chooseItemPosition();
					putDownCommand.execute(selectedItemId,position);
				}
			}
		});


	}

	public void setEatCommand(Command command){
		this.eatCommand = (EatCommand) command;
	}

	public void setPutDownCommand(Command command){
		this.putDownCommand = (PutDownCommand) command;
	}

	private void cleanSelect() {
		// TODO Auto-generated method stub
		int size =inBag.getChildren().size();
		for(int i=0; i<size; i++){
			BorderPane item = (BorderPane) inBag.getChildren().get(i);
			if(item.getCenter().getEffect()!=null)
				item.getCenter().setEffect(null);
		}
	}




}

