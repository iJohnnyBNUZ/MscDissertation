package Controller;

import Model.Location.Coordinate;

public class LocationController {

    public LocationController(){}

    public String moveTo(String userid, Coordinate c, String direction){

        String steam = "";

//        if(tiles.containsKey(c)){
//            System.out.println(direction+"kan fangxiang");
//            if(direction.equals("a")){
//                steam = "User "+ userid + " moves left"+changeDetails(c.getxPostion()-1,c.getyPosition(),userid)+",";
//            } else if(direction.equals("d")){
//                steam = "User "+ userid + " moves right"+changeDetails(c.getxPostion()+1,c.getyPosition(),userid)+",";
//            }else if(direction.equals("w")){
//                steam = "User "+ userid + " moves up"+changeDetails(c.getxPostion(),c.getyPosition()-1,userid)+",";
//            }else if(direction.equals("s")){
//                steam = "User "+ userid + " moves down"+changeDetails(c.getxPostion(),c.getyPosition()+1,userid)+",";
//            }else{
//                steam = "User "+ userid+ " Press a wrong direction! can not move! ";
//            }
//        }

        return steam;
    }

    public String changeDetails(int positionx,int positiony,String userid){
        String output = "";

        output += "Arrived at the border, unable to move!";

        return output;
    }

    public void openDoor(String keyId){

    }
}
