/**
 * 
 */
package florianWidder.StudentID18999061.assesment1.server.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import florianWidder.StudentID18999061.assesment1.model.ErrorMessage;
import florianWidder.StudentID18999061.assesment1.model.LoginMessage;
import florianWidder.StudentID18999061.assesment1.model.LogoutMessage;
import florianWidder.StudentID18999061.assesment1.model.Message;
import florianWidder.StudentID18999061.assesment1.model.User;
import florianWidder.StudentID18999061.assesment1.server.ServerMain;
import florianWidder.StudentID18999061.assesment1.server.controller.UserController;
import florianWidder.StudentID18999061.assesment1.server.util.Logger;
import florianWidder.StudentID18999061.assesment1.server.util.SessionHashGenerator;

/**
 * @author Florian Widder
 * @author Student ID 18999061
 *
 */
public class ClientThread implements Runnable {

	private ClientThread[] threads;
	private Socket client;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private UserController userList;
	private User user;

	/**
	 * @param client
	 * @param threads
	 * @param user
	 * @throws IOException
	 */
	public ClientThread(Socket client, ClientThread[] threads, UserController user) throws IOException {
		this.client = client;
		this.threads = threads;
		this.out = new ObjectOutputStream(this.client.getOutputStream());
		this.in = new ObjectInputStream(this.client.getInputStream());
		this.userList = user;
	}

	@Override
	public void run() {
		try {
			chooseUser();
		} catch (ClassNotFoundException | IOException e) {
			Logger.warn("Problem configuring client: " + e);
		}
		while (true) {
			try {
				Object input = in.readObject();
				if (input instanceof LogoutMessage) {
					LogoutMessage logout = (LogoutMessage) input;
					for (int i = 0; i < ServerMain.maxNumberOfClients; i++) {
						if (threads[i] != null && threads[i] != this)
							threads[i].sendMessage(logout);
					}
					Logger.info("User \"" + user.getUsername() + "\" left the chat");
					closeSession();
					break;
				}
			} catch (ClassNotFoundException | IOException e) {
				LogoutMessage logout = new LogoutMessage(user);
				for (int i = 0; i < ServerMain.maxNumberOfClients; i++) {
					if (threads[i] != null && !threads[i].equals(this))
						try {
							threads[i].sendMessage(logout);
						} catch (IOException e1) {
							Logger.warn("Problem while sending logout messages: " + e);
						}
				}
				Logger.info("User \"" + user.getUsername() + "\" left the chat");
				closeSession();
				break;
			}
		}
	}

	/**
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * 
	 */
	private void chooseUser() throws ClassNotFoundException, IOException {
		User newUser = null;
		do {
			if (newUser != null) {
				LoginMessage error = new LoginMessage(newUser, LoginMessage.loginDenied);
				out.writeObject(error);
			}
			Object input = in.readObject();
			if (input instanceof LoginMessage) {
				LoginMessage login = (LoginMessage) input;
				if (login.getCode() == LoginMessage.loginRequest) {
					newUser = login.getUser();
				} else {
					ErrorMessage error = new ErrorMessage(ErrorMessage.wrongRequestType);
					out.writeObject(error);
					newUser = null;
				}
			} else {
				ErrorMessage error = new ErrorMessage(ErrorMessage.wrongDataType);
				out.writeObject(error);
				newUser = null;
			}
		} while (!userList.addUser(newUser));
		newUser.setUdpSessionHash(new SessionHashGenerator().nextSessionId());
		this.user = newUser;
		out.writeObject(new LoginMessage(newUser, LoginMessage.loginAccept));
		Logger.info("New User: \"" + newUser.getUsername() + "\" is now online!");
	}

	/**
	 * @param m
	 * @throws IOException
	 */
	public void sendMessage(Message m) throws IOException {
		out.writeObject(m);
	}

	/**
	 * 
	 */
	public void closeSession() {
		userList.removeUser(user);
		for (int i = 0; i < ServerMain.maxNumberOfClients; i++)
			if (threads[i] != null && threads[i].equals(this))
				threads[i] = null;
	}

}
