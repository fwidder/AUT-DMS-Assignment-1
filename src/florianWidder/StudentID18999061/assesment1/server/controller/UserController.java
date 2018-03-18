/**
 * 
 */
package florianWidder.StudentID18999061.assesment1.server.controller;

import java.util.ArrayList;

import florianWidder.StudentID18999061.assesment1.model.User;

/**
 * @author Florian Widder
 * @author Student ID 18999061
 *
 */
public class UserController {
	private ArrayList<User> users = new ArrayList<User>();

	public UserController() {

	}

	public boolean addUser(User user) {
		if (user == null || users.contains(user))
			return false;
		users.add(user);
		return true;
	}

	public boolean removeUser(User user) {
		return users.remove(user);
	}

	public User getUser(String username) {
		for (User user : users)
			if (user.getUsername().equals(username))
				return user;
		return null;
	}

	public User[] getUserList() {
		return users.toArray(new User[0]);
	}
}
