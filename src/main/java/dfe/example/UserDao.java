package dfe.example;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


public class UserDao extends HibernateDaoSupport implements Dao<User> {

	@Override
	public Optional<User> get(long id) {
		return Optional.ofNullable((User)getHibernateTemplate().get(User.class, id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAll() {
		return getHibernateTemplate().find("from User");
	}

	@Override
	public void save(User user) {
		getHibernateTemplate().save(user);
	}

	@Override
	public void update(User user, String[] params) {
		user.setName(Objects.requireNonNull(params[0], "Name cannot be null"));
		user.setEmail(Objects.requireNonNull(params[1], "Email cannot be null"));
		getHibernateTemplate().update(user);
	}

	@Override
	public void delete(User user) {
		getHibernateTemplate().delete(user);
	}

}
