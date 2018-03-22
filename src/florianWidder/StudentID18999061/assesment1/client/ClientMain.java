package florianWidder.StudentID18999061.assesment1.client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import florianWidder.StudentID18999061.assesment1.client.network.Connection;
import florianWidder.StudentID18999061.assesment1.client.ui.ClientUI;
import florianWidder.StudentID18999061.assesment1.client.ui.LoginUI;
import florianWidder.StudentID18999061.assesment1.shared.model.User;
import florianWidder.StudentID18999061.assesment1.shared.util.Logger;

public class ClientMain {
	private static String IP;
	private static int Port;
	private static User user;
	private static ClientUI UI;
	private static Connection connection;

	public synchronized static Connection getConnection() {
		return connection;
	}

	public synchronized static String getIP() {
		return IP;
	}

	public synchronized static int getPort() {
		return Port;
	}

	public synchronized static ClientUI getUI() {
		return UI;
	}

	public synchronized static User getUser() {
		return user;
	}

	public static void main(String[] args) throws InterruptedException, UnknownHostException, IOException {
		Logger.info("Client Starting...");

		LoginUI login = new LoginUI();

		login.setModal(true);

		login.setVisible(true);

		while (login.isShowing())
			;

		connection = new Connection();
		Thread TCPClient = new Thread(connection);
		TCPClient.setName("TCPClient");
		TCPClient.start();

		while (connection.isLogin())
			;

		UI = new ClientUI();

		Timer t = new Timer();

		t.schedule(new TimerTask() {

			@Override
			public void run() {
				User[] userList;
				try {
					userList = connection.getUserList();
					UI.refreshUsers(userList);
				} catch (IOException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}, 0, 10000);

		UI.setVisible(true);
	}

	public synchronized static void setIP(String iP) {
		IP = iP;
	}

	public synchronized static void setPort(int port) {
		Port = port;
	}

	public synchronized static void setUser(User user) {
		ClientMain.user = user;
	}

}
