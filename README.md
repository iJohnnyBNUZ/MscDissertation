# MscDissertation
## Set up Java:

Set src to sources root.

Set test to test root.

Set resources to resources root.

Configure Server/src/Main/java/RunServer to main class for Server.

Configure Client/src/Main/java/RunClient to main class for Client.

Set SDK to Java 11. Tested using OpenJDK.

## Set up JFX:

Download OpenJFX, can be found at: https://openjfx.io

Edit configuration and add VM options:

Linux/Mac: --module-path /path/to/javafx-sdk-12.0.1/lib --add-modules javafx.controls,javafx.fxml

Windows: --module-path "\path\to\javafx-sdk-12.0.1\lib" --add-modules javafx.controls,javafx.fxml

## Set up Game
For this game, a server should be set up firstly, then the other clients can connect to it to enter the game.

### Set up Server
Open the terminal or CMD window in the JAR folder.
Run java -jar Server.jar to open the server.
Input 'n' to load the default game data, or input 'y' to load the previous game data.

### Set up Client
Open the terminal or CMD window in the JAR folder.
Run java -jar Client.jar to set up the client.
Select game type in the index page.
Input username and IP address to connect to the server.

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

#### showAlert(String message, String header)
Show an Information type dialog box. "message" is the content, "header" is the header.

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

### BagView
This class is used to update the view of items in user's bag.

#### updateBag(List<Item> bag, int money)
Show the items in user's bag. Iterate the list, divide the items into food items and key items. Show the food items, key items and user's money in three Tab containers.

#### cleanSelect(List<GridPane> gridPaneList, int k)
Clear the effect of the Kth children ImageView in each GridPane container. Iterate the list, for each GridPane, access its Kth children-ImageView. If there is some effect in the ImageView, clear the effect.
  
#### getGridPaneFoodListSize()
Return the number of types of food.

#### getGridPaneKeyListSize()
Return the number of types of keys.
  
### NPCView
This class is used to show a dialog box when User interacts with NPC in the game.

#### updateNpcView(String sentence)
Show a dialog box with sentence in it.

### PostView
This class is used to update the view of post window and provides a list of other online users so they can be @ by the current user in post window.

#### updatePost(List<String> messageList, ArrayList<String> atUserList)
Show the messages in messageList in the PostView. atUserList stores the IDs of all online users, which can be @ by user in post window.
  
#### getMessageListViewSize()
Return the number of messages.

### TransactionView
This class is used to update the view of transaction window.

#### updateTransaction(List<Item> user__shop, String userShopName, List<Item> bag, int money)
Show the current user's items and the items of the entity who trades with the current user in transaction window. user_shop represents the items of that entity and userShopName is the ID of that entity. bag is current user's bag and money is current user's money
  
#### getUserShopVBoxSize()
Return the number of the other entity's items

#### getMyBagVBoxSize()
Return the number of the current user's items

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

### EatCommand
This class is used to forward the current userID and the itemID of the food to the ItemController. If the action can be processed, add new EatEvent to the queue in the ClientMediator. 

### PutDownCommand
This class is used to forward the current userID and the itemID to the ItemController and get a returned message. If the message is not null, the user cannot put the item down and a diaglog box will be showed to tell the user; otherwise, the action can be processed, add new PutDownEvent to the queue in the ClientMediator.

### PostCommand
This class is used to forward the message that the user posts in post window to PostController. If the action can be processed, add new PostEvent to the queue in the ClientMediator.

### TransactionCommand
This class is used to forward the IDs of buyer and seller, the type of transaction, the item list of transaction, the value of transaction and the ID of current user to ItemController, and get a returned message. If the message is not null, the transaction fails and a dialog box will be showed to tell the user; otherwise the transaction succeed, a diaglog box will be showed to tell the user, and a new TransactionEvent will be added to the queue in the ClientMediator.

### ReactToCommand
This class is used to handle the interaction between the current user and other entities. If the current user is really interacting with someone, store the ID of the entity and the result of interaction in ClientMediator, which will be used when update the view. And add a new ReactToEvent to the queue in the ClientMediator.

### CloseReactToCommand
This class is used to clear the information about the interaction, then the client will know now the current user doesn't interact with anyone. 

