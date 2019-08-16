// This class only exists to allow JavaFX 11 to be packaged with the Client jar file
// It is unnecessary for development

public class ClientRunner {
    public static void main(String[] args) {
        try {
            RunClient.main(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
