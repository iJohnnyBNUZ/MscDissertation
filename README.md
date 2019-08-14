# MscDissertation
#Set up Java:

Set src to sources root.

Set test to test root.

Set resources to resources root.

Configure Server/src/Main/java/RunServer to main class for Server.

Configure Client/src/Main/java/RunClient to main class for Client.

Set SDK to Java 11. Tested using OpenJDK.

#Set up JFX:

Download OpenJFX, can be found at: https://openjfx.io

Edit configuration and add VM options:

Linux/Mac: --module-path /path/to/javafx-sdk-12.0.1/lib --add-modules javafx.controls,javafx.fxml

Windows: --module-path "\path\to\javafx-sdk-12.0.1\lib" --add-modules javafx.controls,javafx.fxml

# Client
The client contains all code used on the a user's personal device. It will store a copy of the model and take user input from the view to manipulate the model update the server.
## View
Views are used to show the data passed by the observers on the user interface.

### IndexView
This class is the FX-controller, it get the user input and pass it to the UserController.

#### newGame()
Record the game type as 'new', and show the interface used to input the username and IP address.

#### continueGame()
Record the game type as 'continue', and show the interface used to input the username and IP address.

#### startGame()
Get the user input and transfer the input and game type to the UserController.

### View
This class is the FX-controller, it capture the user actions and invoke the command to response to the user, and allow other views to refer to the elements written in the fxml file through it.
#### oprations: List
A queue of the name of keyEvent created by the user's action.

#### initialKeyAction(Scene scence)
Use the Scence to capture the user's action and put it into the queue.

#### addTimer()
Control the speed of keyEvent processing.

#### draw(String fileName, Coordinate position)
Load the image according to the filename and draw it on the canvas according to the position variable and the width of each tiles.

####  drawInteractive(String fileName, Coordinate position, Boolean isItemTile)
Load the image according to the filename and draw it on the item panel if it is a item tile, else draw it on the entity panel. The real position of the image on the panel is the position variable multiply with the tile's height and width.

#### setWindowsCloseAction(Stage primaryStage)
Show alert when user want to close the window.  

### LocationView
This class is used to update the map of a location.

#### update(Map<Coordinate, String> tiles)
Draw the tiles on the user interface. Iterate the map, invoke the draw method in the View class for each tile.

### ItemView
This class is used to update the view of items in a location.

#### update(Map<String,Coordinate> items)
Draw the items on the user interface. Iterate the map, invoke the drawInteractive method in the View class for each item.

### EntityView
This class is used to update the view of entities in a location.

#### updateNPC(Map<String,Coordinate> npcs)
Draw the NPCs on the user interface. Iterate the map, invoke the drawInteractive method in the View class for each NPC.

#### updateUser(Map<String,Coordinate> users) 
Draw the users on the user interface. Iterate the map, invoke the drawInteractive method in the View class for each user.

#### updateStore(Map<String,Coordinate> stores) 
Draw the stores on the user interface. Iterate the map, invoke the drawInteractive method in the View class for each store.

#### updateEnergy(int energyPoints)
Show the energy on the user interface by setting the value to the energy bar.

#### updateCoin(int coins)
Show the coins owned by the user on the user interface by setting the value to the coin label.

## UserController
This controller works when user start game. It get IP, user type and user name from view, connect to server and check if the user name and type is reasonable. If so, enter game.

## Commands
Commands are used to receive the parameters from the view and invoke the appropriate method in controllers to response to the user's action.

### MoveCommand
This class is used to forward the direction variable received from the view and the username variable to the LocationController. If the action can be processed, add new MovementEvent to the queue in the ClientMediator 

### OpenDoorCommand
This class is used to forward the username variable to the LocationController. If the action can be processed, add new OpenDoorEvent to the queue in the ClientMediator 

### PickUpCommand
This class is used to forward the username variable to the ItemController. If the action can be processed, add new PickUpEvent to the queue in the ClientMediator 

### StartGameCommand
This class is used to forward the game type variable, username variable and IP address variable to the UserController.

### SaveGameCommand
This class add the SaveGameEvent to the queue in the ClientMediator.

##Controller
There is only one controller in this package, the reason is this controller will only be used in the client-side.

###UserController
This class is used to do the username checking and start the game only for the available username.

### startGame(String type, String uName, String IP)
Use the IP to connect to the target server and record the username and game type. After the connection is set up, request the world instance from server

### enterGame()
Checking whether the username is already exist in the record. For the new game, only when the username is not exist, the user is allowed to enter the game, then tell the client to send login request to server.
For the continue game, only when the username is already exist, the user is allowed to enter the game, then tell the client to send login request to server. 

## Observer
Observers are used to observe the models, once the models are changed, they will try to update the view.

### LocationObserver
This observer will get the tiles' names and positions formed the current location from the world, then pass these parameters to the LocationView for updating.

### ItemObserver
This observer will get the items' names and positions in the current location from the world, then pass these parameters to the ItemView for updating.

### EntityObserver
This observer will get the entities' names and positions in the current location from the world, then pass these parameters to the EntityView for updating.

###  
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


