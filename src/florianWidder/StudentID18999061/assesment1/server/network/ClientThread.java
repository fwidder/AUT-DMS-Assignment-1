/**
 *
 */
package florianWidder.StudentID18999061.assesment1.server.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import florianWidder.StudentID18999061.assesment1.server.ServerMain;
import florianWidder.StudentID18999061.assesment1.server.controller.UserController;
import florianWidder.StudentID18999061.assesment1.shared.model.BroadcastMessage;
import florianWidder.StudentID18999061.assesment1.shared.model.ConnectMessage;
import florianWidder.StudentID18999061.assesment1.shared.model.DisconnectMessage;
import florianWidder.StudentID18999061.assesment1.shared.model.ErrorMessage;
import florianWidder.StudentID18999061.assesment1.shared.model.Message;
import florianWidder.StudentID18999061.assesment1.shared.model.MessageTo;
import florianWidder.StudentID18999061.assesment1.shared.model.User;
import florianWidder.StudentID18999061.assesment1.shared.util.Logger;

/**
 * @author Florian Widder
 * @author Student ID 18999061
 *
 */
public class ClientThread implements Runnable {

    private final ClientThread[] threads;
    private final Socket client;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private final UserController userList;
    private User user;

    /**
     * @param client
     * @param threads
     * @param user
     * @throws IOException
     */
    public ClientThread(final Socket client, final ClientThread[] threads, final UserController user)
	    throws IOException {
	this.client = client;
	this.threads = threads;
	out = new ObjectOutputStream(this.client.getOutputStream());
	in = new ObjectInputStream(this.client.getInputStream());
	userList = user;
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
		final ConnectMessage error = new ConnectMessage(newUser, ConnectMessage.loginDenied);
		out.writeObject(error);
	    }
	    final Object input = in.readObject();
	    if (input instanceof ConnectMessage) {
		final ConnectMessage login = (ConnectMessage) input;
		if (login.getCode() == ConnectMessage.loginRequest) {
		    newUser = login.getSender();
		} else {
		    final ErrorMessage error = new ErrorMessage(ErrorMessage.wrongRequestType);
		    out.writeObject(error);
		    newUser = null;
		}
	    } else {
		final ErrorMessage error = new ErrorMessage(ErrorMessage.wrongDataType);
		out.writeObject(error);
		newUser = null;
	    }
	} while (!userList.addUser(newUser));
	user = newUser;
	out.writeObject(new ConnectMessage(newUser, ConnectMessage.loginAccept));
	Logger.info("New User: \"" + newUser.getUsername() + "\" is now online!");
	for (int i = 0; i < ServerMain.maxNumberOfClients; i++) {
	    if (threads[i] != null && threads[i] != this) {
		threads[i].sendMessage(new ConnectMessage(user, ConnectMessage.loginBroadcast));
	    }
	}
    }

    /**
     *
     */
    public void closeSession() {
	userList.removeUser(user);
	for (int i = 0; i < ServerMain.maxNumberOfClients; i++) {
	    if (threads[i] != null && threads[i].equals(this)) {
		threads[i] = null;
	    }
	}
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
		final Object input = in.readObject();
		if (input != null && input instanceof Message) {
		    if (input instanceof DisconnectMessage) {
			final DisconnectMessage logout = (DisconnectMessage) input;
			for (int i = 0; i < ServerMain.maxNumberOfClients; i++) {
			    if (threads[i] != null && threads[i] != this) {
				threads[i].sendMessage(logout);
			    }
			}
			Logger.info("User \"" + user.getUsername() + "\" left the chat - Logout");
			closeSession();
			break;
		    } else if (input instanceof MessageTo) {
			final MessageTo m = (MessageTo) input;
			for (final ClientThread c : threads) {
			    if (c != null && c.user != null && c.user.getUsername().equals(m.getRec())) {
				c.sendMessage(m);
			    }
			}
		    } else if (input instanceof BroadcastMessage) {
			final BroadcastMessage m = (BroadcastMessage) input;
			for (final ClientThread c : threads) {
			    if (c != null) {
				c.sendMessage(m);
			    }
			}
		    } else {
			final ErrorMessage err = new ErrorMessage(ErrorMessage.wrongDataType);
			out.writeObject(err);
		    }
		} else {
		    final ErrorMessage err = new ErrorMessage(ErrorMessage.wrongDataType);
		    out.writeObject(err);
		}
	    } catch (ClassNotFoundException | IOException e) {
		final DisconnectMessage logout = new DisconnectMessage(user, DisconnectMessage.logoutError);
		for (int i = 0; i < ServerMain.maxNumberOfClients; i++) {
		    if (threads[i] != null && !threads[i].equals(this)) {
			try {
			    threads[i].sendMessage(logout);
			} catch (final IOException e1) {
			    Logger.warn("Problem while sending logout messages: " + e);
			}
		    }
		}
		Logger.info("User \"" + user.getUsername() + "\" left the chat - Connection error");
		closeSession();
		break;
	    }
	}
    }

    /**
     * @param m
     * @throws IOException
     */
    public synchronized void sendMessage(final Message m) throws IOException {
	out.writeObject(m);
    }

}
