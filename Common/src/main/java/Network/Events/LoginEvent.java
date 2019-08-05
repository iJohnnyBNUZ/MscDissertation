package Network.Events;

import java.io.Serializable;

public class LoginEvent implements Event, Serializable {
    private String entityID;

    public LoginEvent(String entityID) {
        this.entityID = entityID;
    }

    public String getEntityID() {
        return entityID;
    }
}
