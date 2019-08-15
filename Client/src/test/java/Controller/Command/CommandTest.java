package Controller.Command;

import Controller.ClientMediator;
import Model.Entity.NPC;
import Model.Entity.Shop;
import Model.Entity.User;
import Model.Item.Food;
import Model.Location.*;
import Model.World;
import View.View;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CommandTest extends ApplicationTest {
    private View view = null;
    private ClientMediator  clientMediator;
    private User user;

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        //setup a world
        World world = new World();
        Location location = new Location("location0");
        world.addLocation(location);

        List<Tile> tileslist = new ArrayList<Tile>();
        Tile t1 = new Grass(true,"grass",1); //(0,0)
        Tile t2 = new Grass(true,"grass",1);
        Tile t3 = new Stone(true,"stone",2);

        tileslist.add(t1);
        tileslist.add(t2);
        tileslist.add(t3);
        List<Coordinate> clist = new ArrayList<Coordinate>();

        for(int i=0;i<3;i++){
            Coordinate c = new Coordinate(i,0);
            clist.add(c);

        }

        for (int i =0;i<clist.size();i++){
            location.addTile(clist.get(i),tileslist.get(i));
        }

        //User now is in (0,0)
        user = new User("user1");
        user.setEnergy(100);

        NPC npc = new NPC("npc1",true);
        Shop shop = new Shop("shop1");
        Food food = new Food("apple",10, 10,"food");
        world.addEntity(user);
        world.addEntity(npc);
        world.addEntity(shop);
        location.addEntity(user,clist.get(0));
        location.addEntity(npc,clist.get(2));
        location.addEntity(shop,clist.get(2));
        location.addItem(clist.get(0),food);


        clientMediator = new ClientMediator();
        clientMediator.setPrimaryStage(primaryStage);
        //initialize the controllers, commands, and observers.
        clientMediator.initialController();
        clientMediator.setWorld(world);
        clientMediator.initWorld();
        clientMediator.setUserName("user1");
        clientMediator.enterGame();
    }



    @Test
    public void PickUpCommand() {
        press(KeyCode.ENTER);

    }

    @Test
    public void SaveGameCommand() {
        clickOn("#save");
        clickOn("#saveGame");
    }

    @Test
    public void MoveCommandFalse() {
        user.setEnergy(0);
        press(KeyCode.UP);
    }

    @Test
    public void MoveCommandTrue() {
        press(KeyCode.DOWN);
    }

   
}