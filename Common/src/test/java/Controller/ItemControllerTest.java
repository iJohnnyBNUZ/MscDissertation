package Controller;

import Controller.GameMediator;
import Controller.ItemController;
//import Controller.ServerMediator;
import Model.Entity.Entity;
import Model.Entity.User;
import Model.Location.Coordinate;
import Model.Location.Location;
import Model.World;
import org.junit.Before;
import org.junit.Test;


import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class ItemControllerTest extends BaseTest {
    GameMediator gameMediator = null;
    private ItemController itemController = null;
    private Location location = null;

    @Before
    public void set_up() {
        World world = new World();
        gameMediator = new GameMediator() {
            @Override
            public World getWorld() {
                return world;
            }
        };
        itemController = new ItemController(gameMediator);
        super.initWorld(gameMediator);

        location = gameMediator.getWorld().getEntityLocation("testUser");
    }
//
    @Test
    public void pickUpTest(){
        if(location == null)
            return;

        Entity entity = this.gameMediator.getWorld().getEntity("testUser");
        if (entity == null){
            return;
        }
        int beforeSize = location.getItems().size();
        int beforeBag = entity.getBag().size();

        itemController.pickUp("testUser");

        assertEquals(location.getItems().size(), beforeSize - 1);
        assertEquals(entity.getBag().size(), beforeBag + 1);

    }

    @Test
    public void dropTest(){
        if(location == null)
            return;

        Entity entity = this.gameMediator.getWorld().getEntity("testUser");
        if (entity == null){
            return;
        }

        int beforeSize = location.getItems().size();
        int beforeBag = entity.getBag().size();
        Entity user = gameMediator.getWorld().getEntity("testUser");

//        location.getEntities().put("testUser", new Coordinate(3,4));
        LocationController locationController = new LocationController(gameMediator);
        locationController.moveTo("testUser", "right");
        itemController.drop("testUser", "apple3");


        assertEquals(location.getItems().size(), beforeSize + 1);
        assertEquals(entity.getBag().size(), beforeBag - 1);
    }

    @Test
    public void eatTest(){
        if(location == null)
            return;

        Entity entity = this.gameMediator.getWorld().getEntity("testUser");
        if (entity == null){
            return;
        }

        int beforeEnergy = entity.getEnergy();
        int beforeBag = entity.getBag().size();

        itemController.eat("testUser", "apple3");


        assertEquals(entity.getEnergy(), beforeEnergy + 10);
        assertEquals(entity.getBag().size(), beforeBag - 1);
    }

    @Test
    public void exchangeTest(){

        if(location == null)
            return;

        Entity testUser = this.gameMediator.getWorld().getEntity("testUser");
        if (testUser == null){
            return;
        }
        Entity testUser2 = this.gameMediator.getWorld().getEntity("testUser2");
        if (testUser2 == null){
            return;
        }

        HashMap<String, Integer> list = new HashMap<>();
        list.put("apple", 2);
        list.put("banana", 1);

        int beforeBuyerCoin = testUser2.getCoin();
        int beforeBuyerBagSize = testUser2.getBag().size();

        int beforeSellerCoin = testUser.getCoin();
        int beforeSellerBagSize = testUser.getBag().size();
        //itemController.exchange("testUser2", "testUser", list, 30);

        assertEquals(testUser2.getCoin(), beforeBuyerCoin - 30);
        assertEquals(testUser.getCoin(), beforeSellerCoin + 30);

        assertEquals(testUser2.getBag().size(), beforeBuyerBagSize + 3);
        assertEquals(testUser.getBag().size(), beforeSellerBagSize - 3);
    }

}
