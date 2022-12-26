package it.lea.controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.lea.entities.Product;
import it.lea.services.ProductService;

@WebServlet("/GoToHomePage")
public class GoToHomePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB(name = "it.lea.services/ProductService")
	private ProductService prodService;

	public GoToHomePage() {
		super();
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Product prod = null;
 		try {
			prod = prodService.getProductOfToday();
		} catch (Exception e) {
			System.out.println("errore homepage");
		}
 		
		System.out.println("Prodotto" + prod.getName());

	}

}
