package Controller.Command;

import Controller.ClientMediator;

public class CloseReactToCommand implements Command {

    private ClientMediator clientMediator;

    public CloseReactToCommand(ClientMediator clientMediator){
        this.clientMediator = clientMediator;
    }

    public void execute(){
        this.clientMediator.setReactTo(null);
        this.clientMediator.setReactResult(null);
    }
}
