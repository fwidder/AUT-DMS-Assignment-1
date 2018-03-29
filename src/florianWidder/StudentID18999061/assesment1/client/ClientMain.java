package florianWidder.StudentID18999061.assesment1.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import florianWidder.StudentID18999061.assesment1.client.network.Connection;
import florianWidder.StudentID18999061.assesment1.client.ui.ClientUI;
import florianWidder.StudentID18999061.assesment1.client.ui.LoginUI;
import florianWidder.StudentID18999061.assesment1.shared.model.User;
import florianWidder.StudentID18999061.assesment1.shared.util.Logger;

public class ClientMain {
    private static InetAddress IP;
    private static int Port;
    private static User user;
    static ClientUI UI;
    static Connection connection;

    public synchronized static Connection getConnection() {
	return ClientMain.connection;
    }

    public synchronized static InetAddress getIP() {
	return ClientMain.IP;
    }

    public synchronized static int getPort() {
	return ClientMain.Port;
    }

    public synchronized static ClientUI getUI() {
	return ClientMain.UI;
    }

    public synchronized static User getUser() {
	return ClientMain.user;
    }

    public static void main(final String[] args) throws InterruptedException, UnknownHostException, IOException {
	Logger.info("Client Starting...");

	final LoginUI login = new LoginUI();

	login.setModal(true);

	login.setVisible(true);

	while (login.isShowing()) {
	    ;
	}
	ClientMain.connection = new Connection();
	final Thread TCPClient = new Thread(ClientMain.connection);
	TCPClient.setName("TCPClient");
	TCPClient.start();

	while (ClientMain.connection.isLogin()) {
	    ;
	}

	ClientMain.UI = new ClientUI();

	final Timer t = new Timer();

	t.schedule(new TimerTask() {

	    @Override
	    public void run() {
		User[] userList;
		try {
		    userList = Connection.getUserList();
		    ClientMain.UI.refreshUsers(userList);
		} catch (IOException | ClassNotFoundException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }

	}, 0, 10000);

	ClientMain.UI.setVisible(true);
    }

    public synchronized static void setIP(final InetAddress inetAddress) {
	ClientMain.IP = inetAddress;
    }

    public synchronized static void setPort(final int port) {
	ClientMain.Port = port;
    }

    public synchronized static void setUser(final User user) {
	ClientMain.user = user;
    }
}
