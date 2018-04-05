package florianWidder.StudentID18999061.assesment1.client.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import florianWidder.StudentID18999061.assesment1.client.ClientMain;
import florianWidder.StudentID18999061.assesment1.client.ui.LoginUI;
import florianWidder.StudentID18999061.assesment1.shared.model.ConnectMessage;
import florianWidder.StudentID18999061.assesment1.shared.model.DisconnectMessage;
import florianWidder.StudentID18999061.assesment1.shared.model.Message;
import florianWidder.StudentID18999061.assesment1.shared.model.RequestMessage;
import florianWidder.StudentID18999061.assesment1.shared.model.ResponseMessage;
import florianWidder.StudentID18999061.assesment1.shared.model.User;
import florianWidder.StudentID18999061.assesment1.shared.util.Logger;

/**
 * @author Florian Widder
 * @author Student ID 18999061
 *
 */
public class Connection implements Runnable {

    /**
     * Pulls the current List of logged in user's from the Server
     *
     * @return the Userlist
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public synchronized static User[] getUserList() throws IOException, ClassNotFoundException {
	// Open connection (UDP)
	DatagramSocket clientSocket = new DatagramSocket();
	InetAddress IPAddress = ClientMain.getIP();
	// Create packages
	byte[] sendData = new byte[1024];
	byte[] receiveData = new byte[1024];
	// Generate request
	RequestMessage m = new RequestMessage();
	m.setCode(RequestMessage.request);
	m.setPayload("userlist");
	m.setSender(ClientMain.getUser());
	// Serialize request
	ByteArrayOutputStream bStream = new ByteArrayOutputStream();
	ObjectOutput oo = new ObjectOutputStream(bStream);
	oo.writeObject(m);
	oo.close();
	sendData = bStream.toByteArray();
	// Send request
	DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, ClientMain.getPort());
	clientSocket.send(sendPacket);
	// Receive answer from server
	DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	clientSocket.receive(receivePacket);
	// Deserialize answer
	byte[] data = receivePacket.getData();
	ByteArrayInputStream in = new ByteArrayInputStream(data);
	ObjectInputStream is = new ObjectInputStream(in);
	Object o = is.readObject();
	// Parse answer
	if (!(o instanceof ResponseMessage)) {
	    clientSocket.close();
	    return new User[0];
	}
	clientSocket.close();
	ResponseMessage re = (ResponseMessage) o;
	if (re.getResponse() instanceof User[]) {
	    return (User[]) re.getResponse();
	}
	return new User[0];
    }

    private ObjectOutputStream socketWriter;
    private ObjectInputStream socketReader;
    private Socket socket;
    private boolean login = true;

    private LinkedBlockingQueue<Message> messages = new LinkedBlockingQueue<>();

    /**
     * @return messages
     */
    public synchronized LinkedBlockingQueue<Message> getMessages() {
	return messages;
    }

    /**
     * @return login state
     */
    public synchronized boolean isLogin() {
	return login;
    }

    /**
     * Login in at the Server
     *
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private synchronized void login() throws ClassNotFoundException, IOException {
	// Create and send request
	ConnectMessage m = new ConnectMessage(ClientMain.getUser(), ConnectMessage.loginRequest);
	socketWriter.writeObject(m);
	Object tmp = socketReader.readObject();
	// Parse answer
	if (tmp instanceof ConnectMessage) {
	    ConnectMessage in = (ConnectMessage) tmp;
	    if (in.getCode() != ConnectMessage.loginAccept) {
		LoginUI login = new LoginUI();

		login.setModal(true);

		login.setVisible(true);

		while (login.isShowing()) {
		    ;
		}

		login();
	    } else {
		ClientMain.setUser(in.getSender());
		login = false;
	    }
	}
    }

    /**
     * Logout
     *
     * @throws IOException
     */
    public synchronized void logout() throws IOException {
	socketWriter.writeObject(new DisconnectMessage(ClientMain.getUser(), DisconnectMessage.logoutNormal));
	Logger.info("Logout...");
	socketWriter.close();
	socketReader.close();
	socket.close();

    }

    @Override
    public void run() {
	try {
	    // Create streams
	    socket = new Socket(ClientMain.getIP(), ClientMain.getPort());
	    OutputStream socketOutputStream = socket.getOutputStream();
	    InputStream socketInputStream = socket.getInputStream();
	    socketReader = new ObjectInputStream(socketInputStream);
	    socketWriter = new ObjectOutputStream(socketOutputStream);

	    // Do login
	    login();

	    // Listen for messages
	    while (true) {
		Object o = socketReader.readObject();
		if (o instanceof Message) {
		    Message m = (Message) o;
		    ClientMain.getUI().newMessage(m);
		}
	    }

	} catch (IOException | ClassNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    /**
     * send a message to server
     *
     * @param message
     * @throws IOException
     */
    public synchronized void sendMessage(Message message) throws IOException {
	socketWriter.writeObject(message);

	ClientMain.getUI().newMessage(message);
    }

}
