package Controller.Command;

import Controller.Controller;
import Controller.ItemController;
import Model.Item.Item;

import java.util.HashMap;
import java.util.List;

public class BuyCommand implements Command {
	
	private ItemController itemController = null;
    public BuyCommand(Controller itemController2) {
		// TODO Auto-generated constructor stub
    	this.itemController = (ItemController) itemController2;
	}

	public void execute(String usershopName,HashMap<String,Integer> buyList, int buyValue){
		System.out.println("several items are bought, it costs "+ buyValue +" coins");
		itemController.buyItems(usershopName,buyList,buyValue);
	}
}
