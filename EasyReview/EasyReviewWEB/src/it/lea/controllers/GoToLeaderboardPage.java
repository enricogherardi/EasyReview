package it.lea.controllers;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.lea.entities.User;
import it.lea.services.UserService;

@WebServlet("/GoToLeaderboardPage")
public class GoToLeaderboardPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB(name = "it.lea.services/UserService")
	private UserService userService;

	public GoToLeaderboardPage() {
		super();
		// TODO Auto-generated constructor stub
	}
	/*
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<User> userList = null;
		try {
			userList = userService.getLeaderboard();
		} catch (Exception e) {
			System.out.println("errore leaderboardPage");
		}
		for (User u : userList) {
			// System.out.println(u.getUsername() + "\t" + u.getPointOfToday());
		}

	}*/

}
