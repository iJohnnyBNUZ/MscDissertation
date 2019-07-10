package Controller.Command;

public class BuyCommand implements Command {

    public void execute(String boughtItemId, int money){
        //current user's money decreases
        //current user's bag add item
        //shop or other user remove item
        System.out.println("item "+boughtItemId+" is bought, it costs "+ money +" coins");
    }
}
