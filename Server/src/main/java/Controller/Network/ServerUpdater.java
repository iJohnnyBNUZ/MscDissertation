package Controller.Network;

import Controller.ServerMediator;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ServerUpdater extends Thread implements Runnable {

	private ObjectOutputStream objectOutput;
	private boolean canRun = true;
	private ServerMediator serverMediator;

	ServerUpdater(ObjectOutputStream objectOutput, ServerMediator serverMediator) throws Exception{
		this.objectOutput = objectOutput;
		this.serverMediator = serverMediator;
	}

	@Override
	public void run() {
		System.out.println("Running client updater");
		while (canRun) {
			try {
				//Wait for input
			}
			catch(Exception ex) {
				canRun = false;
				ex.printStackTrace();
			}
		}
	}

	void sendMessage(Object event) {
		try {
			objectOutput.writeObject(event);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			objectOutput.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void sendWorld() {
		try {
			System.out.println("World users: " + serverMediator.getWorld().getEntities());
			objectOutput.writeObject(serverMediator.getWorld());
			try {
				objectOutput.reset();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void setCanRun(boolean b) {
		canRun = b;
	}
}
