import Controller.GameMediator;
import Controller.ItemController;
import Controller.ServerMediator;
import Model.Entity.Entity;
import Model.Location.Coordinate;
import Model.Location.Location;
import Model.World;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class ItemControllerTest extends BaseTest {
    ServerMediator gameMediator = new ServerMediator();
    private ItemController itemController = null;
    private Location location = null;

    @Before
    public void set_up() {
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
        itemController.pickUp("testUser", new Coordinate(0, 0));
        assertEquals(location.getItems().size(), beforeSize - 1);
        assertEquals(entity.getBag().size(), beforeBag + 1);

    }


}
