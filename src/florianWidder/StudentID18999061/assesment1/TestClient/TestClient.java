/**
 * 
 */
package florianWidder.StudentID18999061.assesment1.TestClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import florianWidder.StudentID18999061.assesment1.model.LoginMessage;
import florianWidder.StudentID18999061.assesment1.model.LogoutMessage;
import florianWidder.StudentID18999061.assesment1.model.User;

/**
 * @author Florian Widder
 * @author Student ID 18999061
 *
 */
public class TestClient {

	/**
	 * @param args
	 * @throws IOException
	 * @throws UnknownHostException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		Socket socket = new Socket("localhost", 12345);
		OutputStream socketOutputStream = socket.getOutputStream();
		InputStream socketInputStream = socket.getInputStream();
		ObjectInputStream socketReader = new ObjectInputStream(socketInputStream);
		ObjectOutputStream socketWriter = new ObjectOutputStream(socketOutputStream);
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		User user = null;
		LoginMessage login = null;
		while (user == null || login == null || login.getCode() == LoginMessage.loginDenied) {
			System.out.println("Username:");
			String userName = console.readLine();
			user = new User(userName);
			login = new LoginMessage(user, LoginMessage.loginRequest);
			socketWriter.writeObject(login);
			Object input = socketReader.readObject();
			if (input instanceof LoginMessage) {
				login = (LoginMessage) input;
				if (login.getCode() == LoginMessage.loginAccept)
					user = login.getUser();
				else
					login = null;
			} else {
				login = null;
			}
		}
		System.out.println("Your username is: " + user.getUsername());
		socketWriter.writeObject(new LogoutMessage(user));
		console.close();
		socketWriter.close();
		socketReader.close();
		socketOutputStream.close();
		socketInputStream.close();
		socket.close();
	}
}
