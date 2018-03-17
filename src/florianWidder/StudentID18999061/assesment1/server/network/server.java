/**
 * 
 */
package florianWidder.StudentID18999061.assesment1.server.network;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import florianWidder.StudentID18999061.assesment1.server.Main;

/**
 * @author Florian Widder
 * @author Student ID 18999061
 *
 */
public class server {
	// The server socket.
	private static ServerSocket serverSocket = null;
	// The client socket.
	private static Socket clientSocket = null;
	/**
	 * Session Timeout in milliseconds
	 * default: 60000 (1 Minute)
	 */
	private static final long sessionTimeout = 60000; 

	// This chat server can accept up to maxClientsCount clients' connections.
	private static final int maxClientsCount = 10;
	private static final clientThread[] threads = new clientThread[maxClientsCount];

	public static void main(String args[]) {

		try {
			serverSocket = new ServerSocket(Main.PORT);
		} catch (IOException e) {
			System.err.println("Error creating the ServerSocket: " + e);
		}

		// Wait for clients
		while (true) {
			try {
				clientSocket = serverSocket.accept();
				int i = 0;
				for (i = 0; i < maxClientsCount; i++) {
					if (threads[i] == null) {
						(threads[i] = new clientThread(clientSocket, threads)).start();
						break;
					} else {
						if (threads[i].getUser().getLastSeen() > sessionTimeout) {
							threads[i].removeSession();
							(threads[i] = new clientThread(clientSocket, threads)).start();
							break;
						}
					}
				}
				if (i == maxClientsCount) {
					PrintStream os = new PrintStream(clientSocket.getOutputStream());
					os.println("#1001 - Server has reached maximum number of clients. Please try again later.");
					os.close();
					clientSocket.close();
				}
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}
}
