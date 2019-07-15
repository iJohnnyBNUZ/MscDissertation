package Controller.Save;

import Controller.ServerMediator;

public class SaveWorld {
    private ServerMediator serverMediator;

    public SaveWorld(ServerMediator serverMediator, String saveDirectoryPath) {
        this.serverMediator = serverMediator;
    }
}
