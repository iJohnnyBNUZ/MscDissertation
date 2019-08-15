package Controller.Load;

import Controller.ServerMediator;

import java.io.File;

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

	public void loadLocations() {
		File[] fileList = getFileList(filePath + "Locations");
		if(fileList != null) {
			for (File child : fileList) {
				serverMediator.getWorld().addLocation(loadLocation.buildLocation(child));
			}
		}
		else {
			System.out.println("No files in location directory");
		}
	}

	public void loadEntities() {
		File[] fileList = getFileList(filePath + "Entities");
		if(fileList != null) {
			for (File child : fileList) {
				loadEntity.buildEntity(child);
			}
		}
		else {
			System.out.println("No files in entity directory");
		}
	}

	private File[] getFileList(String filePath) {
		File dir = new File(filePath);
		System.out.println("File path is " + dir.getAbsolutePath());
		return dir.listFiles();
	}
}
