package it.lea.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.lea.entities.Product;
import it.lea.entities.Question;
import it.lea.services.ProductService;
import it.lea.services.QuestionService;
import it.lea.services.QuestionnaireService;
import it.lea.utils.ImageUtils;

@WebServlet("/CreateQuestionnaire")
@MultipartConfig
public class CreateQuestionnaire extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "it.lea.services/ProductService")
	private ProductService productService;
	@EJB(name = "it.lea.services/QuestionnaireService")
	private QuestionnaireService questionnaireService;
	@EJB(name = "it.lea.services/QuestionService")
	private QuestionService questionService;

	public CreateQuestionnaire() {
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// If the user is not logged in (not present in session) redirect to the login
		HttpSession session = request.getSession();
		if (session.isNew() || session.getAttribute("user") == null) {
			String loginpath = getServletContext().getContextPath() + "/index.html";
			response.sendRedirect(loginpath);
			return;
		}

		String productName = null;
		Date date = null;
		Part imgFile = null;
		byte[] imgByteArray = null;
		Integer questionsNum = null;
		List<Question> questions = null;
		List<String> questionsText = new ArrayList<String>();

		String path = "/WEB-INF/CreationPage.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

		try {
			String caption = request.getParameter("caption");
			System.out.println("THE prod IS " + caption);

			productName = request.getParameter("product");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			date = (Date) sdf.parse(request.getParameter("date"));

			imgFile = request.getPart("picture");
			InputStream imgContent = imgFile.getInputStream();
			imgByteArray = ImageUtils.readImage(imgContent);

			questionsNum = (Integer) session.getAttribute("questionsNum");

			for (int i = 0; i < questionsNum; i++) {

				questionsText.add(request.getParameter(Integer.toString(i)));

			}

			if (productName == null || date.compareTo(new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000)) < 0
					|| imgByteArray.length == 0 || questionsNum == null || questionsText == null) {
				ctx.setVariable("message", "Please select the current or a future date");

				templateEngine.process(path, ctx, response.getWriter());
				return;
			}

		} catch (Exception e) {
			ctx.setVariable("message", "Error in creating the questionnaire");

			templateEngine.process(path, ctx, response.getWriter());
			return;
		}

		// Create Product

		Product product = null;

		try {
			product = productService.createProduct(imgByteArray, productName);
			questions = questionService.saveQuestions(questionsText);

			questionnaireService.saveQuestionnaire(date, product, questions);

		} catch (Exception e) {
			ctx.setVariable("message", "There is already a questionnaire for the selected date");

			templateEngine.process(path, ctx, response.getWriter());
			return;
		}

		ctx.setVariable("message", "The product and the questionnaire have been saved");
		session.removeAttribute("questionsNum");
		session.removeAttribute("product");
		session.removeAttribute("date");

		templateEngine.process(path, ctx, response.getWriter());

	}

}
