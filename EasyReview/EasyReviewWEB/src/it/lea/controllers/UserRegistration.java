package it.lea.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*import it.lea.entities.Answer;
import it.lea.entities.Product;
import it.lea.entities.Questionnaire;*/
import it.lea.entities.User;
import it.lea.exceptions.RegistrationException;
import it.lea.services.UserService;
import java.sql.Date;
//import java.util.Date;


@WebServlet("/UserRegistration")
public class UserRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB(name = "it.lea.services/UserService")
	private UserService usrService;

	public UserRegistration() {
		super(); 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub 
		
		/* *******************************************************************************************************************
		//Date sqlDate = new Date(java.util.Date().getTime());
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		Product pro = new Product("hh...s.s", "playstation9");
		Questionnaire qpr= new Questionnaire(sqlDate, pro, 2, "prova?", "ciao?", "pp?", "bbb", "bb", null, null , null, null , null );
		 ************************************************************************************************************************** */
		
		String usrn = null;
		String pwd = null;
		String confirmpwd = null;
		String email = null;
		usrn = request.getParameter("user");
		pwd = request.getParameter("pass");
		email = request.getParameter("email");
		confirmpwd = request.getParameter("confirmpass");
		try {
			if (usrn == null || pwd == null || confirmpwd == null || email == null) {
				throw new RegistrationException("Missing or empty value");
			}
			if (!pwd.equals(confirmpwd)) {
				throw new RegistrationException("The two passwords do not match");
			}
		} catch (RegistrationException e) {
			// request.getSession().setAttribute("passwordMatch", "false");
			// response.sendError(HttpServletResponse.SC_BAD_REQUEST, "The two passwords do
			// not match");
			request.setAttribute("passwordMatch", "The two passwords do not match");
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.include(request, response);
			return;
		}

		
		
		User user = null;
		try {
			// query db to authenticate for user
			user = usrService.registerUser(usrn, confirmpwd, email);
			
		} catch (RegistrationException e) {
			request.setAttribute("registrationError", "Email or username already used");
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.include(request, response);
			return;

		}
		

		if (user == null) {
			System.out.println("Messaggio di errore-> registrazione fallita");
		} else {
			request.setAttribute("user", usrn);
			RequestDispatcher rd = request.getRequestDispatcher("ShowInfo.jsp");
			rd.forward(request, response);
		}
	}

}
