package wishlist.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import wishlist.model.Users;
import wishlist.repository.UsersRepository;

@Controller
public class SignUpController {
	private UsersRepository usersRepository;

	@Autowired
	public SignUpController(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}

	@GetMapping("/signup")
	public String getSignup() {
		return "signup";
	}

	@PostMapping("/signup")
	public String postSignup(@RequestParam String username, @RequestParam String password,
			@RequestParam String confirmPassword) throws NoSuchAlgorithmException {
		if (password.equals(confirmPassword)) {
			Users user = new Users();

			user.setUserName(username);

			String passwordHash = createPassword(password);

			user.setPasswd(passwordHash);

			usersRepository.save(user);

			return "login";
		} else {
			return "signup";
		}
	}

	private String createPassword(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] digest = md.digest();
		String passwordHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
		return passwordHash;
	}
}
