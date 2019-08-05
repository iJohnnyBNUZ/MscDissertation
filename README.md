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

## UserController
This controller works when user start game. It get IP, user type and user name from view, connect to server and check if the user name and type is reasonable. If so, enter game.

## Commands

# Common
This package contains code that both the client and server depend on. Each will run separate instances of this package.

## Events
Events are custom data structures used to 'talk' between the client and server. They are small and contain only essential information to update a model on both the client and the server. It allows us to avoid sending the entire world between clients.

### LoginEvent(String entityID)
This event sends the userID of the user that wants to login to the server. The server then checks if the user exists. If they do, it's location will be initialized and the world will be sent to the client.

#### LogoutEvent(String entityID)
This event sends the userID of the user that wants to logout to the server. The server then removes the user from being displayed on any locations.

### MoveEvent(String entityID, String direction)
This event sends the userID and the direction of the user who want to move around in current location. Server then check if user can move to the tile in this direction. If so, the server will synchronize user's move action to all clients.

### OpenDoorEvent(String entityID)
This event sends the userID of the user that wants to move to the next Location. Server then check if the user can open door. If so, the server will synchronize user's location to all clients.

### PostEvent(String entityID, String meaasge)
This event sends the uerID and the message that user wants to send to others. Server then shows the message to all clients.

## Location(String locationID)
Each location is composed by several different kinds of tiles. Entities and Items can also exists in one location with its corresponding coordinate.

### Coordinate(int positionX, int positionY)
Cordinate is to record the position of tiles, entities and items. These objects can in the same coordinate.

### Tile(boolean isMovable, String terrain, int energyCost)
Tile is the component unit of locations. Each tile has its own attributions, user will lost energy when they move to tiles and can not move to unmovable tiles. Terrain records the type of tiles.

### Door(String currentLocation, String nextLocation)
Door is one kind of tile that connected two locations. When user open door, they are able to move to next location.

### Stone(), Water(), Grass()
Stone, Water, Grass are different kinds of tiles.

## Controllers
Controller used by both client and server, dealing with all business logic and modify models based on its related Event. 

### LocationController
This controller solve the move and open door event. For move, it will check if user has energy to move to the tile in direction, and whether the tile is movable. If so, it will change user coordinate. For open door, it will check if user has unused key in their bag, if user has opened this door, if user has enough energy to move. If so, it will add user to the next location and give it a initial coordinate near door.

### PostController
This controller solve the post event. It will add messages in world as what users posted.

# Server
This package contains server-side logic. It will listen for new clients trying to connect with it and update them when any user makes a change to the server's model.

##Server
This class listens for clients trying to connect

#### eventQueue : LinkedList<Events>
A queue of events sent from all active clients.

#### updateOtherClients(ServerListener eventCreator) : void
Updates all clients except for the one that created the event.
