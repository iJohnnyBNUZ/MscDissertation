package Controller;

import Model.Location.Coordinate;
import Model.Location.Tile;
import Model.World;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LocationController {
    private World world;

    public LocationController(World world){
        this.world = world;
    }
    public LocationController(){}

    public String moveTo(String userid, Coordinate c, String direction){
        HashMap<Coordinate, Tile> tiles = this.world.getCurrentLocation().getTiles();
        Map<String, Coordinate> Entities = this.world.getCurrentLocation().getEntities();

        String steam = "";

        if(tiles.containsKey(c)){
            System.out.println(direction+"kan fangxiang");
            if(direction.equals("a")){
                steam = "User "+ userid + " moves left"+changeDetails(c.getxPostion()-1,c.getyPosition(),userid)+",";
            } else if(direction.equals("d")){
                steam = "User "+ userid + " moves right"+changeDetails(c.getxPostion()+1,c.getyPosition(),userid)+",";
            }else if(direction.equals("w")){
                steam = "User "+ userid + " moves up"+changeDetails(c.getxPostion(),c.getyPosition()-1,userid)+",";
            }else if(direction.equals("s")){
                steam = "User "+ userid + " moves down"+changeDetails(c.getxPostion(),c.getyPosition()+1,userid)+",";
            }else{
                steam = "User "+ userid+ " Press a wrong direction! can not move! ";
            }
        }

        return steam;
    }

    public String changeDetails(int positionx,int positiony,String userid){
        String output = "";

        Iterator<Map.Entry<Coordinate, Tile>> iterator = this.world.getCurrentLocation().getTiles().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Coordinate, Tile> entry = iterator.next();
            if(entry.getKey().getxPostion()==positionx && entry.getKey().getyPosition() == positiony){
                this.world.getCurrentLocation().getEntities().put(userid,entry.getKey());
                System.out.println("Already update the coordinate of User:"+userid);
                output += " now is in [" + positionx +","+positiony+"], it's a "+ entry.getValue().getTerrain()+" tile";
                return output;
            }
        }
        output += "Arrived at the border, unable to move!";

        return output;
    }

    public void openDoor(String keyId){

    }
}
