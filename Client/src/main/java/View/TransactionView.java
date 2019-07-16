package View;

import Controller.Command.BuyCommand;
import Controller.Command.SellCommand;
import Model.Item.Item;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.*;

public class TransactionView {

	private TabPane newTransaction;
	private Tab user_shop;
	private VBox usershopVbox;
	private Label totalValue;
	private Button buy;
	private Button closeUserShop;
	private HashMap<String,Integer> buyList = new HashMap<String,Integer>();
	private HashMap<String,Integer> sellList = new HashMap<String,Integer>();
	private VBox myBagVbox;
	private Label numOfMyCoins;
	private Label totalSellValue;
	private Button sell;
	private Button closeMyBag;

	private int row = 3;
	private int column = 3;
	private double image_h = 50.0;
	private double image_w = 50.0;
	private int buyValue;
	private int sellValue;
	private HashMap<TextField,Integer> oldBuyNum = new HashMap<TextField,Integer>();
	private HashMap<TextField,Integer> oldSellNum = new HashMap<TextField,Integer>();
	private String soldItemId = null;
	private int soldMoney = 0;

	private BuyCommand buyCommand;
	private SellCommand sellCommand;

	public TransactionView(View view) {
		// TODO Auto-generated constructor stub
		this.user_shop = view.getUser_shop();
		this.closeUserShop = view.getCloseUserShop();
		this.newTransaction = view.getNewTransaction();
		this.usershopVbox = view.getUsershopVbox();
		this.totalValue = view.getTotalValue();
		this.buy = view.getBuy();
		this.myBagVbox = view.getMyBagVbox();
		this.numOfMyCoins = view.getNumOfMyCoins();
		this.totalSellValue = view.getTotalSellValue();
		this.sell = view.getSell();
		this.closeMyBag = view.getCloseMyBag();
		this.buyCommand = buyCommand;
		this.sellCommand = sellCommand;

		closeUserShop.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				newTransaction.setVisible(false);
			}
		});

		closeMyBag.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				newTransaction.setVisible(false);
			}
		});

	}


	public void updateTransaction(List<Item> user__shop, String userShopName, List<Item> bag, int money){
		newTransaction.setVisible(true);
		user_shop.setText(userShopName);
		totalValue.setText("0");

		/* update User_ShopTab */
		usershopVbox.getChildren().clear();
		List<String> usershopItems = new LinkedList<String>();
		for (Item item : user__shop) {
			String usershopType = item.getItemID().replaceAll("[0-9]","");
			usershopItems.add(usershopType);
		}
		if(usershopItems != null){
			Set<String> uniqueUserShopSet = new HashSet<String>(usershopItems);
			for (final String tmp_name : uniqueUserShopSet){
				final ImageView usershop_item_img = new ImageView();
				final Label numOfItems = new Label();
				numOfItems.setPrefWidth(100);
				numOfItems.setAlignment(Pos.CENTER);
				final Label coins = new Label();
				coins.setPrefWidth(100);
				coins.setAlignment(Pos.CENTER);
				final Label blank = new Label();
				blank.setPrefWidth(60);
				final BorderPane numBuyPane = new BorderPane();
				final TextField numBuy = new TextField();
				oldBuyNum.put(numBuy,0);
				numBuy.setText("0");
				numBuy.setPrefWidth(50);
				numBuyPane.setCenter(numBuy);

				//set the image
				URL url = this.getClass().getResource("/images/" + tmp_name + ".png");
				final Image image = new Image(url.toString(), image_h, image_w, false, false);
				usershop_item_img.setImage(image);
				//set the number of items
				numOfItems.setText(String.valueOf(Collections.frequency(usershopItems, tmp_name)));
				//set the coins of each item
				for(Item item: user__shop){
					String itemString = item.getItemID().replaceAll("[0-9]","");
					if(itemString.equals(tmp_name)){
						coins.setText(String.valueOf(item.getCoinValue()));
						break;
					}
				}
				//set the initial number to buy
				final GridPane itemBox = new GridPane();
				itemBox.add(usershop_item_img,0,0);
				itemBox.add(numOfItems,1,0);
				itemBox.add(coins,2,0);
				itemBox.add(blank,3,0);
				itemBox.add(numBuyPane,4,0);
				usershopVbox.getChildren().add(itemBox);
				buyList.put(tmp_name,0);
				numBuy.textProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
						if(numBuy.getText()!=null && !numBuy.getText().equals("") && !numBuy.getText().equals("null")){
							buyValue = Integer.parseInt(totalValue.getText())-oldBuyNum.get(numBuy)+Integer.parseInt(coins.getText())*Integer.parseInt(numBuy.getText());
							totalValue.setText(String.valueOf(buyValue));
							if(buyList.containsKey(tmp_name)){
								buyList.put(tmp_name,Integer.parseInt(numBuy.getText()));
							}
							if(oldBuyNum.containsKey(numBuy)){
								oldBuyNum.put(numBuy,Integer.parseInt(numBuy.getText())*Integer.parseInt(coins.getText()));
							}
						}
					}
				});
			}

		}

		buy.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				buyCommand.execute(userShopName,buyList,buyValue);
			}
		});


		/* update MyBagTab */
		myBagVbox.getChildren().clear();
		numOfMyCoins.setText(String.valueOf(money));
		totalSellValue.setText("0");
		List<String> mybagItems = new LinkedList<String>();
		for (Item item : bag) {
			String mybagType = item.getItemID().replaceAll("[0-9]","");
			mybagItems.add(mybagType);
		}
		if(mybagItems != null){
			Set<String> uniqueMyBagSet = new HashSet<String>(mybagItems);
			for (final String tmp_bag_name : uniqueMyBagSet){
				final ImageView mybag_item_img = new ImageView();
				final Label numOfMyBagItems = new Label();
				numOfMyBagItems.setPrefWidth(100);
				numOfMyBagItems.setAlignment(Pos.CENTER);
				final Label mybagcoins = new Label();
				mybagcoins.setPrefWidth(100);
				mybagcoins.setAlignment(Pos.CENTER);
				final Label mybagblank = new Label();
				mybagblank.setPrefWidth(60);
				final BorderPane numSellPane = new BorderPane();
				final TextField numSell = new TextField();
				oldSellNum.put(numSell,0);
				numSell.setText("0");
				numSell.setPrefWidth(50);
				numSellPane.setCenter(numSell);

				//set the image
				URL url = this.getClass().getResource("/images/" + tmp_bag_name + ".png");
				final Image image = new Image(url.toString(), image_h, image_w, false, false);
				mybag_item_img.setImage(image);
				//set the number of items
				numOfMyBagItems.setText(String.valueOf(Collections.frequency(mybagItems, tmp_bag_name)));
				//set the coins of each item
				for(Item item: bag){
					String itemString = item.getItemID().replaceAll("[0-9]","");
					if(itemString.equals(tmp_bag_name)){
						mybagcoins.setText(String.valueOf(item.getCoinValue()));
						break;
					}
				}
				//set the initial number to sell
				final GridPane itemMyBagBox = new GridPane();
				itemMyBagBox.add(mybag_item_img,0,0);
				itemMyBagBox.add(numOfMyBagItems,1,0);
				itemMyBagBox.add(mybagcoins,2,0);
				itemMyBagBox.add(mybagblank,3,0);
				itemMyBagBox.add(numSellPane,4,0);
				myBagVbox.getChildren().add(itemMyBagBox);
				sellList.put(tmp_bag_name,0);
				numSell.textProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
						if(numSell.getText()!=null && !numSell.getText().equals("") && !numSell.getText().equals("null")){
							sellValue = Integer.parseInt(totalSellValue.getText())-oldSellNum.get(numSell)+Integer.parseInt(mybagcoins.getText())*Integer.parseInt(numSell.getText());
							totalSellValue.setText(String.valueOf(sellValue));
							if(sellList.containsKey(tmp_bag_name)){
								sellList.put(tmp_bag_name,Integer.parseInt(numSell.getText()));
							}
							if(oldSellNum.containsKey(numSell)){
								oldSellNum.put(numSell,Integer.parseInt(numSell.getText())*Integer.parseInt(mybagcoins.getText()));
							}
						}
					}
				});
			}

		}

		sell.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				sellCommand.execute(userShopName, sellList,sellValue);
			}
		});
	}

	public void setBuyCommand(BuyCommand buyCommand){
		this.buyCommand = buyCommand;
	}

	public void setSellCommand(SellCommand sellCommand){
		this.sellCommand = sellCommand;
	}


}

