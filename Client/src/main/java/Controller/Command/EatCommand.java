package Controller.Command;

public class EatCommand implements Command {

    public void execute(String selectedItemId){
        System.out.println("Item "+selectedItemId+" is eatten");
        //add user's energy
        //remove item from user's bag
    }
}
