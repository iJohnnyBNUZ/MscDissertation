package Controller.Save;

import Controller.ServerMediator;
import Model.Entity.Entity;
import Model.Location.Location;
import com.google.gson.JsonArray;

public class SaveWorld {
    private ServerMediator serverMediator;
    private String saveDirectoryPath;
    private SaveLocation saveLocation;
    private SaveEntity saveEntity;

    public SaveWorld(ServerMediator serverMediator, String saveDirectoryPath) {
        this.serverMediator = serverMediator;
        this.saveDirectoryPath = saveDirectoryPath;
        this.saveLocation = new SaveLocation(serverMediator);
        this.saveEntity = new SaveEntity(serverMediator);
    }

    public void saveWorld() {
        saveLocations();
        saveUsers();
    }

    public void saveLocations() {
        JsonArray savedLocations = new JsonArray();
        for (Location location : serverMediator.getWorld().getLocations()) {
            savedLocations.add(saveLocation.formatLocationAsJson(location));
        }
    }

    public void saveUsers() {
        JsonArray savedUsers = new JsonArray();
        for (Entity user : serverMediator.getWorld().getEntities()) {
            savedUsers.add(saveEntity.formatUserAsJson(user));
        }
    }
}
