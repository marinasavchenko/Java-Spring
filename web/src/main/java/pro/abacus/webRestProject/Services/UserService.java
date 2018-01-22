package pro.abacus.webRestProject.Services;

import pro.abacus.webRestProject.models.User;

public interface UserService {
	void save(User user);

	User findByUsername(String username);
	boolean isDuplicate (User user);

}
