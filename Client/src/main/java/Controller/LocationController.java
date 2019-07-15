//package Controller;
//
//import Model.Item.Item;
//import Model.Item.Key;
//import Model.Location.Coordinate;
//import Model.Location.Location;
//
//public class LocationController implements Controller{
//    private ClientMediator clientMediator;
//
//    public LocationController(ClientMediator clientMediator){
//        this.clientMediator = clientMediator;
//    }
//
//    public void moveTo(String direction){
//        String uName = clientMediator.getClient().getUserName();
//        Location entityLocation = clientMediator.getWorld().getEntityLocation(uName);
//
//        Coordinate entityCoordinate = entityLocation.getEntities().get(uName);
//
//            //can not find user
//            if (entityCoordinate == null)
//                return;
//
//            switch (direction) {
//                case "left":
//                    changeUserCoordinate(entityCoordinate.getxPostion() - 1, entityCoordinate.getyPosition(), uName);
//                    break;
//                case "right":
//                    changeUserCoordinate(entityCoordinate.getxPostion() + 1, entityCoordinate.getyPosition(), uName);
//                    break;
//                case "up":
//                    changeUserCoordinate(entityCoordinate.getxPostion(), entityCoordinate.getyPosition() - 1, uName);
//                    break;
//                case "down":
//                    changeUserCoordinate(entityCoordinate.getxPostion(), entityCoordinate.getyPosition() + 1, uName);
//                    break;
//                default:
//            }
//
//    }
//
//    public void changeUserCoordinate(int positionx,int positiony,String userid){
//
//        for(Coordinate c: clientMediator.getWorld().getEntityLocation(userid).getTiles().keySet()){
//            if(c.getxPostion() == positionx && c.getyPosition() == positiony){
//                clientMediator.getWorld().getEntityLocation(userid).changeUserCoordinate(userid, c);
//                return;
//            }
//        }
//
//    }
//
//    public void openDoor(String userid){
//        for(Item item: clientMediator.getWorld().getEntity(userid).getBag()){
//            if(item instanceof Key){
//                //get currentLocation index
//                int indexOfCurrentLocation = clientMediator.getWorld().getLocations().indexOf(clientMediator.getWorld().getEntityLocation(userid));
//                //new location will be index+1 in the Location list
//                clientMediator.getWorld().setEntityLocation(userid, "location1");
//
//                //initial the user in Coordinate(0,0) in the next Location
//                int positionX=0,positionY=0;
//
//                for(Coordinate c: clientMediator.getWorld().getLocations().get(indexOfCurrentLocation+1).getTiles().keySet()){
//                    if(c.getxPostion() == positionX && c.getyPosition() == positionY){
//                        clientMediator.getWorld().getLocations().get(indexOfCurrentLocation+1).addEntity(userid,c);
//                        clientMediator.getWorld().getLocations().get(indexOfCurrentLocation).removeEntity(userid);
//                        System.out.println("USer->"+ userid+"open the door and moves to the new Location");
//                        //remove the used key from User's bag
//                        clientMediator.getWorld().getEntity(userid).getBag().remove(item);
//                        System.out.println("The key used has removed from bag!");
//                        return;
//                    }
//                }
//            }
//        }
//        System.out.println("There is no Key object in the bag!");
//    }
//}
