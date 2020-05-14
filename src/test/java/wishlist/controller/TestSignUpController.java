//package wishlist.controller;
//
//import static org.junit.Assert.assertSame;
//
//import java.security.NoSuchAlgorithmException;
//
//import org.junit.Test;
//import org.mockito.Mockito;
//
//import wishlist.repository.UsersRepository;
//
//public class TestSignUpController {
//	@Test
//	public void shouldConfirmPasswordAndSaveToDb() throws NoSuchAlgorithmException {
//		UsersRepository usersRepository = Mockito.mock(UsersRepository.class);
//		
//		SignUpController controller = new SignUpController(usersRepository);
//		
//		String result = controller.postSignup("Test", "111111", "111111");
//		
//		assertSame("login", result);
//	}
//	
//	@Test
//	public void shouldNotConfirmPassword() throws NoSuchAlgorithmException {
//		SignUpController controller = new SignUpController(null);
//		
//		String result = controller.postSignup("тест", "111111", "222222");
//		
//		assertSame("signup", result);
//	}
//}