## Controller
There is only one controller in this package, the reason is this controller will only be used in the client-side.

### UserController
This class is used to do the username checking and start the game only for the available username.

#### startGame(String type, String uName, String IP)
Use the IP to connect to the target server and record the username and game type. After the connection is set up, request the world instance from server

#### enterGame()
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

### BagObserver
This observer will get the current user's bag and money from the world, then pass them to the BagView for updating.

### PostObserver
This observer will get a list of messages and a list of all the online users apart from the current user, then pass them to the PostView for updating. This observer will also check if the current user is @ by other users according to one variable in ClientMediator. If the current user is really @ by other users, a dialog box with the message will be showed to notify the user.

### ReactToObserver
This observer will check if the current user is interacting with someone now according to one variable in ClientMediator. If it is, the observer will check the type of the entity that the user interacts with. If the user interacts with NPC, the observer will get a message from another variable in ClientMediator and pass it to the NPCView for updating. If the user interacts with user or shop, the observer will get the entity's bag and ID, and the current user's bag and money, and then pass them to TransactionView for updating.


###  
# Common
This package contains code that both the client and server depend on. Each will run separate instances of this package.

## Events
Events are custom data structures used to 'talk' between the client and server. They are small and contain only essential information to update a model on both the client and the server. It allows us to avoid sending the entire world between clients.

### LoginEvent(String entityID)
This event sends the userID of the user that wants to login to the server. The server then checks if the user exists. If they do, it's location will be initialized and the world will be sent to the client.

### LogoutEvent(String entityID)
This event sends the userID of the user that wants to logout to the server. The server then removes the user from being displayed on any locations.

### MoveEvent(String entityID, String direction)
This event sends the userID and the direction of the user who want to move around in current location. Server then check if user can move to the tile in this direction. If so, the server will synchronize user's move action to all clients.

### OpenDoorEvent(String entityID)
This event sends the userID of the user that wants to move to the next Location. Server then check if the user can open door. If so, the server will synchronize user's location to all clients.

### PostEvent(String entityID, String meaasge)
This event sends the uerID and the message that user wants to send to others. Server then shows the message to all clients.

### EatEvent(String entityID, String selectedItemID)
This event sends the userID and the ID of food that the user wants to eat. Server will change this user's bag and energy, and then synchronize this user's action to all clients.

### PutDownEvent(String entityID, String ItemID)
This event sends the userID and the ID of the item that the user wants to put down. Server will put the item on the tile where the user is, and then synchronize this user's action to all clients.

### TransactionEvent(String buyerID, String sellerID, HashMap<String,Integer> tranList, int value, String entityID)
This event sends the IDs of buyer and seller, the item list of transaction, the value of transaction and the current userID of that client. Server will finish this transaction, and then synchronize the change of buyer and seller to all clients.

### ReactToEvent(String reactToID, String entityID)
This event sends the userID and the ID of the entity that the user interacts with. Server will handle the interaction, and then synchronize the interaction to all the clients.

## Model

### Location(String locationID)
Each location is composed by several different kinds of tiles. Entities and Items can also exists in one location with its corresponding coordinate.

#### Coordinate(int positionX, int positionY)
Cordinate is to record the position of tiles, entities and items. These objects can in the same coordinate.

#### Tile(boolean isMovable, String terrain, int energyCost)
Tile is the component unit of locations. Each tile has its own attributions, user will lost energy when they move to tiles and can not move to unmovable tiles. Terrain records the type of tiles.

#### Door(String currentLocation, String nextLocation)
Door is one kind of tile that connected two locations. When user open door, they are able to move to next location.

#### Stone(), Water(), Grass()
Stone, Water, Grass are different kinds of tiles.

### Item(String id,int coinValue,String type)
Each item has a public attribute such as ID, value, and type

#### Food(String id,int energy,int coinValue,String type)
In addition to the item's public attributes, Food also has an energy attribute that represents the energy value that the food can replenish.

#### Coin(String id,int coinValue,String type)
#### Key(String id,int coinValue,String type) 
There is also an isUsed attribute for each key, which represents whether the key has been used.

## Controllers
Controller used by both client and server, dealing with all business logic and modify models based on its related Event. 

