package Network.Events;

import java.io.Serializable;

public class EatEvent implements Event, Serializable {

    private String EntityID;
    private String selectedItemID;

    public EatEvent(String EntityID, String selectedItemID){
        this.EntityID = EntityID;
        this.selectedItemID = selectedItemID;
    }


    public String getEntityID() {
        return EntityID;
    }

    public String getSelectedItemID() {
        return selectedItemID;
    }

}
