package Controller.Load;

import Controller.GameMediator;

import java.io.File;

public class LoadWorld {
	private GameMediator gameMediator;
	private LoadLocation loadLocation  = new LoadLocation();
	private LoadEntity loadEntity = new LoadEntity();

	public LoadWorld(GameMediator gameMediator, String filePath) {
		this.gameMediator = gameMediator;
		loadLocations(filePath + "/Locations");
		loadEntities(filePath  + "/Entities");
	}

	private void loadLocations(String filePath) {
		File[] fileList = getFileList(filePath);
		if(fileList != null) {
			for (File child : fileList) {
				gameMediator.getWorld().addLocation(loadLocation.buildLocation(child));
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
				gameMediator.getWorld().addEntity(loadEntity.buildEntity(child));
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
