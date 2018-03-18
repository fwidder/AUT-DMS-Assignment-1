/**
 * 
 */
package florianWidder.StudentID18999061.assesment1.server.network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import florianWidder.StudentID18999061.assesment1.model.ErrorMessage;
import florianWidder.StudentID18999061.assesment1.server.ServerMain;
import florianWidder.StudentID18999061.assesment1.server.controller.UserController;
import florianWidder.StudentID18999061.assesment1.server.util.Logger;

/**
 * @author Florian Widder
 * @author Student ID 18999061
 *
 */
public class Server implements Runnable {

	private ServerSocket serverSocket;
	private final ClientThread[] threads;
	private UserController userController;

	/**
	 * 
	 */
	public Server() {
		threads = new ClientThread[ServerMain.maxNumberOfClients];
		userController = new UserController();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(ServerMain.PORT);
			while (true) {
				Socket client;
				client = serverSocket.accept();
				Logger.info("New Client: " + client.getInetAddress());
				int i;
				for (i = 0; i < ServerMain.maxNumberOfClients; i++) {
					if (threads[i] == null) {
						threads[i] = new ClientThread(client, threads, userController);
						Thread clientThread = new Thread(threads[i]);
						clientThread.setName("Client " + i);
						clientThread.start();
						break;
					}
				}
				if (i == ServerMain.maxNumberOfClients) {
					ErrorMessage error = new ErrorMessage(ErrorMessage.userLimitReached);
					try (ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream())) {
						out.writeObject(error);
					}
					client.close();
					Logger.warn(error.toString());
				}
			}
		} catch (IOException e) {
			Logger.error("Problem beim Starten des Servers: " + e);
		}
	}

}
