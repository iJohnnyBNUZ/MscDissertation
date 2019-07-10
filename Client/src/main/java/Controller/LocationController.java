package Controller;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import Model.World;
import Model.Location.Coordinate;

public class LocationController {
	private World world =null;
	private ResourceBundle rb = ResourceBundle.getBundle("config");

    public LocationController(World world){
    	this.world =world;
    }

    public String getUserName() {
    	// Get user name from resources
    	String uName = null;
    	try{
    		uName = rb.getString("userName");
    	}
    	catch (MissingResourceException e) {
    		e.printStackTrace();
    	}
    	
    	return uName;
    }
    
    public void moveTo(String direction){
    	String uName = getUserName();
        if(uName!=null) {
        	System.out.println(uName+"move to"+direction);
        }else {
        	System.out.println("User is not exist!");
        }
  
    }
    

    public String changeDetails(int positionx,int positiony,String userid){
        String output = "";

        output += "Arrived at the border, unable to move!";

        return output;
    }

    public void openDoor(String keyId){

    }
}
