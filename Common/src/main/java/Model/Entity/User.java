package Model.Entity;

import java.util.ArrayList;
import java.util.List;

public class User extends Entity {
    private String UserId;
    private Boolean isOnline;
    private List<String> openedDoors;

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public User(String id) {
        super(id);
        this.UserId = id;
        this.isOnline = true;
        this.openedDoors = new ArrayList<String>();
    }

    public String reactTo(Entity entity) {
        entity.decreaseEnergy(30);
        notifyObserver();
        return  "decrease 30 energy";
    }

    public String getUserId() {
        return UserId;
    }

    public void logout(){
        isOnline = false;
    }

    public void addOpenedDoors(String doorId){
        openedDoors.add(doorId);
    }

    public void removeOpenedDoors(String doorId){
        openedDoors.remove(doorId);
    }

    public List<String> getOpenedDoors() {
        return openedDoors;
    }

    public void setOpenedDoors(List<String> openedDoors) {
        this.openedDoors = openedDoors;
    }


}
