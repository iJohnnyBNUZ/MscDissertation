package Controller.FileUtils;

public class JsonIO {

    private FileIO fileIO = new FileIO();

    public JsonIO() {
    }

    public String readJson(String path){
        String json = "";
        System.out.println("Loading from " + path);
        try {
            json = fileIO.readFile(path);
        } catch (Exception e){
            e.printStackTrace();
        }

        return json;
    }

    public void writeJson(String path, String data) {
        try {
            fileIO.writeFile(path, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
