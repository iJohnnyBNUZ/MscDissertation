package Controller.Command;

import Controller.ClientMediator;
import Controller.ItemController;
import Controller.PostController;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class PostCommandTest {

    private ClientMediator clientMediator;
    private PostController postController;
    private PostCommand postCommand;

    @Before
    public void setUp(){
        clientMediator = new ClientMediator();
        postController = new PostController(clientMediator);
        postCommand = new PostCommand(postController,clientMediator);
        clientMediator.setUserName("user0");
    }

    @Test
    public void executeTest(){
        List<String> list = new LinkedList<String>();
        list.add("user0");
        postCommand.execute("hello","2019-08-02",list);
        assertEquals(1,clientMediator.getWorld().getMessageList().size());
        assertEquals(1,clientMediator.getEventQueue().size());
    }
}
