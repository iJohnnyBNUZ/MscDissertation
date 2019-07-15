//package Controller;
//
//import Model.Entity.Entity;
//import Model.Item.Food;
//import Model.Item.Item;
//import Model.Location.Coordinate;
//import Model.Location.Location;
//
//import java.util.HashMap;
//import java.util.List;
//
//public class ItemController implements Controller{
//    private ClientMediator clientMediator;
//    //get current userID
//    private String userID;
//
//    ItemController(ClientMediator clientMediator){
//        this.clientMediator = clientMediator;
//    }
//
//    public void pickUp(Coordinate coordinate){
//        // delete from location
//        Location location = this.clientMediator.getWorld().getEntityLocation(userID);
//        Item item = null;
//        if (location.getItems().get(coordinate) != null){
//            item = location.getItems().remove(coordinate);
//        }
//
//        // add to user's bag
//        Entity entity = this.clientMediator.getWorld().getEntity(userID);
//        entity.pickUp(item);
//    }
//
//    public void drop(String itemID, String userID){
//        // delete from bag
//        Entity entity = this.clientMediator.getWorld().getEntity(userID);
//        Item item = entity.putDown(itemID);
//
//        //add to location
//        Location location = this.clientMediator.getWorld().getEntityLocation(userID);
//        Coordinate userCoordinate = location.getEntities().get(userID);
//        if (item != null){
//            location.addItem(userCoordinate, item);
//        }
//
//    }
//
//    public void exchange(String itemID, String sellerID, String buyerID){
//        //delete from seller's bag
//        Entity seller = this.clientMediator.getWorld().getEntity(sellerID);
//        Item item = seller.putDown(itemID);
//
//        //add to buyer's bag
//        Entity buyer = this.clientMediator.getWorld().getEntity(buyerID);
//        if (item != null){
//            buyer.pickUp(item);
//        }
//    }
//
//    public void eat(String itemID){
//
//        int addEnergy = 0;
//        Food food;
//        for(Item item:this.clientMediator.getWorld().getEntity(userID).getBag()){
//            if(item.getItemID()==itemID){
//                food = (Food)item;
//                addEnergy = food.getEnergy();
//                //add user's energy
//                this.clientMediator.getWorld().getEntity(userID).increaseEnergy(addEnergy);
//                //delete from user's bag
//                this.clientMediator.getWorld().getEntity(userID).removeFromBag(food);
//                break;
//            }
//        }
//
//    }
//
//    public void buyItems(List<Item> usershop,HashMap<String, Integer> buyList, int buyValue){
//
//        //if user has enough money to buy,decrease user's money,current user add item,other user or shop remove item
//        if(this.clientMediator.getWorld().getEntity(userID).getCoin()>=buyValue){
//            this.clientMediator.getWorld().getEntity(userID).setCoin(this.clientMediator.getWorld().getEntity(userID).getCoin()-buyValue);
//            //current user add items
//            //other user or shop remove item
//        }
//        else{
//            //tell user you don't have enough money
//        }
//    }
//
//    public void sellItems(String usershopName,List<Item> usershop,HashMap<String,Integer> sellList,int sellValue){
//        //if usershopName is a user,judge if the user has enough money to buy these items
//        //if so,remove items from current user's bag,and add items to other user or shop's itemList
//        //else, the other user can't buy the items
//        //shop always can buy these items
//
//    }
//}
