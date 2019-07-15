package Controller.Load;

import Model.Location.Location;

import java.io.File;

public class LoadLocation {
	public LoadLocation() {

	}

	public Location buildLocation(File file) {

		Location location = new Location("test");
		System.out.println("Loading location file: " + file.getName());
		return location;
	}
}
