package wishlist.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import wishlist.model.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {

	Optional<Users> findByUsernameAndPassword(String username, String password);
}
