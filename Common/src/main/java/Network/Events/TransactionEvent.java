package Network.Events;

import java.io.Serializable;
import java.util.HashMap;

public class TransactionEvent implements Event, Serializable {

    private String currUser;
    private String usershopname;
    private HashMap<String,Integer> transactionList;
    private int value;
    private String EntityID;

    public TransactionEvent(String currUser, String usershopName, HashMap<String,Integer> transactionList, int value, String user){
        this.currUser = currUser;
        this.usershopname =usershopName;
        this.transactionList = transactionList;
        this.value = value;
        this.EntityID = user;
    }

    public String getCurrUser(){
        return currUser;
    }

    public String getUsershopname(){
        return usershopname;
    }

    public HashMap<String,Integer> getTransactionList() {
        return transactionList;
    }

    public int getValue(){
        return value;
    }

    public String getEntityID(){
        return EntityID;
    }
}
