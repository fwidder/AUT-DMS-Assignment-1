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

public class Connection implements Runnable {

	private ObjectOutputStream socketWriter;
	private ObjectInputStream socketReader;
	private Socket socket;
	private boolean login = true;
	private LinkedBlockingQueue<Message> messages = new LinkedBlockingQueue<Message>();

	public synchronized User[] getUserList() throws IOException, ClassNotFoundException {
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName(ClientMain.getIP());
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		RequestMessage m = new RequestMessage();
		m.setCode(RequestMessage.request);
		m.setPayload("userlist");
		m.setSender(ClientMain.getUser());
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		ObjectOutput oo = new ObjectOutputStream(bStream);
		oo.writeObject(m);
		oo.close();
		sendData = bStream.toByteArray();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, ClientMain.getPort());
		clientSocket.send(sendPacket);
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);
		byte[] data = receivePacket.getData();
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream is = new ObjectInputStream(in);
		Object o = is.readObject();
		if (!(o instanceof ResponseMessage)) {
			clientSocket.close();
			return new User[0];
		}
		clientSocket.close();
		ResponseMessage re = (ResponseMessage) o;
		if (re.getResponse() instanceof User[])
			return (User[]) re.getResponse();
		return new User[0];
	}

	public synchronized boolean isLogin() {
		return login;
	}

	private synchronized void login() throws ClassNotFoundException, IOException {
		ConnectMessage m = new ConnectMessage(ClientMain.getUser(), ConnectMessage.loginRequest);
		socketWriter.writeObject(m);
		Object tmp = socketReader.readObject();
		if (tmp instanceof ConnectMessage) {
			ConnectMessage in = (ConnectMessage) tmp;
			if (in.getCode() != ConnectMessage.loginAccept) {
				LoginUI login = new LoginUI();

				login.setModal(true);

				login.setVisible(true);

				while (login.isShowing())
					;

				login();
			} else {
				ClientMain.setUser(in.getSender());
				login = false;
			}
		}
	}

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
			socket = new Socket(ClientMain.getIP(), ClientMain.getPort());
			OutputStream socketOutputStream = socket.getOutputStream();
			InputStream socketInputStream = socket.getInputStream();
			socketReader = new ObjectInputStream(socketInputStream);
			socketWriter = new ObjectOutputStream(socketOutputStream);
			login();

			while (true) {
				Object o = socketReader.readObject();
				if(o instanceof Message) {
					Message m = (Message) o;
					messages.put(m);
				}
				ClientMain.getUI().newMessage();
				Logger.info(ClientMain.getUser().getUsername() + "→" + o.toString());
			}

		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void sendMessage(Message message) throws IOException {
		socketWriter.writeObject(message);
	}

	public synchronized LinkedBlockingQueue<Message> getMessages() {
		return messages;
	}

}
