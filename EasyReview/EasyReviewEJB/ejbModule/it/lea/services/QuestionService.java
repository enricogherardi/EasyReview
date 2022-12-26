package it.lea.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.lea.entities.Question;

@Stateless
public class QuestionService {
	@PersistenceContext(unitName = "EasyReviewEJB")
	private EntityManager em;

	public QuestionService() {

	}

	public List<Question> saveQuestions(List<String> questions) {

		List<Question> quest = new ArrayList<Question>();
		if (questions != null) {

			for (int i = 0; i < questions.size(); i++) {

				quest.add(new Question(questions.get(i)));
			}
		}

		return quest;

	}

}
