package View;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import Controller.Command.Command;
import Controller.Command.CommunicationCommand;
import Controller.Command.MoveCommand;
import Model.Location.Coordinate;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class EntityView{
	
	private View view = null;
	private ProgressBar energy = null;
	private Label coin = null;
	private CommunicationCommand communication= null;
	private MoveCommand move = null;
	private int row=10;
	private int column=10;
	
	public EntityView(View view) {
		energy = view.getEnergy();
		coin = view.getCoin();
		this.view = view;
		
	}
	
	public void test() {
		// TODO Auto-generated method stub
		Map<String,Coordinate> tmp = new HashMap<String,Coordinate>();
		int num=0;
		for(int i=0;i<row/2;i++) {
			for(int j=0;j<column/5;j++) {
				Coordinate tmp_cor = new Coordinate(i, j);
				if(j%2 == 0 && i%2 == 0) {
					tmp.put("lemon"+num, tmp_cor);
				}else {
					tmp.put("apple"+num, tmp_cor);
				}
				
				num++;
			}
		}
		System.out.println("tiles size"+ tmp.size());
		updateNPC(tmp);
	}
	
	public void testStore() {
		// TODO Auto-generated method stub
		Map<String,Coordinate> tmp = new HashMap<String,Coordinate>();
		int num=0;
		for(int i=5;i<row;i++) {
			for(int j=5;j<column;j++) {
				Coordinate tmp_cor = new Coordinate(i, j);
				tmp.put("store"+num, tmp_cor);
				num++;
			}
		}
		System.out.println("tiles size"+ tmp.size());
		updateStore(tmp);
	}
	
	public void testUsers() {
		// TODO Auto-generated method stub
		Map<String,Coordinate> tmp = new HashMap<String,Coordinate>();
		int num=0;
		for(int i=2;i<5;i++) {
			for(int j=3;j<5;j++) {
				Coordinate tmp_cor = new Coordinate(i, j);
				tmp.put("users"+num, tmp_cor);
				num++;
			}
		}
		System.out.println("tiles size"+ tmp.size());
		updateUser(tmp);
	}
	
	public void testNPC() {
		// TODO Auto-generated method stub
		Map<String,Coordinate> tmp = new HashMap<String,Coordinate>();
		int num=0;
		for(int i=4;i<6;i++) {
			for(int j=4;j<7;j++) {
				Coordinate tmp_cor = new Coordinate(i, j);
				tmp.put("npcs"+num, tmp_cor);
				num++;
			}
		}
		System.out.println("tiles size"+ tmp.size());
		updateNPC(tmp);
	}
	
	public void updateNPC(final Map<String,Coordinate> npcs) {
		
		if(npcs.size() <= 100) {
			for(final String name: npcs.keySet()) {
				/*
				URL url = this.getClass().getResource("/images/npc.png");
				Image image = new Image(url.toString(), image_h, image_w, false, false);
				
				gContext.drawImage(image,0, 0,image_h,image_w, npcs.get(name).getyPosition()*tileWidth,
						npcs.get(name).getxPostion()*tileHeight,tileWidth,tileHeight);*/
				
				ImageView imgView = view.drawClickable("npc", npcs.get(name), false);
				
				imgView.setOnMouseClicked(new EventHandler<MouseEvent>() {

					public void handle(MouseEvent m) {
						// TODO Auto-generated method stub
						System.out.println("npccccccccccccc"+npcs.get(name).getxPostion()+
								npcs.get(name).getyPosition());
					}
					
				});
			}
		}else {
			System.out.println("Wrong tiles size");
		}
		
	}
	
	public void updateUser(Map<String,Coordinate> users) {
		
		if(users.size() <= 100) {
			for(String name: users.keySet()) {
				ImageView imgView = view.drawClickable("player", users.get(name), false);
				
				imgView.setOnMouseClicked(new EventHandler<MouseEvent>() {

					public void handle(MouseEvent m) {
						// TODO Auto-generated method stub
						System.out.println("userrrrrr");
					}
					
				});
			}
		}else {
			System.out.println("Wrong tiles size");
		}
		
	}
	
	public void updateStore(Map<String,Coordinate> stores) {
		
		if(stores.size() <= 100) {
			for(String name: stores.keySet()) {
				ImageView imgView = view.drawClickable("store", stores.get(name), false);
				
				imgView.setOnMouseClicked(new EventHandler<MouseEvent>() {

					public void handle(MouseEvent m) {
						// TODO Auto-generated method stub
						System.out.println("storeeeeeeeeeeeeeeee");
					}
					
				});
			}
		}else {
			System.out.println("Wrong tiles size");
		}
		
	}
	
	public void updateEnergy(int energyPoints) {
		
		if(energyPoints <= 100 && energyPoints >= 0) {
			energy.setProgress((double)energyPoints/100);
		}else {
			System.out.println("Wrong energy value!");
		}
		
	}
	
	public void updateCoin(int coins) {
		
		if(coins > 0) {
			coin.setText(""+coins);
		}else {
			System.out.println("Wrong coin value!");
		}
		
	}
	
	public void setCommunicationCommand(Command command) {
		communication = (CommunicationCommand) command;
	}
	
	public void setMoveCommand(Command command) {
		move = (MoveCommand) command;
	}
}
