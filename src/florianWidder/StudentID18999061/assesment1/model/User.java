/**
 * 
 */
package florianWidder.StudentID18999061.assesment1.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Florian Widder
 * @author Student ID 18999061
 *
 */
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3359649237539654074L;

	/**
	 * Creates a new User
	 * 
	 * @param username
	 *            name of the User
	 */
	public User(String username) {
		this.username = username;
		login = LocalDateTime.now();
		lastSeen = System.currentTimeMillis();
	}

	private String udpSessionHash;
	private String username;

	/**
	 * Login time
	 */
	private LocalDateTime login;

	/**
	 * Milliseconds since the last received package
	 */
	private long lastSeen;

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the lastSeen
	 */
	public long getLastSeen() {
		return System.currentTimeMillis() - lastSeen;
	}

	/**
	 */
	public void updateLastSeen() {
		lastSeen = System.currentTimeMillis();
	}

	/**
	 * @return the login
	 */
	public LocalDateTime getLogin() {
		return login;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	/**
	 * @return the udpSessionHash
	 */
	public String getUdpSessionHash() {
		return udpSessionHash;
	}

	/**
	 * @param udpSessionHash
	 *            the udpSessionHash to set
	 */
	public void setUdpSessionHash(String udpSessionHash) {
		this.udpSessionHash = udpSessionHash;
	}

}
