package wishlist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import wishlist.model.Tasks;
import wishlist.repository.CategorylistRepository;
import wishlist.repository.TasksRepository;
import wishlist.repository.UsersRepository;

@RestController
public class TasksController {

	private TasksRepository tasksRepository;
	private UsersRepository usersRepository;
	private CategorylistRepository categorylistRepository;

	@Autowired
	public TasksController(TasksRepository tasksRepository, UsersRepository usersRepository,
			CategorylistRepository categorylistRepository) {
		this.tasksRepository = tasksRepository;
		this.usersRepository = usersRepository;
		this.categorylistRepository = categorylistRepository;
	}

	public static class TaskAddRequest {
		public String name;
		public String description;
		public Integer userId;
		public Integer categoryId;
	}

	@RequestMapping(value = "/tasks/add", method = RequestMethod.POST)
	public ResponseEntity<Tasks> createTask(@RequestBody TaskAddRequest req) {
		Tasks task = new Tasks();
		task.setName(req.name);
		task.setDescription(req.description);
		task.setDone(false);
		task.setUser(usersRepository.findById(req.userId).get());
		task.setCategories(req.categoryId != null ? categorylistRepository.findById(req.categoryId).get() : null);
		tasksRepository.save(task);
		return new ResponseEntity<Tasks>(task, HttpStatus.OK);
	}

	@RequestMapping(value = "/tasks/{id}/archive", method = RequestMethod.POST)
	public ResponseEntity<Void> archiveTask(@PathVariable Integer id) {
		Tasks task = tasksRepository.findById(id).get();
		task.setDone(true);
		tasksRepository.save(task);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
