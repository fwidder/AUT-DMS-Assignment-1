package florianWidder.StudentID18999061.assesment1.server.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import florianWidder.StudentID18999061.assesment1.server.Main;
import florianWidder.StudentID18999061.assesment1.server.model.User;

/*
	 * The chat client thread. This client thread opens the input and the output
	 * streams for a particular client, ask the client's name, informs all the
	 * clients connected to the server about the fact that a new client has joined
	 * the chat room, and as long as it receive data, echos that data back to all
	 * other clients. When a client leaves the chat room this thread informs also
	 * all the clients about that and terminates.
	 */
class clientThread extends Thread {

	private BufferedReader is = null;
	private BufferedWriter os = null;
	private Socket clientSocket = null;
	private final clientThread[] threads;
	private int maxClientsCount;
	private User user;

	/**	
	 * Creates a new Thread for the client
	 * 
	 * @param clientSocket socket of the client
	 * @param threads thread list
	 */
	public clientThread(Socket clientSocket, clientThread[] threads) {
		this.clientSocket = clientSocket;
		this.threads = threads;
		maxClientsCount = threads.length;
	}

	public void run() {

		try {
			is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			os = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			chooseUser();
		} catch (IOException e) {
			System.err.println("Error talking to client: " + e);
		}
	}

	/**
	 * @throws IOException 
	 * 
	 */
	private void chooseUser() throws IOException {
		String username = is.readLine();
		boolean success = Main.getUserController().addUser(new User(username));
		while(!success) {
			os.write("#1002 - User already exists");
			username = is.readLine();
			success = Main.getUserController().addUser(new User(username));
		}
		os.write("#0001 - User created");
		user.updateLastSeen();
		setUser(Main.getUserController().getUser(username));
	}

	/**
	 * Removes the selected Session
	 */
	public void removeSession() {
		for (int i = 0; i < maxClientsCount; i++) {
			if (threads[i] == this) {
				threads[i] = null;
			}
		}

		try {
			is.close();
			os.close();
			clientSocket.close();
		} catch (IOException e) {
			System.err.println("Error closing connection: " + e);
		}
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
}