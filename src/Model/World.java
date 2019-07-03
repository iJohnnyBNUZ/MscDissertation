package Model;

public class World {
	private static World ourInstance = new World();

	public static World getInstance() {
		return ourInstance;
	}

	private World() {
	}
}
