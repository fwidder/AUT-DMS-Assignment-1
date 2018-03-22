package florianWidder.StudentID18999061.assesment1.shared.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Utility class for network Informations
 * 
 * @author Florian Widder
 * @author Student ID 18999061
 *
 */
public class IPTools {
	/**
	 * @return the local Host-Name
	 * @throws UnknownHostException
	 */
	public static String getHostname() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostName();
	}

	/**
	 * @return the local IP-Address
	 * @throws UnknownHostException
	 */
	public static String getIP() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostAddress();
	}
}
