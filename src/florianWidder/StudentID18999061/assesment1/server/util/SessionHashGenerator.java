/**
 * 
 */
package florianWidder.StudentID18999061.assesment1.server.util;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author Florian Widder
 * @author Student ID 18999061
 *
 */
public class SessionHashGenerator {

	private SecureRandom random = new SecureRandom();

	/**
	 * @return
	 */
	public String nextSessionId() {
		return new BigInteger(130, random).toString(32);
	}

}