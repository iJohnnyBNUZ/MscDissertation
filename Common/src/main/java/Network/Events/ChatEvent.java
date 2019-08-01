package Network.Events;

import java.io.Serializable;

public class ChatEvent implements Event, Serializable {
    private String entityID;
    private String communicateMessage;

    public ChatEvent(String entityID, String communicateMessage) {
        this.entityID = entityID;
        this.communicateMessage = communicateMessage;
    }

    public String getEntityID() {
        return entityID;
    }

    public String getcommunicateMessage(){
        return this.communicateMessage;
    }

}
