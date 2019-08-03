package Controller;

import Controller.Load.LoadWorld;
import Controller.Network.Server;
import Controller.Save.SaveEntity;
import Controller.Save.SaveLocation;
import Controller.Save.SaveWorld;
import Model.Entity.User;
import Model.World;

import java.io.IOException;
import java.util.Scanner;

public class ServerMediator implements GameMediator{
	private World world;
	private SaveWorld saveWorld = new SaveWorld(this, "GameFiles/SavedGames");
	private SaveEntity saveEntity = new SaveEntity(this, "GameFiles/SavedGames/Users");
	private SaveLocation saveLocation = new SaveLocation(this);

	public ServerMediator() {
		this.world = new World();
	}

	public void startServer() throws Exception {
		System.out.println("Do you want to load a previous game state? [y/n]");
		Scanner scanner = new Scanner(System.in);
		String load = scanner.nextLine();
		if (load.charAt(0) == 'y') {
			startGame("GameFiles/LoadGame");
		}
		else {
			startGame("GameFiles/NewGame");
		}
		Server server = new Server(this);
	}

	private void startGame(String directoryPath) throws IOException {
		LoadWorld loadWorld = new LoadWorld(this, directoryPath);
		loadWorld.loadLocations();
	}

	public void saveWorld() {
		saveWorld.saveWorld();
	}

	public SaveLocation getLocationSaver() {
		return saveLocation;
	}

	public SaveEntity getEntitySaver() {
		return saveEntity;
	}

	public void saveSingleUser(User user) {
		saveEntity.saveSingleEntity(user);
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World newWorld) {
		this.world = newWorld;
	}
}
