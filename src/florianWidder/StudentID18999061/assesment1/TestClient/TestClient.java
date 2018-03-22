/**
 * 
 */
package florianWidder.StudentID18999061.assesment1.TestClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import florianWidder.StudentID18999061.assesment1.shared.model.ConnectMessage;
import florianWidder.StudentID18999061.assesment1.shared.model.DisconnectMessage;
import florianWidder.StudentID18999061.assesment1.shared.model.MessageTo;
import florianWidder.StudentID18999061.assesment1.shared.model.User;

/**
 * @author Florian Widder
 * @author Student ID 18999061
 *
 */
public class TestClient {

	/**
	 * @param args
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		Socket socket = new Socket("localhost", 12345);
		OutputStream socketOutputStream = socket.getOutputStream();
		InputStream socketInputStream = socket.getInputStream();
		ObjectInputStream socketReader = new ObjectInputStream(socketInputStream);
		ObjectOutputStream socketWriter = new ObjectOutputStream(socketOutputStream);
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		User user = null;
		ConnectMessage login = null;
		System.out.println("Username:");
		String userName = console.readLine();
		user = new User(userName);
		login = new ConnectMessage(user, ConnectMessage.loginRequest);
		socketWriter.writeObject(login);
		Object input = socketReader.readObject();
		if (input instanceof ConnectMessage) {
			login = (ConnectMessage) input;
			if (login.getCode() == ConnectMessage.loginAccept)
				user = login.getSender();
			else
				throw new Exception("Login denied:" + login.getCode());
		} else {
			throw new Exception("Recieved wrong Mimetype");

		}
		System.out.println("Logged in: " + user.toString());

		MessageTo m = new MessageTo();
		m.setPayload("Test");
		m.setRec("Test");
		socketWriter.writeObject(m);
		socketWriter.writeObject(new DisconnectMessage(user));
		System.out.println("Logged out");
		console.close();
		socketWriter.close();
		socketReader.close();
		socketOutputStream.close();
		socketInputStream.close();
		socket.close();
	}
}
