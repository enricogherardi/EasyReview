package it.lea.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.lea.entities.Product;

@Stateless
public class ProductService {
	@PersistenceContext(unitName = "EasyReviewEJB")
	private EntityManager em;

	public ProductService() {

	}

	public Product getProductOfToday() throws Exception {
		List<Product> prod = null;
		try {
			prod = em.createNamedQuery("Product.getProdOfToday", Product.class).getResultList();

		} catch (Exception e) {
			throw new Exception("Could not find the product of the day");
		}

		if (prod.isEmpty()) {
			return null;

		} else {
			return prod.get(0);

		}
	}

	public Product createProduct(byte[] photoimage, String name) throws Exception {

		Product product = new Product(photoimage, name);

		try {

			em.persist(product);
			
		} catch (Exception e) {
			
			throw new Exception("Error creating the product");
		}
		return product;

	}

}
