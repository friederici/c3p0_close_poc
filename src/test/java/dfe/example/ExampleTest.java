package dfe.example;

import java.util.List;

import org.springframework.test.annotation.AbstractAnnotationAwareTransactionalTests;

public class ExampleTest extends AbstractAnnotationAwareTransactionalTests {

	private Dao<User> userDao;

	public void setUserDao(Dao<User> userDao) {
		this.userDao = userDao;
	}

	@Override
	protected String[] getConfigLocations() {
		return new String[] { "classpath:applicationContext.xml" };
	}

	public void testListUsers() {
		System.out.println("testListUsers()");
		List<User> users = this.userDao.getAll();
		System.out.println("users: " + users.toString());
		assertNotNull(users);
	}

	public void testCreateUser() {
		System.out.println("testCreateUser()");

		User u = User.builder().email("mail").name("name").build();
		this.userDao.save(u);

		List<User> users = this.userDao.getAll();
		System.out.println("users: " + users.toString());
		assertTrue(users.contains(u));
	}

}
