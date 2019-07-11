package Controller;

import Controller.Network.Server;
import Model.World;

public class GameMediator {
	private World world;

	public GameMediator() {
		this.world = new World();
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World newWorld) {
		this.world = newWorld;
	}

	public void startServer() throws Exception {
		Server server = new Server(this);
	}
}
