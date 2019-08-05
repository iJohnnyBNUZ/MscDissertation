package Controller;

import Model.World;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class PostControllerTest {
    private GameMediator gameMediator;
    private PostController postController;

    @Before
    public void setUp() throws Exception {
        //setup a world
        World world = new World();

        gameMediator = new GameMediator() {
            @Override
            public World getWorld() {
                return world;
            }
        };

        postController = new PostController(gameMediator);
    }

    @Test
    public void addPostMessage() {
        String message = "test message one";
        postController.addPostMessage(message);
        List<String> list = new LinkedList<String>();
        list.add(message);
        assertEquals(gameMediator.getWorld().getMessageList(),list);

        String message1 = "test message two";
        postController.addPostMessage(message1);
        list.add(message1);
        assertEquals(gameMediator.getWorld().getMessageList(),list);

        postController.addPostMessage(message1);
        list.add(message);
        assertNotEquals(gameMediator.getWorld().getMessageList(),list);
    }
}