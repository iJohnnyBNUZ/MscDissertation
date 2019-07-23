package Controller;

import Controller.Load.LoadWorld;
import Controller.Network.Server;
import Controller.Save.SaveWorld;
import Model.World;

import java.io.IOException;

public class ServerMediator implements GameMediator{
	private World world;

	public ServerMediator() {
		this.world = new World();
	}

	public void newGame() throws IOException {
		LoadWorld loadWorld = new LoadWorld(this, "GameFiles/NewGame");
		loadWorld.loadLocations();
	}

	public void loadGame() throws IOException {
		LoadWorld loadWorld = new LoadWorld(this, "GameFiles/SavedGames");
	}

	public void saveGame() {
		SaveWorld saveWorld = new SaveWorld(this, "GameFiles/SavedGames");
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
