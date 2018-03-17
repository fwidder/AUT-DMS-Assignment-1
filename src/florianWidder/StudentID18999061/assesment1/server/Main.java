package florianWidder.StudentID18999061.assesment1.server;

import java.net.UnknownHostException;

import florianWidder.StudentID18999061.assesment1.server.util.IPTools;

/**
 * Main Class to start the Server for Assignment 1 Project
 * 
 * @author Florian Widder
 * @author Student ID 18999061
 *
 */
public class Main {
	/**
	 * Port the Server will be running on
	 */
	public static int PORT = 8080;
	private static UserController userController = new UserController();

	/**
	 * Main method to run the Server
	 * 
	 * @param args
	 *            Java-VM Arguments
	 */
	public static void main(String[] args) {
		System.out.println("Server Starting...");
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
							System.out.println("Changed Port to " + PORT);
						} catch (Exception e) {
							System.err.println("Bad Port " + e);
							System.err.println("Port set to standart (8080)");
							PORT = 8080;
						}
					} else {
						System.err.println("--port passed but no Port specified");
						System.err.println("Port set to standart (8080)");
						PORT = 8080;
					}
					break;

				default:
					System.err.println("Unkown argument \"" + arg + "\" ignored");
					break;
				}
			}
		}
		// Print Server Informations
		try {
			System.out.println(
					"Server staring on: " + IPTools.getIP() + " | " + IPTools.getHostname() + " on Port: " + PORT);
		} catch (UnknownHostException e) {
			System.err.println("Error getting Local IP-Adress or Hostname: " + e);
		}
	}

	/**
	 * @return
	 */
	public static UserController getUserController() {
		// TODO Auto-generated method stub
		return userController;
	}
}
