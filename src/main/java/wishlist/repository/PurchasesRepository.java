package wishlist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import wishlist.model.Purchases;
import wishlist.model.Users;

public interface PurchasesRepository extends JpaRepository<Purchases, Integer> {
	List<Purchases> findAllByUser(Users user);
}
