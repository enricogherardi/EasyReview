package it.lea.services;

import java.sql.Timestamp;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import it.lea.entities.Admin;
import it.lea.exceptions.CredentialsException;
import it.lea.exceptions.RegistrationException;

@Stateless
public class AdminService {
	@PersistenceContext(unitName = "EasyReviewEJB")
	private EntityManager em;

	public AdminService() {
	}

	public Admin checkCredentials(String usrn, String pwd) throws CredentialsException, NonUniqueResultException {
		List<Admin> adminList = null;
		try {
			adminList = em.createNamedQuery("Admin.checkCredentials", Admin.class).setParameter(1, usrn)
					.setParameter(2, pwd).getResultList();
		} catch (PersistenceException e) {
			throw new CredentialsException("Could not verify credentals");
		}
		if (adminList.isEmpty())
			return null;
		else if (adminList.size() == 1)
			return adminList.get(0);
		throw new NonUniqueResultException("More than one Admin registered with same credentials");

	}

}
