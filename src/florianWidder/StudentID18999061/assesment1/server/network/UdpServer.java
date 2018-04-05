/**
 *
 */
package florianWidder.StudentID18999061.assesment1.server.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import florianWidder.StudentID18999061.assesment1.server.ServerMain;
import florianWidder.StudentID18999061.assesment1.shared.model.Message;
import florianWidder.StudentID18999061.assesment1.shared.model.RequestMessage;
import florianWidder.StudentID18999061.assesment1.shared.model.ResponseMessage;

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
	    // Create UDP Socket
	    socket = new DatagramSocket(ServerMain.PORT);
	    byte[] incomingData = new byte[1024];

	    while (true) {
		// Wait for requests
		DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
		socket.receive(incomingPacket);
		byte[] data = incomingPacket.getData();
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream is = new ObjectInputStream(in);
		Object o = is.readObject();
		if (!(o instanceof RequestMessage)) {
		    continue;
		}
		Message ret = null;
		RequestMessage request = (RequestMessage) o;
		if (request.getCode() != RequestMessage.request || request.getPayload() == null
			|| request.getPayload().isEmpty()) {
		    request.setCode(RequestMessage.malformed);
		    request.setPayload("Empty Requestbody or wrong code.");
		    ret = request;
		} else {
		    switch (request.getPayload().toLowerCase()) {
		    case "userlist":
			ResponseMessage response = new ResponseMessage();
			response.setCode(RequestMessage.acceptet);
			response.setPayload("userlist");
			response.setResponse(ServerMain.getUsers());
			ret = response;
			break;

		    default:
			continue;
		    }
		}

		InetAddress IPAddress = incomingPacket.getAddress();
		int port = incomingPacket.getPort();
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		ObjectOutput oo = new ObjectOutputStream(bStream);
		oo.writeObject(ret);
		oo.close();

		byte[] serializedMessage = bStream.toByteArray();
		DatagramPacket replyPacket = new DatagramPacket(serializedMessage, serializedMessage.length, IPAddress,
			port);
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
