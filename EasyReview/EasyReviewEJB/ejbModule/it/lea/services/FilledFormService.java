package it.lea.services;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.lea.entities.Answer;
import it.lea.entities.FilledForm;
import it.lea.entities.Questionnaire;
import it.lea.entities.User;

@Stateless
public class FilledFormService {
	@PersistenceContext(unitName = "EasyReviewEJB")
	private EntityManager em;

	public FilledFormService() {

	}

	public FilledForm saveFilledForm(User user, Questionnaire questionnaire, List<Answer> answers, Integer age,
			String sex, String expertice) throws SQLException {

		FilledForm form = null;
		form = new FilledForm(user, questionnaire, age, sex, expertice);

		for (Answer a : answers) {

			form.addAnswer(a);
		}

		questionnaire.addFilledForm(form);

		user.getForms().add(form);
		em.persist(form);

		return form;

	}

	public List<FilledForm> retrieveByDate(Date date) throws Exception {

		List<FilledForm> forms = null;
		try {
			forms = em.createNamedQuery("FilledForm.retrieveAnswersByDate", FilledForm.class).setParameter(1, date)
					.getResultList();
		} catch (Exception e) {
			throw new Exception("Error searching the answers");
		}

		return forms;

	}

	public void deleteFilledForm(Integer formId) throws Exception {

		try {
			FilledForm form = em.find(FilledForm.class, formId);
 
			em.remove(form);
		} catch (Exception e) {

 			throw new Exception("Error deleting the form");
		}

	}

}
