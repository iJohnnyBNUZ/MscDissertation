package Network.Events;

import java.io.Serializable;
import java.util.List;

public class PostEvent implements Event, Serializable {
    private String entityID;
    private String postMessage;
    private List<String> atUser;

    public PostEvent(String entityID, String postMessage, List<String> atUser) {
        this.entityID = entityID;
        this.postMessage = postMessage;
        this.atUser = atUser;
    }

    public String getEntityID() {
        return entityID;
    }

    public String getPostMessage(){
        return postMessage;
    }

    public List<String> getAtUser() { return atUser; }

}
