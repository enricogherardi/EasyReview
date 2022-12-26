package it.lea.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "product", schema = "db_easyr")
@NamedQuery(name = "Product.getProdOfToday", query = "SELECT p FROM Questionnaire q JOIN q.product p WHERE q.date=CURRENT_DATE")

public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToMany(mappedBy = "product")
	private List<Questionnaire> questionnaires = new ArrayList<Questionnaire>();

	@OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
	private List<Review> reviews;

	@Basic(fetch = FetchType.EAGER)
	@Lob
	private byte[] photoimage;

	@Column(name = "prod_name")
	private String name;

	public Product() {

	}

	public Product(byte[] photoimage, String name) {
		super();
		this.photoimage = photoimage;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Questionnaire> getQuestionnaires() {
		return questionnaires;
	}

	public void setQuestionnaires(List<Questionnaire> questionnaires) {
		this.questionnaires = questionnaires;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public byte[] getPhotoimage() {
		return photoimage;
	}

	public void setPhotoimage(byte[] photoimage) {
		this.photoimage = photoimage;
	}

	public String getPhotoimageData() {
		return Base64.getMimeEncoder().encodeToString(photoimage);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
