package wishlist.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import wishlist.model.Purchases;
import wishlist.repository.PurchasesRepository;
import wishlist.repository.UsersRepository;

@RestController
public class PurchasesController {

	private UsersRepository usersRepository;
	private PurchasesRepository purchasesRepository;

	public PurchasesController(UsersRepository usersRepository, PurchasesRepository purchasesRepository) {
		this.usersRepository = usersRepository;
		this.purchasesRepository = purchasesRepository;
	}

	public static class PurchasesAddRequest {
		public String name;
		public Boolean done;
		public Integer userId;
	}

	@RequestMapping(value = "/purchases/add", method = RequestMethod.POST)
	public ResponseEntity<Purchases> purchasesCreate(@RequestBody PurchasesAddRequest req) {
		Purchases purchases = new Purchases();
		purchases.setName(req.name);
		purchases.setDone(false);
		purchases.setUser(usersRepository.findById(req.userId).get());
		purchasesRepository.save(purchases);
		return new ResponseEntity<Purchases>(purchases, HttpStatus.OK);
	}

	@RequestMapping(value = "/purchases/{id}/archive", method = RequestMethod.POST)
	public ResponseEntity<Void> archiveTask(@PathVariable Integer id) {
		Purchases purchases = purchasesRepository.findById(id).get();
		purchases.setDone(true);
		purchasesRepository.save(purchases);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
