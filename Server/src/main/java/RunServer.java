import Controller.ServerMediator;

public class RunServer {
	public static void main(String[] args) throws Exception{
		ServerMediator serverMediator = new ServerMediator();
		serverMediator.startServer();
	}
}
