package Controller.Network;

import Controller.ServerMediator;

import java.io.IOException;
import java.io.ObjectOutputStream;

class ServerUpdater extends Thread implements Runnable {

	private ObjectOutputStream objectOutput;
	private ServerMediator serverMediator;

	ServerUpdater(ObjectOutputStream objectOutput, ServerMediator serverMediator) {
		this.objectOutput = objectOutput;
		this.serverMediator = serverMediator;
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
}
