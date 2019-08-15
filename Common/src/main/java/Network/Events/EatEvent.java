package Network.Events;

import java.io.Serializable;

public class EatEvent implements Event, Serializable {

    private String entityID;
    private String selectedItemID;

    public EatEvent(String entityID, String selectedItemID){
        this.entityID = entityID;
        this.selectedItemID = selectedItemID;
    }


    public String getEntityID() {
        return entityID;
    }

    public String getSelectedItemID() {
        return selectedItemID;
    }

}
