package wishlist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String getLogin() {
		return "login";
	}

	@RequestMapping("/welcome")
	public String getWelcome() {
		return "welcome";
	}
}
