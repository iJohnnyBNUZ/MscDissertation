package Controller.Command;

import Controller.ClientMediator;

public class CloseReactToCommand implements Command {

    private ClientMediator clientMediator;

    public CloseReactToCommand(ClientMediator clientMediator){
        this.clientMediator = clientMediator;
    }


    /**
     * Assign two variables in clientMediator to null which means the user doesn't interact with anyone
     */
    public void execute(){
        this.clientMediator.setReactTo(null);
        this.clientMediator.setReactResult(null);
    }
}
