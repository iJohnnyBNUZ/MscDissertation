package Controller.Command;

public class SellCommand implements Command {

    public void execute(String soldItemId, int money){
        //current user's money increases
        //current user remove item
        //shop or other user add item
        System.out.println("item "+soldItemId+" is sold, it earns "+ money +" coins");
    }
}
