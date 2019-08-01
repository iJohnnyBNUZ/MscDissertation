package Network.Events;

import java.io.Serializable;

public class PostEvent implements Event, Serializable {
    private String entityID;
    private String postMessage;

    public PostEvent(String entityID, String postMessage) {
        this.entityID = entityID;
        this.postMessage = postMessage;
    }

    public String getEntityID() {
        return entityID;
    }

    public String getPostMessage(){
        return postMessage;
    }

}
