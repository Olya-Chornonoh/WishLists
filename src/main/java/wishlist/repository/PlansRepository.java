package wishlist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import wishlist.model.Plans;
import wishlist.model.Users;

public interface PlansRepository extends JpaRepository<Plans, Integer> {
	List<Plans> findAllByUser(Users user);
}
