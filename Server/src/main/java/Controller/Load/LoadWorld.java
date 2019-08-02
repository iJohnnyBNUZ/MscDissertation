package Controller.Load;

import Controller.ServerMediator;

import java.io.File;
import java.io.IOException;

public class LoadWorld {
	private ServerMediator serverMediator;
	private LoadLocation loadLocation;
	private LoadEntity loadEntity;
	private String filePath;

	public LoadWorld(ServerMediator serverMediator, String filePath) {
		this.serverMediator = serverMediator;
		this.loadLocation = new LoadLocation(serverMediator);
		this.loadEntity = new LoadEntity(serverMediator);
		this.filePath = filePath;
	}

	public void loadLocations() throws IOException {
		File[] fileList = getFileList(filePath + "/Locations");
		if(fileList != null) {
			for (int i = 0; i < fileList.length; i++){
				File child = new File(filePath + "/Locations/DefaultLocation" + i + ".json");
				serverMediator.getWorld().addLocation(loadLocation.buildLocation(child));
			}
		}
		else {
			System.out.println("No files in this directory");
		}
	}

	public void loadEntities() throws IOException {
		File[] fileList = getFileList(filePath + "/Entities");
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
