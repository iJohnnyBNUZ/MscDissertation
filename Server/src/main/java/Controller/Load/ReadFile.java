package Controller.Load;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadFile {
	public String readJSON(String filePath) throws IOException {
		System.out.println(Paths.get(filePath).toAbsolutePath().toString());
		byte[] encoded = Files.readAllBytes(Paths.get(filePath));
		return new String(encoded);
	}
}
