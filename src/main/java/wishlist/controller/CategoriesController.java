package wishlist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import wishlist.model.Categorylist;
import wishlist.repository.CategorylistRepository;
import wishlist.repository.UsersRepository;

@RestController
public class CategoriesController {

	private CategorylistRepository categorylistRepository;
	private UsersRepository usersRepository;

	@Autowired
	public CategoriesController(CategorylistRepository categorylistRepository, UsersRepository usersRepository) {
		this.categorylistRepository = categorylistRepository;
		this.usersRepository = usersRepository;
	}

	@RequestMapping(value = "/categories/add", method = RequestMethod.POST)
	public ResponseEntity<Categorylist> createCategory(@RequestBody CategoryAddRequest req) {
		Categorylist category = new Categorylist();
		category.setName(req.name);
		category.setUser(usersRepository.findById(req.userId).get());
		categorylistRepository.save(category);
		return new ResponseEntity<Categorylist>(category, HttpStatus.OK);
	}

	public static class CategoryAddRequest {
		public String name;
		public Integer userId;
	}
}
