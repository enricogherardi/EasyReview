package it.lea.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import it.lea.services.FilledFormService;
import it.lea.services.UserService;
import it.lea.entities.FilledForm;
import it.lea.entities.User;

@WebServlet("/GetStatistics")
public class GetStatistics extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "it.lea.services/UserService")
	private UserService userService;
	@EJB(name = "it.lea.services/FilledFormService")
	private FilledFormService formService;

	public GetStatistics() {
		super();
	}

	public void init() throws ServletException {

		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// If the user is not logged in (not present in session) redirect to the login
		String loginpath = getServletContext().getContextPath() + "/index.html";
		HttpSession session = request.getSession();
		if (session.isNew() || session.getAttribute("user") == null) {
			response.sendRedirect(loginpath);
			return;
		}

		Date date = null;

		String path = "/WEB-INF/InspectionPage.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

		try {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			date = (Date) sdf.parse(request.getParameter("date"));

			if (date.compareTo(new Date(System.currentTimeMillis())) > 0) {

				ctx.setVariable("message", "Please, select a past or current date!");

				templateEngine.process(path, ctx, response.getWriter());
				return;
			}

		} catch (Exception e) {
			ctx.setVariable("message", "Please, insert a valid date!");

			templateEngine.process(path, ctx, response.getWriter());
			return;
		}

		List<User> users = null;
		List<User> logged = null;
		List<FilledForm> forms = null;

		try {
			users = userService.hasDoneQuestionnaireByDate(date);
			logged = userService.hasOpenedQuestionnaireByDate(date);
			forms = formService.retrieveByDate(date);

			// Retrieve users that logged but then canceled the questionnaire
			for (int i = 0; i < users.size(); i++) {
				for (int k = 0; k < logged.size(); k++) {
					if (logged.get(k).getId().equals(users.get(i).getId())) {

						logged.remove(k);
					}
				}

			}

		} catch (Exception e) {
			ctx.setVariable("message", "There is no questionnaire for the selected date!");

			templateEngine.process(path, ctx, response.getWriter());
			return;
		}

		ctx.setVariable("users", users);
		ctx.setVariable("canceled", logged);
		ctx.setVariable("forms", forms);

		templateEngine.process(path, ctx, response.getWriter());

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
