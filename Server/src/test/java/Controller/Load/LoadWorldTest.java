package Controller.Load;

import Controller.ServerMediator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LoadWorldTest {
    private ServerMediator serverMediator = new ServerMediator();
    private LoadWorld loadWorld = new LoadWorld(serverMediator, "src/test/resources/");

    @Test
    public void loadLocations() {
        loadWorld.loadLocations();
        Assert.assertEquals(serverMediator.getWorld().getLocations().size(), 3);
        Assert.assertEquals(serverMediator.getWorld().getEntities().size(), 0);
    }

    @Test
    public void loadEntities() {
        loadWorld.loadLocations();
        loadWorld.loadEntities();
        Assert.assertEquals(serverMediator.getWorld().getEntities().size(), 1);
    }
}