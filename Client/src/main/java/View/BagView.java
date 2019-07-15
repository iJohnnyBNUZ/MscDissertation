package View;

import Controller.Command.Command;
import Controller.Command.EatCommand;
import Controller.Command.PutDownCommand;
import Model.Item.Food;
import Model.Item.Item;
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

	private Label numOfCoins;
	private TabPane tabBagView;
	private GridPane bagFood;
	private GridPane bagKeys;
	private Button eatFood;
	private Button putDownFood;
	private Button closeFood;
	private Button putDownKey;
	private Button closeKey;
	private Button closeCoins;

	private EatCommand eatCommand;
	private PutDownCommand putDownCommand;
	private String selectedItemId = null;
	private int energy = 0;

	private int row = 3;
	private int column = 3;
	private double image_h = 50.0;
	private double image_w =50.0;



	public BagView(View view) {
		// TODO Auto-generated constructor stub
		this.numOfCoins = view.getNumOfCoins();
		this.tabBagView = view.getTabBagView();
		this.eatFood = view.getEatFood();
		this.putDownFood = view.getPutDownFood();
		this.closeFood = view.getCloseFood();
		this.bagFood = view.getBagFood();
		this.putDownKey = view.getPutDownKey();
		this.closeKey= view.getCloseKey();
		this.bagKeys = view.getBagKeys();
		this.closeCoins = view.getCloseCoins();

		this.eatCommand = eatCommand;
		this.putDownCommand = putDownCommand;

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

		closeCoins.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				tabBagView.setVisible(false);
			}
		});

	}


	public void updateBag(List<Item> bag,int money){
		numOfCoins.setText(String.valueOf(money));

		/* get List<String> foodItems of food and keyItems of key */
		List<String> foodItems = new LinkedList<String>();
		List<String> keyItems = new LinkedList<String>();
		for(Item item: bag){
			if(item.getType()=="food"){
				String foodString = item.getItemID().replaceAll("[0-9]","");
				foodItems.add(foodString);
			}
			else if(item.getType()=="key"){
				String keyString = item.getItemID().replaceAll("[0-9]","");
				keyItems.add(keyString.toString());
			}
		}

		/* preparation for displaysing */
		int r = 0;
		int c = 0;
		final GaussianBlur effect = new GaussianBlur();

		/* display food in the bag */
		if(foodItems != null){
			Set<String> uniqueFoodSet = new HashSet<String>(foodItems);
			for (final String tmp_food_name : uniqueFoodSet){
				if (r > row - 1) {
					System.out.println("ERROR");
					break;
				}
				//create border pane for each item
				final BorderPane food_item = new BorderPane();
				//create label to show the number of item
				Label food_item_num = new Label("" + Collections.frequency(foodItems, tmp_food_name));
				food_item.setBottom(food_item_num);
				//create ImageView to each of the items
				final ImageView food_item_img = new ImageView();
				URL url = this.getClass().getResource("/images/" + tmp_food_name + ".png");
				final Image food_image = new Image(url.toString(), image_h, image_w, false, false);
				//create item's image.
				food_item_img.setImage(food_image);
				food_item.setCenter(food_item_img);
				food_item_img.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent mouseEvent) {
						if(food_item_img.getEffect()==effect){
							food_item_img.setEffect(null);
							selectedItemId = null;
						}
						else{
							cleanSelect(bagFood);
							food_item_img.setEffect(effect);
							//set selectedItemId
							for(Item item: bag){
								String[] name = item.getItemID().split("0|1|2|3|4|5|6|7|8|9");
								StringBuffer itemString = new StringBuffer();
								for (String s : name) {
									itemString.append(s);
								}
								if(itemString.toString().equals(tmp_food_name)){
									selectedItemId = item.getItemID();
									final Food food = (Food) item;
									energy = food.getEnergy();
									break;
								}
							}
						}
					}
				});
				//set the item's position.
				bagFood.add(food_item, c, r);
				if (c < column - 1) {
					c++;
				} else {
					c = 0;
					r++;
				}
			}
		}

		r = 0;
		c = 0;
		/* display keys in the bag */
		if(keyItems != null){
			Set<String> uniqueKeySet = new HashSet<String>(keyItems);
			for (final String tmp_key_name : uniqueKeySet){
				if (r > row - 1) {
					System.out.println("ERROR");
					break;
				}
				//create border pane for each item
				final BorderPane key_item = new BorderPane();
				//create label to show the number of item
				Label key_item_num = new Label("" + Collections.frequency(keyItems, tmp_key_name));
				key_item.setBottom(key_item_num);
				//create ImageView to each of the items
				final ImageView key_item_img = new ImageView();
				URL url = this.getClass().getResource("/images/" + tmp_key_name + ".png");
				final Image key_image = new Image(url.toString(), image_h, image_w, false, false);
				//create item's image.
				key_item_img.setImage(key_image);
				key_item.setCenter(key_item_img);
				key_item_img.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent mouseEvent) {
						if(key_item_img.getEffect()==effect){
							key_item_img.setEffect(null);
							selectedItemId = null;
						}
						else{
							cleanSelect(bagKeys);
							key_item_img.setEffect(effect);
							//set selectedItemId
							for(Item item: bag){
								String itemString = item.getItemID().replaceAll("[0-9]","");
								if(itemString.equals(tmp_key_name)){
									selectedItemId = item.getItemID();
									break;
								}
							}
						}
					}
				});
				//set the item's position.
				bagKeys.add(key_item, c, r);
				if (c < column - 1) {
					c++;
				} else {
					c = 0;
					r++;
				}
			}
		}

		eatFood.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				eatCommand.execute(selectedItemId);
			}
		});

		putDownFood.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				putDownCommand.execute(selectedItemId);
			}
		});



	}


	private void cleanSelect(GridPane gridPane){
		int size = gridPane.getChildren().size();
		for(int i=0; i<size; i++){
			BorderPane item = (BorderPane) gridPane.getChildren().get(i);
			if(item.getCenter().getEffect()!=null)
				item.getCenter().setEffect(null);
		}
	}

	public void setEatCommand(EatCommand eatCommand){
		this.eatCommand = eatCommand;
	}

	public void setPutDownCommand(PutDownCommand putDownCommand){
		this.putDownCommand = putDownCommand;
	}



}

