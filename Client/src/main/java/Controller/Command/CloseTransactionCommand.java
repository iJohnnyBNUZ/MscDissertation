package Controller.Command;

import Controller.ClientMediator;

public class CloseTransactionCommand implements Command {

    private ClientMediator clientMediator = null;

    public CloseTransactionCommand(ClientMediator clientMediator){
        this.clientMediator = clientMediator;
    }

    public void execute(){
        this.clientMediator.setTransactionWith(null);
    }
}
