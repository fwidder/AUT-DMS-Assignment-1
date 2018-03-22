/**
 * 
 */
package florianWidder.StudentID18999061.assesment1.shared.model;

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
	 * @return the lastSeen
	 */
	public long getLastSeen() {
		return System.currentTimeMillis() - lastSeen;
	}

	/**
	 * @return the login
	 */
	public LocalDateTime getLogin() {
		return login;
	}

	/**
	 * @return the udpSessionHash
	 */
	public String getUdpSessionHash() {
		return udpSessionHash;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	/**
	 * @param udpSessionHash
	 *            the udpSessionHash to set
	 */
	public void setUdpSessionHash(String udpSessionHash) {
		this.udpSessionHash = udpSessionHash;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "User [udpSessionHash=" + udpSessionHash + ", username=" + username + ", login=" + login + ", lastSeen="
				+ lastSeen + "]";
	}

	/**
	 */
	public void updateLastSeen() {
		lastSeen = System.currentTimeMillis();
	}

}
