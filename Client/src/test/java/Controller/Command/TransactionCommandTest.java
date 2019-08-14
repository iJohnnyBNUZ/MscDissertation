package Controller.Command;

import Controller.ClientMediator;
import Controller.ItemController;
import Model.Entity.User;
import Model.Item.Food;
import View.View;
import javafx.concurrent.Task;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class TransactionCommandTest {

    private ClientMediator clientMediator;
    private ItemController itemController;
    private TransactionCommand transactionCommand;
    private  View view;
    private HashMap<String,Integer> tranList;

    @Before
    public void setUp(){
        clientMediator = new ClientMediator();
        itemController = new ItemController(clientMediator);
        transactionCommand = new TransactionCommand(itemController,clientMediator);
        view = new View();
        clientMediator.setView(view);
        User user0 = new User("user0");
        User user1 = new User("user1");
        clientMediator.getWorld().addEntity(user0);
        clientMediator.getWorld().addEntity(user1);
        clientMediator.getWorld().getEntity("user0").getBag().add(new Food("apple0",10,10,"food"));
        clientMediator.getWorld().getEntity("user0").getBag().add(new Food("apple1",10,10,"food"));
        clientMediator.getWorld().getEntity("user0").getBag().add(new Food("apple2",10,10,"food"));
        clientMediator.getWorld().getEntity("user0").getBag().add(new Food("lemon0",10,10,"food"));
        clientMediator.getWorld().getEntity("user0").getBag().add(new Food("lemon1",10,10,"food"));
        clientMediator.getWorld().getEntity("user0").getBag().add(new Food("lemon2",10,10,"food"));

    }

    @Test
    public void executeTest(){

        Task<Void> progressTask = new Task<Void>(){

            @Override
            protected Void call() throws Exception{
                clientMediator.setUserName("user0");
                tranList.put("apple",1);
                clientMediator.getWorld().getEntity("user0").setCoin(5);
                clientMediator.getWorld().getEntity("user1").setCoin(5);
                assertEquals("user0",clientMediator.getUserName());
                assertNotNull(itemController.exchange("user1","user0",tranList,10,"user0"));
                transactionCommand.execute("user1",tranList,10,"sell");
                return null;
            }

            @Override
            protected void succeeded(){
                clientMediator.getWorld().getEntity("user1").setCoin(10);
                assertNull(itemController.exchange("user1","user0",tranList,10,"user0"));
                transactionCommand.execute("user1",tranList,10,"sell");
                clientMediator.getWorld().getEntity("user1").setCoin(15);
                assertNull(itemController.exchange("user1","user0",tranList,10,"user0"));
                transactionCommand.execute("user1",tranList,10,"sell");

                tranList.clear();
                tranList.put("lemon",1);
                clientMediator.getWorld().getEntity("user0").setCoin(5);
                assertNotNull(itemController.exchange("user0","user1",tranList,10,"user0"));
                transactionCommand.execute("user1",tranList,10,"buy");
                clientMediator.getWorld().getEntity("user0").setCoin(10);
                assertNull(itemController.exchange("user0","user1",tranList,10,"user0"));
                transactionCommand.execute("user1",tranList,10,"buy");
                clientMediator.getWorld().getEntity("user0").setCoin(15);
                assertNull(itemController.exchange("user0","user1",tranList,10,"user0"));
                transactionCommand.execute("user1",tranList,10,"buy");
            }
        };

        new Thread(progressTask).start();

    }
}


