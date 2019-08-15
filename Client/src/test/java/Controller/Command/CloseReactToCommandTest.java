package Controller.Command;

import Controller.ClientMediator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CloseReactToCommandTest {

    private ClientMediator clientMediator;
    private CloseReactToCommand closeReactToCommand;

    @Before
    public void setUp(){
        clientMediator = new ClientMediator();
        closeReactToCommand = new CloseReactToCommand(clientMediator);
    }

    @Test
    public void executeTest(){
        clientMediator.setReactTo("user0");
        clientMediator.setReactResult("transaction with shop");
        closeReactToCommand.execute();
        assertEquals(null,clientMediator.getReactTo());
        assertEquals(null,clientMediator.getReactResult());
        clientMediator.setReactTo(null);
        clientMediator.setReactResult(null);
        closeReactToCommand.execute();
        assertEquals(null,clientMediator.getReactTo());
        assertEquals(null,clientMediator.getReactResult());
    }
}
