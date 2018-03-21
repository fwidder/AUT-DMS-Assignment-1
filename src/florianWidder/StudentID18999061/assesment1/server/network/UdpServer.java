/**
 * 
 */
package florianWidder.StudentID18999061.assesment1.server.network;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import florianWidder.StudentID18999061.assesment1.model.RequestMessage;
import florianWidder.StudentID18999061.assesment1.server.ServerMain;

/**
 * @author Florian Widder
 * @author Student ID 18999061
 *
 */
public class UdpServer implements Runnable {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		DatagramSocket socket;
		try {
			socket = new DatagramSocket(ServerMain.PORT);
			byte[] incomingData = new byte[1024];

			while (true) {
				DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
				socket.receive(incomingPacket);
				byte[] data = incomingPacket.getData();
				ByteArrayInputStream in = new ByteArrayInputStream(data);
				ObjectInputStream is = new ObjectInputStream(in);
				Object o = is.readObject();
				if (!(o instanceof RequestMessage))
					continue;
				RequestMessage request = (RequestMessage) o;
				if (request.getCode() != RequestMessage.request || request.getPayload() == null
						|| request.getPayload().isEmpty()) {
					request.setCode(RequestMessage.malformed);
					request.setPayload("Empty Requestbody or wrong code.");
				} else
					switch (request.getPayload().toLowerCase()) {
					case "userlist":
						request.setCode(RequestMessage.acceptet);
						request.setPayload("");
						break;
					default:
						continue;
					}
				InetAddress IPAddress = incomingPacket.getAddress();
				int port = incomingPacket.getPort();
				String reply = "Thank you for the message";
				byte[] replyBytea = reply.getBytes();
				DatagramPacket replyPacket = new DatagramPacket(replyBytea, replyBytea.length, IPAddress, port);
				socket.send(replyPacket);
			}

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
