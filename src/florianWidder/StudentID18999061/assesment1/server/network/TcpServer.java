/**
 *
 */
package florianWidder.StudentID18999061.assesment1.server.network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import florianWidder.StudentID18999061.assesment1.server.ServerMain;
import florianWidder.StudentID18999061.assesment1.server.controller.UserController;
import florianWidder.StudentID18999061.assesment1.shared.model.ErrorMessage;
import florianWidder.StudentID18999061.assesment1.shared.util.Logger;

/**
 * @author Florian Widder
 * @author Student ID 18999061
 *
 */
public class TcpServer implements Runnable {

    private ServerSocket serverSocket;
    // Array for holding the client threads
    private ClientThread[] threads;
    private UserController userController;

    /**
     *
     */
    public TcpServer() {
	threads = new ClientThread[ServerMain.maxNumberOfClients];
	userController = new UserController();
    }

    public synchronized UserController getUserController() {
	return userController;
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
		// Wait for clients
		client = serverSocket.accept();
		Logger.info("New Client: " + client.getInetAddress());
		int i;
		// Check user limit and start new Thread for new Client
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
