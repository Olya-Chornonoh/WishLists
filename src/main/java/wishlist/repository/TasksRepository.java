package wishlist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import wishlist.model.Tasks;
import wishlist.model.Users;

public interface TasksRepository extends JpaRepository<Tasks, Integer> {
	List<Tasks> findAllByUser(Users user);
}
