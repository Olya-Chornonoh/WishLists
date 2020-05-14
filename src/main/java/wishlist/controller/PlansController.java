package wishlist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import wishlist.model.Plans;
import wishlist.repository.PlansRepository;
import wishlist.repository.UsersRepository;

@RestController
public class PlansController {

	private UsersRepository usersRepository;
	private PlansRepository plansRepository;

	@Autowired
	public PlansController(UsersRepository usersRepository, PlansRepository plansRepository) {
		this.usersRepository = usersRepository;
		this.plansRepository = plansRepository;
	}

	public static class PlanAddRequest {
		public Boolean shortterm;
		public Integer userId;
		public String name;
		public Boolean done;
	}

	@RequestMapping(value = "/plans/add", method = RequestMethod.POST)
	public ResponseEntity<Plans> createPlan(@RequestBody PlanAddRequest req) {
		Plans plans = new Plans();
		plans.setName(req.name);
		plans.setDone(false);
		plans.setUser(usersRepository.findById(req.userId).get());
		plans.setShortterm(req.shortterm);
		plansRepository.save(plans);
		return new ResponseEntity<Plans>(plans, HttpStatus.OK);
	}

	@RequestMapping(value = "/plans/{id}/archive", method = RequestMethod.POST)
	public ResponseEntity<Void> archivePlan(@PathVariable Integer id) {
		Plans plans = plansRepository.findById(id).get();
		plans.setDone(true);
		plansRepository.save(plans);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
