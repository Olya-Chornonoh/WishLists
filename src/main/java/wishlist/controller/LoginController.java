package wishlist.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import wishlist.model.Users;
import wishlist.repository.CategorylistRepository;
import wishlist.repository.PlansRepository;
import wishlist.repository.PurchasesRepository;
import wishlist.repository.TasksRepository;
import wishlist.repository.UsersRepository;

@Controller
public class LoginController {

	private UsersRepository usersRepository;
	private CategorylistRepository categorylistRepository;
	private PlansRepository plansRepository;
	private PurchasesRepository purchasesRepository;
	private TasksRepository tasksRepository;

	@Autowired
	public LoginController(UsersRepository usersRepository, CategorylistRepository categorylistRepository,
			PlansRepository plansRepository, PurchasesRepository purchasesRepository, TasksRepository tasksRepository) {
		this.usersRepository = usersRepository;
		this.categorylistRepository = categorylistRepository;
		this.plansRepository = plansRepository;
		this.purchasesRepository = purchasesRepository;
		this.tasksRepository = tasksRepository;
	}

	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}

	@PostMapping("/login")
	public String postLogin(@RequestParam String username, @RequestParam String password, Model model)
			throws NoSuchAlgorithmException {

		String passwordHash = chekPassword(password);

		Optional<Users> user = usersRepository.findByUsernameAndPassword(username, passwordHash);

		return chekUser(username, model, user);
	}

	private String chekPassword(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] digest = md.digest();
		String passwordHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
		return passwordHash;
	}

	private String chekUser(String username, Model model, Optional<Users> user) {
		if (user.isPresent()) {
			model.addAttribute("userId", user.get().getId());
			model.addAttribute("username", username);
			model.addAttribute("categories", categorylistRepository.findAllByUser(user.get()));
			model.addAttribute("purchases", purchasesRepository.findAllByUser(user.get()));
			model.addAttribute("plans", plansRepository.findAllByUser(user.get()));
			model.addAttribute("tasks", tasksRepository.findAllByUser(user.get()));
			return "index";
		} else {
			return "login";
		}
	}
}

