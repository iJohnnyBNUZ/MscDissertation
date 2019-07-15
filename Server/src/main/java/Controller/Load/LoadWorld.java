package Controller.Load;

import Controller.ServerMediator;

import java.io.File;

public class LoadWorld {
	private ServerMediator serverMediator;
	private LoadLocation loadLocation  = new LoadLocation();
	private LoadEntity loadEntity = new LoadEntity();

	public LoadWorld(ServerMediator serverMediator, String filePath) {
		this.serverMediator = serverMediator;
		loadLocations(filePath + "/Locations");
		loadEntities(filePath  + "/Entities");
	}

	private void loadLocations(String filePath) {
		File[] fileList = getFileList(filePath);
		if(fileList != null) {
			for (File child : fileList) {
				serverMediator.getWorld().addLocation(loadLocation.buildLocation(child));
			}
		}
		else {
			System.out.println("No files in this directory");
		}
	}

	private void loadEntities(String filePath) {
		File[] fileList = getFileList(filePath);
		if(fileList != null) {
			for (File child : fileList) {
				serverMediator.getWorld().addEntity(loadEntity.buildEntity(child));
			}
		}
		else {
			System.out.println("No files in this directory");
		}
	}

	private File[] getFileList(String filePath) {
		File dir = new File(filePath);
		return dir.listFiles();
	}
}
