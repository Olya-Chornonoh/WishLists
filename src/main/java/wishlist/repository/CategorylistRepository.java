package wishlist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import wishlist.model.Categorylist;
import wishlist.model.Users;

public interface CategorylistRepository extends JpaRepository<Categorylist, Integer> {
	List<Categorylist> findAllByUser(Users user);
}
