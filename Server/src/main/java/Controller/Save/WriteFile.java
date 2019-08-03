package Controller.Save;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class WriteFile {

	public void writeJsonArray(JsonArray jsonArray, String filePath) {
		try {
			Writer writer =  new FileWriter(filePath);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(jsonArray, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
