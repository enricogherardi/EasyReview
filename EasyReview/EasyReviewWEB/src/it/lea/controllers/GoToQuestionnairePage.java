package it.lea.controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// import it.lea.entities.Answer;
// import it.lea.entities.Questionnaire;
import it.lea.services.AnswerService;
import it.lea.services.QuestionnaireService;

@WebServlet("/GoToQuestionnairePage")
public class GoToQuestionnairePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB(name = "it.lea.services/QuestionnaireService")
	private QuestionnaireService questService;
	@EJB(name = "it.lea.services/AnswerService")
	private AnswerService ansService;

	public GoToQuestionnairePage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Questionnaire quest = null;
		try {
			quest = questService.getQuestionnaireOfToday();
		} catch (Exception e) {
			System.out.println("errore homepage");
		}
		// System.out.println("Questionario" + quest.getDate() + quest.getProd() + quest.getnQMark() + quest.getqMark1()
		//		+ quest.getqMark2() + quest.getqMark3() + quest.getqMark4() + quest.getqMark5() + quest.getqMark6()
		//		+ quest.getqMark7() + quest.getqMark1());
	}
	*/

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		/*Answer ans = null;
		try {
			// query db to authenticate for user
			ans = ansService.insertAnswer();

		} catch (Exception e) {
			request.setAttribute("answerError", "ANswer error");
			// RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			// rd.include(request, response);
			return;

		}*/

		// System.out.println("la nuova Answer è:" + ans.getSex() + ans.getaMark1() + ans.getaMark10() + ans.getExpertice());
	}

}
