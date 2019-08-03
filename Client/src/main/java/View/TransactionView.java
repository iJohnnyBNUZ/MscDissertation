package View;

import Controller.Command.CloseReactToCommand;
import Controller.Command.TransactionCommand;
import Model.Item.Item;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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

	private View view;
	private TabPane Transaction;
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
	private HashMap<ChoiceBox,Integer> oldBuyNum = new HashMap<ChoiceBox, Integer>();
	private HashMap<ChoiceBox,Integer> oldSellNum = new HashMap<ChoiceBox,Integer>();
	private String soldItemId = null;
	private int soldMoney = 0;

	private TransactionCommand transactionCommand;
	private CloseReactToCommand closeReactToCommand;

	public TransactionView(View view) {
		this.view = view;
		this.user_shop = view.getUser_shop();
		this.closeUserShop = view.getCloseUserShop();
		this.Transaction = view.getNewTransaction();
		this.usershopVbox = view.getUsershopVbox();
		this.totalValue = view.getTotalValue();
		this.buy = view.getBuy();
		this.myBagVbox = view.getMyBagVbox();
		this.numOfMyCoins = view.getNumOfMyCoins();
		this.totalSellValue = view.getTotalSellValue();
		this.sell = view.getSell();
		this.closeMyBag = view.getCloseMyBag();

		closeUserShop.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				Transaction.setVisible(false);
				closeReactToCommand.execute();
			}
		});

		closeMyBag.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				Transaction.setVisible(false);
				closeReactToCommand.execute();;
			}
		});

	}


	public void updateTransaction(List<Item> user__shop, String userShopName, List<Item> bag, int money,String result){
		view.showAlert(result);
		Transaction.setVisible(true);
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
				final ChoiceBox numBuy = new ChoiceBox();
				final ArrayList<Integer> numBuyList = new ArrayList<Integer>();
				//final TextField numBuy = new TextField();
				oldBuyNum.put(numBuy,0);
				//numBuy.setText("0");
				numBuy.setPrefWidth(50);
				numBuyPane.setCenter(numBuy);

				//set the image
				URL url = this.getClass().getResource("/images/" + tmp_name + ".png");
				final Image image = new Image(url.toString(), image_h, image_w, false, false);
				usershop_item_img.setImage(image);
				//set the number of items
				numOfItems.setText(String.valueOf(Collections.frequency(usershopItems, tmp_name)));
				for(int i = 0; i<= Collections.frequency(usershopItems, tmp_name); i++){
					numBuyList.add(i);
				}
				numBuy.setItems(FXCollections.observableArrayList(numBuyList));
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
				numBuy.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
					@Override
					public void changed(ObservableValue observableValue, Object o, Object t1) {
						buyValue = Integer.parseInt(totalValue.getText())-oldBuyNum.get(numBuy)+Integer.parseInt(coins.getText())*Integer.parseInt(numBuy.getValue().toString());
						totalValue.setText(String.valueOf(buyValue));
						if(buyList.containsKey(tmp_name)){
							buyList.put(tmp_name,Integer.parseInt(numBuy.getValue().toString()));
						}
						if(oldBuyNum.containsKey(numBuy)){
							oldBuyNum.put(numBuy,Integer.parseInt(numBuy.getValue().toString())*Integer.parseInt(coins.getText()));
						}
					}
				});
				/*
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
				*/
			}

		}

		buy.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				for(Integer buyItemNum: buyList.values()){
					if(buyItemNum != 0){
						//buyCommand.execute(userShopName,buyList,buyValue);
						transactionCommand.execute(userShopName,buyList,buyValue,"buy");
						break;
					}
				}
				//System.out.println("BuyList是" + buyList.values());
				//System.out.println("OldByNum是" + oldBuyNum);

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
				//final TextField numSell = new TextField();
				final ChoiceBox numSell = new ChoiceBox();
				final ArrayList<Integer> numSellList = new ArrayList<Integer>();
				oldSellNum.put(numSell,0);
				//numSell.setText("0");
				numSell.setPrefWidth(50);
				numSellPane.setCenter(numSell);

				//set the image
				URL url = this.getClass().getResource("/images/" + tmp_bag_name + ".png");
				final Image image = new Image(url.toString(), image_h, image_w, false, false);
				mybag_item_img.setImage(image);
				//set the number of items
				numOfMyBagItems.setText(String.valueOf(Collections.frequency(mybagItems, tmp_bag_name)));
				for(int i = 0; i<= Collections.frequency(mybagItems, tmp_bag_name); i++){
					numSellList.add(i);
				}
				numSell.setItems(FXCollections.observableArrayList(numSellList));
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
				numSell.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
					@Override
					public void changed(ObservableValue observableValue, Object o, Object t1) {
						sellValue = Integer.parseInt(totalSellValue.getText())-oldSellNum.get(numSell)+Integer.parseInt(mybagcoins.getText())*Integer.parseInt(numSell.getValue().toString());
						totalSellValue.setText(String.valueOf(sellValue));
						if(sellList.containsKey(tmp_bag_name)){
							sellList.put(tmp_bag_name,Integer.parseInt(numSell.getValue().toString()));
						}
						if(oldSellNum.containsKey(numSell)){
							oldSellNum.put(numSell,Integer.parseInt(numSell.getValue().toString())*Integer.parseInt(mybagcoins.getText()));
						}
					}
				});
				/*
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
				*/
			}

		}

		sell.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				for(Integer sellItemNum: sellList.values()){
					if(sellItemNum != 0){
						//sellCommand.execute(userShopName, sellList,sellValue);
						transactionCommand.execute(userShopName, sellList, sellValue, "sell");
						break;
					}
				}
			}
		});

	}

	public void setTransactionCommand(TransactionCommand transactionCommand) {
		this.transactionCommand = transactionCommand;
	}

	public void setCloseReactToCommand(CloseReactToCommand closeReactToCommand){
		this.closeReactToCommand = closeReactToCommand;
	}


}