### LocationController
This controller solve the move and open door event. For move, it will check if user has energy to move to the tile in direction, and whether the tile is movable. If so, it will change user coordinate. For open door, it will check if user has unused key in their bag, if user has opened this door, if user has enough energy to move. If so, it will add user to the next location and give it a initial coordinate near door.

### PostController
This controller solve the post event. It will add messages in world as what users posted.

### ReactToController
This controller is used to handle the interaction.

#### communicateWith(String userID)
This method is used to return the ID of entity that the current user interacts with.

#### reactToEntity(String id, String userID)
This method is used to make real interaction for the current user by calling reactTo(Entity entity) method and return a result of interaction. "userID" is the ID of current user and "id" is the ID of the entity that the current user interacts with.

#### ItemController
The item controller is mainly responsible for the user picking up items, dropping items, exchanging items, etc.

# Server
This package contains server-side logic. It will listen for new clients trying to connect with it and update them when any user makes a change to the server's model.

#### RunServer

##### main(String args[]) : void

Creates server mediator and starts server

## Controller
Package contains network connectivity, loading, and saving.

#### ServerMediator - implements GameMediator

Encapsulates access to the world and loading/saving functionality.

##### Field Summary

##### world : private World

World instance from Common module

##### saveWorld : private SaveWorld

Saves locations and entities to JSON.

##### saveLocation : private SaveLocation

Saves locations to JSON.

##### saveEntity : private SaveEntity

Saves entities to JSON.

##### Method Summary

##### startServer() : public void

Asks if user wants to create new instance of server or load from saved data, spawns new instance of server.

##### startGame(String directoryPath) : private void

Loads locations and entities from given directory path.

##### saveWorld() : public void

Saves locations and entities to JSON.

##### getLoactionSaver() : public SaveLocation

Access location saver.

##### getEntitySaver() : public SaveEntity

Access entity saver.

##### getWorld() : public World

Access to world instance.

### Network

Package contains classes that deal with the connections to the client.

#### Server - implements Runnable

Listens for clients trying to connect to server and starts threads to communicate with them.

##### Field Summary

##### serverSocket : private ServerSocket

Accepts new sockets when clients connect.

##### clients : private ArrayList<ServerListener>

Stores all clients connected to server.

##### serverMediator : private ServerMediator

Stores instance of serverMediator.

##### eventQueue : private LinkedList<Events> 

A queue of events sent from all active clients.

##### Method Summary

##### run : public void

Thread to listen for new clients.

##### removeClient(ServerListener client) : void

Disconnects client from server

##### addEventToQueue(Event event) : void

Adds event to Event queue.

##### updateOtherClients(ServerListener eventCreator) : void

Updates all clients except for the one that created the event.

##### sendWorldTOClients() : void

Sends whole world to all clients.

-----------------------------------


#### ServerUpdater - extends Thread implements Runnable

Keeps client up to date with events occuring on server.

##### Field Summary

##### objectOutput : private ObjectOutputStream

Sends objects to client.

##### serverMediator : private ServerMediator

Stores access to serverMediator. Used for updating server-side model.

##### Method Summary


-----------------------------------

#### ServerListener - extends Thread implements Runnable

##### Field Summary

##### server : private Server

Instance of server.

##### objectInput : private ObjectInputStream

Takes inputs from client.

##### canRun : private boolean

When this is true, thread loops continuously.

##### userName : private String

Stores user name of client connected to the listener.

##### serverMediator : private ServerMediator

Stores access to ServerMediator.

##### locationController : private LocationController

Stores access to LocationController.

##### itemController : private ItemController

Stores access to ItemController.

##### postController : private PostController

Stores access to PostController for chatting.

##### reactToController : private ReactToController

Stores access to ReactToController used to react to interactions from other entities.

##### serverUpdater : private ServerUpdater

Stores access to serverUpdater.

##### Method Summary

##### run : public void

Loops thread to get input from client continuously

##### getInputFromClient : private void

Waits for input from client and handles it.

##### sendWorld() : private void

Sends world to all clients.

##### updateMyClient() : void

Sends world only to client being listened to.

##### logout() : private void

Logs out of server.

##### sendMessage(Event event) : void

Sends message to client using ClientUpdater.

##### Client Input Handlers

Series of methods to handle different types of inputs from server. Adding new events require new handlers.
