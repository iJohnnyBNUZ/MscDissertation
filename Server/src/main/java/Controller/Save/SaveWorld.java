package Controller.Save;

import Controller.ServerMediator;
import Model.Entity.Entity;
import Model.Location.Location;
import com.google.gson.JsonArray;

public class SaveWorld {
    private ServerMediator serverMediator;
    private String saveDirectoryPath;
    private WriteFile writeFile;

    public SaveWorld(ServerMediator serverMediator, String saveDirectoryPath) {
        this.serverMediator = serverMediator;
        this.saveDirectoryPath = saveDirectoryPath;
        this.writeFile = new WriteFile();
    }

    public void saveWorld() {
        saveLocations();
        saveUsers();
    }

    private void saveLocations() {
        JsonArray savedLocations = new JsonArray();
        for (Location location : serverMediator.getWorld().getLocations()) {
            savedLocations.add(serverMediator.getLocationSaver().saveLocationAsJson(location));
        }
        System.out.println("Saving locations");
        writeFile.writeJsonArray(savedLocations, saveDirectoryPath + "/Locations/Locations.json");
    }

    private void saveUsers() {
        JsonArray savedUsers = new JsonArray();
        for (Entity user : serverMediator.getWorld().getEntities()) {
            savedUsers.add(serverMediator.getEntitySaver().saveEntityAsJson(user));
        }
        System.out.println("Saving users");
        writeFile.writeJsonArray(savedUsers, saveDirectoryPath + "/Entities/Entities.json");
    }
}
