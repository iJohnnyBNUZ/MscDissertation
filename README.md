# MscDissertation
#Set up Java:

Set src to sources root.

Set test to test root.

Set resources to resources root.

Configure src/Main.java to main class.

Set SDK to Java 11. Tested using OpenJDK.

#Set up JFX:

Download OpenJFX, can be found at: https://openjfx.io

Edit configuration and add VM options:

Linux/Mac: --module-path /path/to/javafx-sdk-12.0.1/lib --add-modules javafx.controls,javafx.fxml

Windows: --module-path "\path\to\javafx-sdk-12.0.1\lib" --add-modules javafx.controls,javafx.fxml
