package Controller;

import Controller.Load.LoadWorld;
import Controller.Network.Server;
import Controller.Save.SaveWorld;
import Model.World;

public class GameMediator {
	private World world;

	public GameMediator() {
		this.world = new World();
	}

	public void newGame() {
		LoadWorld loadWorld = new LoadWorld(this, "GameFiles/NewGame");
	}

	public void loadGame() {
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
