package Controller.Command;

import Model.Location.Coordinate;

public class PutDownCommand implements Command {

    public void execute(String selectedItem, Coordinate position){
        System.out.println("Item "+selectedItem+" is put down on this position");
    }
}
