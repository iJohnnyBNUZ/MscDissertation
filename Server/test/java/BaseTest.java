import Controller.GameMediator;
import Model.Entity.Entity;
import Model.Entity.NPC;
import Model.Entity.Shop;
import Model.Entity.User;
import Model.Item.Food;
import Model.Item.Item;
import Model.Location.*;
import Model.World;

public class BaseTest {

    public void initWorld(GameMediator gameMediator){
        Location l1 = new Location("location1");
        gameMediator.getWorld().addLocation(l1);

        int num=0;
        for(int i=0;i<10;i++) {
            for(int j=0;j<10;j++) {
                Coordinate tmp_cor = new Coordinate(i, j);
                if(i<5&&j<5) {
                    Tile tile = new Grass(true,"grass"+num,1);
                    l1.addTile(tmp_cor, tile);
                }else if(i<5&& j >5){
                    Tile tile = new Water(true,"water"+num,1);
                    l1.addTile(tmp_cor, tile);
                }else if(i>5) {
                    Tile tile = new Stone(true,"stone"+num,1);
                    l1.addTile(tmp_cor, tile);
                }else {
                    Tile tile = new Door(true,"door"+num,1);
                    l1.addTile(tmp_cor, tile);
                }

                num++;
            }
        }

        Item item1 = new Food("apple1", 10, 10, "food");
        Coordinate cor1 = new Coordinate(0, 0);
        Item item2 = new Food("orange1", 20, 20, "food");
        Coordinate cor2 = new Coordinate(3, 1);
        Item item3 = new Food("apple2", 10, 10, "food");
        Coordinate cor3 = new Coordinate(2, 4);

        l1.addItem(cor1,item1);
        l1.addItem(cor2, item2);
        l1.addItem(cor3, item3);

        Entity e1 = new NPC("npc1");
        Coordinate n_cor1 = new Coordinate(4, 4);
        Entity e2 = new NPC("npc2");
        Coordinate n_cor2 = new Coordinate(8, 4);

        gameMediator.getWorld().addEntity(e1);
        l1.addEntity("npc1", n_cor1);
        gameMediator.getWorld().addEntity(e2);
        l1.addEntity("npc2", n_cor2);

        Entity e3 = new Shop("store1");
        Coordinate n_cor3 = new Coordinate(0, 4);
        l1.addEntity("store1", n_cor3);
        gameMediator.getWorld().addEntity(e3);

        User user = new User("testUser");
        Coordinate n_cor4 = new Coordinate(0, 0);
        l1.addEntity("testUser", n_cor4);
        gameMediator.getWorld().addEntity(user);

    }


}
