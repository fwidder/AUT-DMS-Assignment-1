package florianWidder.StudentID18999061.assesment1.server;

import java.net.UnknownHostException;

import florianWidder.StudentID18999061.assesment1.server.network.Server;
import florianWidder.StudentID18999061.assesment1.server.util.IPTools;
import florianWidder.StudentID18999061.assesment1.server.util.Logger;

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

	public static final int maxNumberOfClients = 10;

	public static final int sessionTimeout = 60000;

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
							PORT = Integer.parseInt(args[i]);
							if (PORT <= 0 || PORT > 65535)
								throw new Exception("Port out of Range: " + PORT);
							Logger.warn("Port set to standart (" + PORT + ")");
						} catch (Exception e) {
							Logger.warn("Bad Port " + e);
							PORT = 12345;
						}
					} else {
						Logger.warn("--port passed but no Port specified");
						Logger.warn("Port set to standart (" + PORT + ")");
						PORT = 12345;
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
			Logger.info("Server IP: " + IPTools.getIP() + " | " + IPTools.getHostname() + " on Port: " + PORT);
		} catch (UnknownHostException e) {
			Logger.error("Error getting Local IP-Adress or Hostname: " + e);
		}
		Server server = new Server();
		Thread serverThread = new Thread(server);
		serverThread.setName("Server");
		serverThread.start();
	}
}
