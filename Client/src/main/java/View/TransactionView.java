package View;

import Controller.Command.BuyCommand;
import Controller.Command.Command;
import Controller.Command.SellCommand;
import Model.Item.Food;
import Model.Item.Item;
import Model.Item.Key;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.*;

public class TransactionView {

	private TabPane transactionView = null;
	private GridPane userOrShop = null;
	private Button buy = null;
	private Button closeUserShop = null;
	private GridPane myBag = null;
	private Button sell = null;
	private Button closeMyBag = null;
	private Label numOfMyBagCoins = null;

	private int row = 3;
	private int column = 3;
	private double image_h = 50.0;
	private double image_w = 50.0;
	private String soldItemId = null;
	private String boughtItemId = null;
	private int soldMoney = 0;
	private int boughtMoney = 0;

	private BuyCommand buyCommand = new BuyCommand();
	private SellCommand sellCommand = new SellCommand();

	public TransactionView(View view) {
		// TODO Auto-generated constructor stub
		this.transactionView = view.getTransactionView();
		this.userOrShop = view.getUserOrShop();
		this.buy = view.getBuy();
		this.closeUserShop = view.getCloseUserShop();
		this.myBag = view.getMyBag();
		this.sell = view.getSell();
		this.closeMyBag = view.getCloseMyBag();
		this.numOfMyBagCoins = view.getNumOfMyBagCoins();

		closeUserShop.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				transactionView.setVisible(false);
			}
		});

		closeMyBag.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				transactionView.setVisible(false);
			}
		});

	}

	public void updateTransaction(List<Item> user__shop, List<Item> bag, int money) {

		//update current user's bag
		numOfMyBagCoins.setText(String.valueOf(money));
		myBag.getChildren().clear();
		List<String> mybagItems = new LinkedList<String>();
		for (Item item : bag) {
			String[] itemName = item.getItemID().split("0|1|2|3|4|5|6|7|8|9");
			StringBuffer itemType = new StringBuffer();
			for (String s : itemName) {
				itemType.append(s);
			}
			mybagItems.add(itemType.toString());
		}

		int r = 0;
		int c = 0;

		if (mybagItems != null) {
			//convert the list to set.
			Set<String> uniqueSet = new HashSet<String>(mybagItems);
			for (final String tmp_name : uniqueSet) {

				//Item item_bag = null;
				if (r > row - 1) {
					System.out.println("ERROR");
					break;
				}


				//create border pane for each item
				final BorderPane item = new BorderPane();

				//create label to show the number of item
				Label item_num = new Label("" + Collections.frequency(mybagItems, tmp_name));

				item.setBottom(item_num);

				//create ImageView to each of the items
				final ImageView bag_item_img = new ImageView();
				URL url = this.getClass().getResource("/images/" + tmp_name + ".png");
				final Image image = new Image(url.toString(), image_h, image_w, false, false);
				//final String style = "-fx-background-color:  #ffffff";
				final GaussianBlur effect = new GaussianBlur();

				//set image on-click effect
				bag_item_img.setOnMouseClicked(new EventHandler<MouseEvent>() {

					public void handle(MouseEvent arg0) {

						// TODO Auto-generated method stub

						if (bag_item_img.getEffect() == effect) {
							bag_item_img.setEffect(null);
							soldItemId = null;
						} else {
							//清除其他选中的item的效果 cleanSelect();
							cleanSelect(myBag);
							bag_item_img.setEffect(effect);
							for (int i = 0; i < bag.size(); i++) {
								if (bag.get(i).getItemID() == tmp_name) {
									soldItemId = bag.get(i).getItemID();
									soldMoney = bag.get(i).getCoinValue();
									break;
								}
							}
						}
					}


				});


				//create item's image.
				bag_item_img.setImage(image);

				item.setCenter(bag_item_img);
				//set the item's position.
				myBag.add(item, c, r);
				if (c < column - 1) {
					c++;
				} else {
					c = 0;
					r++;
				}

			}


		}

		sell.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				sellCommand.execute(soldItemId,soldMoney);
			}
		});


		//update other user's bag or shop
		userOrShop.getChildren().clear();
		List<String> userShopItems = new LinkedList<String>();
		for (Item item : user__shop) {
			String[] itemName = item.getItemID().split("0|1|2|3|4|5|6|7|8|9");
			StringBuffer itemType = new StringBuffer();
			for (String s : itemName) {
				itemType.append(s);
			}
			userShopItems.add(itemType.toString());
		}

		r = 0;
		c = 0;

		if (userShopItems != null) {
			//convert the list to set.
			Set<String> uniqueSet = new HashSet<String>(userShopItems);
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
				Label item_num = new Label("" + Collections.frequency(mybagItems, tmp_name));

				item.setBottom(item_num);

				//create ImageView to each of the items
				final ImageView usershop_item_img = new ImageView();
				URL url = this.getClass().getResource("/images/" + tmp_name + ".png");
				System.out.println(url);
				final Image image = new Image(url.toString(), image_h, image_w, false, false);
				//final String style = "-fx-background-color:  #ffffff";
				final GaussianBlur effect = new GaussianBlur();

				//set image on-click effect
				usershop_item_img.setOnMouseClicked(new EventHandler<MouseEvent>() {

					public void handle(MouseEvent arg0) {

						// TODO Auto-generated method stub

						if (usershop_item_img.getEffect() == effect) {
							usershop_item_img.setEffect(null);
							boughtItemId = null;
						} else {
							//清除其他选中的item的效果 cleanSelect();
							cleanSelect(userOrShop);
							usershop_item_img.setEffect(effect);
							for (int i = 0; i < user__shop.size(); i++) {
								if (user__shop.get(i).getItemID() == tmp_name) {
									boughtItemId = user__shop.get(i).getItemID();
									boughtMoney = user__shop.get(i).getCoinValue();
									break;
								}
							}
						}
					}


				});


				//create item's image.
				usershop_item_img.setImage(image);

				item.setCenter(usershop_item_img);
				//set the item's position.
				userOrShop.add(item, c, r);
				if (c < column - 1) {
					c++;
				} else {
					c = 0;
					r++;
				}

			}


		}

		buy.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				buyCommand.execute(boughtItemId,boughtMoney);
			}
		});

	}


	public void setBuyCommand(Command command) {
		this.buyCommand = (BuyCommand) command;
	}

	public void setSellCommand(Command command) {
		this.sellCommand = (SellCommand) command;
	}

	private void cleanSelect(GridPane container) {
		// TODO Auto-generated method stub
		int size = container.getChildren().size();
		for (int i = 0; i < size; i++) {
			BorderPane item = (BorderPane) container.getChildren().get(i);
			if (item.getCenter().getEffect() != null)
				item.getCenter().setEffect(null);
		}
	}


}

