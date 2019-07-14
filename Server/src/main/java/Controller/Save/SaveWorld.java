package Controller.Save;

import Controller.GameMediator;

public class SaveWorld {
    private GameMediator gameMediator;

    public SaveWorld(GameMediator gameMediator, String saveDirectoryPath) {
        this.gameMediator = gameMediator;
    }
}
