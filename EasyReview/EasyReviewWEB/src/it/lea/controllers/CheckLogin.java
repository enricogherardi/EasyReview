package it.lea.controllers;

import java.io.IOException;
import javax.ejb.EJB;
import javax.persistence.NonUniqueResultException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import it.lea.entities.User;
import it.lea.exceptions.CredentialsException;
import it.lea.services.UserService;


@WebServlet("/CheckLogin")
public class CheckLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB(name = "it.lea.services/UserService")
	private UserService usrService;

	public CheckLogin() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// obtain and escape params
		String usrn = null;
		String pwd = null;
		try {
			usrn = request.getParameter("username");
			pwd = request.getParameter("password");
			if (usrn == null || pwd == null || usrn.isEmpty() || pwd.isEmpty()) {
				throw new Exception("Missing or empty credential value");
			}

		} catch (Exception e) {
			// for debugging only e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
			return;
		}
		User user;
		try {
			// query db to authenticate for user
 			user = usrService.checkCredentials(usrn, pwd);
 		} catch (CredentialsException | NonUniqueResultException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check credentials");
			return;
		}

		// If the user exists, add info to the session and go to home page, otherwise
		// show login page with error message
		if (user == null) {
			System.out.println("Messaggio di errore-> username o pass errati");
		} else {
			request.setAttribute("user", usrn);
			RequestDispatcher rd = request.getRequestDispatcher("ShowInfo.jsp");
			rd.forward(request, response);
		}
	}

}