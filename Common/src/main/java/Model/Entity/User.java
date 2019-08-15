package Model.Entity;

import java.util.ArrayList;
import java.util.List;

public class User extends Entity {
    private String UserId;
    private Boolean isOnline;
    private List<String> openedDoors;

    public User(String id) {
        super(id);
        this.UserId = id;
        this.isOnline = true;
        this.openedDoors = new ArrayList<>();
    }

    public String reactTo(Entity entity) {
        notifyObserver();
        return  "transaction with user";
    }

    public String getUserId() {
        return UserId;
    }

    public void addOpenedDoors(String doorId){
        openedDoors.add(doorId);
    }

    public List<String> getOpenedDoors() {
        return openedDoors;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }
}
