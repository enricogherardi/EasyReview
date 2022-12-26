package it.lea.services;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import it.lea.entities.FilledForm;
import it.lea.entities.Log;
// import it.lea.entities.Product;
// import it.lea.entities.Questionnaire;
import it.lea.entities.User;
import it.lea.exceptions.CredentialsException;
import it.lea.exceptions.RegistrationException;

@Stateless
public class UserService {
	@PersistenceContext(unitName = "EasyReviewEJB")
	private EntityManager em;

	public UserService() {
	}

	public User findById(int userId) {
		return (em.find(User.class, userId));
	}

	public User checkCredentials(String usrn, String pwd) throws CredentialsException, NonUniqueResultException {
		List<User> uList = null;
		try {
			uList = em.createNamedQuery("User.checkCredentials", User.class).setParameter(1, usrn).setParameter(2, pwd)
					.getResultList();
		} catch (PersistenceException e) {
			throw new CredentialsException("Could not verify credentals");
		}
		if (uList.isEmpty())
			return null;
		else if (uList.size() == 1)
			return uList.get(0);
		throw new NonUniqueResultException("More than one user registered with same credentials");

	}

	public Boolean checkIfBanned(Integer userId) {

		User u = em.find(User.class, userId);
		em.refresh(u);

		return u.getBanned();

	}

	public Boolean hasDoneDailyQuestionnaire(Integer userId) throws Exception {

		List<User> uList = null;
		try {
			uList = em.createNamedQuery("User.hasDoneDailyQuestionnaire", User.class).setParameter(1, userId)
					.getResultList();
		} catch (Exception e) {
			throw new Exception("Error searching the user");
		}
		if (uList.isEmpty())
			return false;
		else
			return true;

	}

	public List<User> hasDoneQuestionnaireByDate(Date date) throws Exception {

		List<User> uList = null;
		try {
			uList = em.createNamedQuery("User.hasDoneQuestionnaireByDate", User.class).setParameter(1, date)
					.getResultList();
		} catch (Exception e) {
			throw new Exception("Error searching the users");
		}

		return uList;

	}

	public Log saveLog(User user) {

		Log log = null;

		if (user != null) {

			log = new Log(user, new Date((System.currentTimeMillis())), new Timestamp(System.currentTimeMillis()));
			em.persist(log);

		}

		return log;

	}

	public User registerUser(String usrn, String email, String pwd) throws RegistrationException {

		User user = new User(usrn, email, pwd);
		try {
			em.persist(user);
			em.flush();

		} catch (PersistenceException e) {
			throw new RegistrationException("Could not register the user");
		}
		return user;
	}

	public List<User> getLeaderboard(Integer userId) throws Exception {

		List<User> userList = null;
		User u = em.find(User.class, userId);
		try {
			em.refresh(u);
			userList = em.createNamedQuery("User.getLeaderboard", User.class).getResultList();

		} catch (Exception e) {
			throw new Exception("Could not find the leaderboard");
		}
		return userList;
	}

	public List<User> hasOpenedQuestionnaireByDate(Date date) throws Exception {

		List<User> uList = null;
		try {
			uList = em.createNamedQuery("Log.hasOpenedQuestionnaireByDate", User.class).setParameter(1, date)
					.getResultList();

		} catch (Exception e) {
			throw new Exception("Error searching the users");
		}

		return uList;

	}

}
