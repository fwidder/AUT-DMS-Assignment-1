package florianWidder.StudentID18999061.assesment1.server;

import java.net.UnknownHostException;

import florianWidder.StudentID18999061.assesment1.server.network.Server;
import florianWidder.StudentID18999061.assesment1.server.network.UdpServer;
import florianWidder.StudentID18999061.assesment1.shared.model.User;
import florianWidder.StudentID18999061.assesment1.shared.util.IPTools;
import florianWidder.StudentID18999061.assesment1.shared.util.Logger;

/**
 * ServerMain Class to start the Server for Assignment 1 Project
 *
 * @author Florian Widder
 * @author Student ID 18999061
 *
 */
public class ServerMain {
    /**
     * Port the Server will be running on
     */
    public static int PORT = 12345;

    /**
     * Maximum number of clients the Server will accept
     */
    public static int maxNumberOfClients = 10;

    private static Server server;

    public static User[] getUsers() {
	return ServerMain.server.getUserController().getUserList();
    }

    /**
     * ServerMain method to run the Server
     *
     * @param args
     *            Java-VM Arguments
     */
    public static void main(String[] args) {
	Logger.info("Server starting...");
	// Parsing args
	if (args != null && args.length > 0) {
	    for (int i = 0; i < args.length; i++) {
		String arg = args[i].toLowerCase();
		switch (arg) {
		case "--port":
		    if (args.length > i + 1) {
			i++;
			try {
			    ServerMain.PORT = Integer.parseInt(args[i]);
			    if (ServerMain.PORT <= 0 || ServerMain.PORT > 65535) {
				Logger.warn("Port set to standart (" + ServerMain.PORT + ")");
				ServerMain.PORT = 12345;
			    }
			} catch (Exception e) {
			    Logger.warn("Bad Port " + e);
			    ServerMain.PORT = 12345;
			    Logger.warn("Port set to standart (" + ServerMain.PORT + ")");
			}
		    } else {
			Logger.warn("--port passed but no Port specified");
			ServerMain.PORT = 12345;
			Logger.warn("Port set to standart (" + ServerMain.PORT + ")");
		    }
		    break;

		case "--userLimit":
		    if (args.length > i + 1) {
			i++;
			try {
			    ServerMain.maxNumberOfClients = Integer.parseInt(args[i]);
			} catch (Exception e) {
			    Logger.warn("Bad maxNumberOfClients " + e);
			    ServerMain.maxNumberOfClients = 10;
			    Logger.warn("maxNumberOfClients set to standart (" + ServerMain.maxNumberOfClients + ")");
			}
		    } else {
			Logger.warn("--userLimit passed but no maxNumberOfClients specified");
			ServerMain.maxNumberOfClients = 10;
			Logger.warn("maxNumberOfClients set to standart (" + ServerMain.maxNumberOfClients + ")");
		    }
		    break;

		default:
		    Logger.warn("Unkown argument \"" + arg + "\" ignored");
		    break;
		}
	    }
	}

	// Print Server Informations
	try {
	    Logger.info(
		    "Server IP: " + IPTools.getIP() + " | " + IPTools.getHostname() + " on Port: " + ServerMain.PORT);
	} catch (UnknownHostException e) {
	    Logger.error("Error getting Local IP-Adress or Hostname: " + e);
	}
	ServerMain.server = new Server();
	Thread serverThread = new Thread(ServerMain.server);
	serverThread.setName("Server TCP");
	serverThread.start();

	UdpServer udpServer = new UdpServer();
	Thread udpServerThread = new Thread(udpServer);
	udpServerThread.setName("Server UDP");
	udpServerThread.start();
    }
}
