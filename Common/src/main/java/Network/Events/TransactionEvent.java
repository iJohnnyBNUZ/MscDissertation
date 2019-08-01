package Network.Events;

import java.io.Serializable;
import java.util.HashMap;

public class TransactionEvent implements Event, Serializable {

    private String buyerID;
    private String sellerID;
    private HashMap<String,Integer> tranList;
    private int value;
    private String EntityID;

    public TransactionEvent(String buyerID, String sellerID, HashMap<String,Integer> tranList, int value, String EntityID){
        this.buyerID = buyerID;
        this.sellerID =sellerID;
        this.tranList = tranList;
        this.value = value;
        this.EntityID = EntityID;
    }

    public String getBuyerID(){
        return buyerID;
    }

    public String getSellerID(){
        return sellerID;
    }

    public HashMap<String,Integer> getTranList() {
        return tranList;
    }

    public int getValue(){
        return value;
    }

    public String getEntityID(){
        return EntityID;
    }
}
