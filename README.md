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

# Client
The client contains all code used on the a user's personal device. It will store a copy of the model and take user input from the view to manipulate the model update the server.

## Commands

# Common
This package contains code that both the client and server depend on. Each will run separate instances of this package.

## Events
Events are custom data structures used to 'talk' between the client and server. They are small and contain only essential information to update a model on both the client and the server. It allows us to avoid sending the entire world between clients.

### LoginEvent(String entityID)
This event sends the userID of the user that wants to login to the server. The server then checks if the user exists. If they do, it's location will be initialized and the world will be sent to the client.

#### LogoutEvent(String entityID)
This event sends the userID of the user that wants to logout to the server. The server then removes the user from being displayed on any locations.

# Server
This package contains server-side logic. It will listen for new clients trying to connect with it and update them when any user makes a change to the server's model.

##Server
This class listens for clients trying to connect

#### eventQueue : LinkedList<Events>
A queue of events sent from all active clients.

#### updateOtherClients(ServerListener eventCreator) : void
Updates all clients except for the one that created the event.
