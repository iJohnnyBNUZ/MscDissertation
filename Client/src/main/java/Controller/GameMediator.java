package Controller;

import Controller.Network.Client;
import Model.World;

import java.io.IOException;

public class GameMediator {
	World world;

	public GameMediator() {
		this.world = new World();
	}

	public void testClient() throws IOException, ClassNotFoundException {
		Client client = new Client(this);
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World newWorld) {
		this.world = newWorld;
	}
}
