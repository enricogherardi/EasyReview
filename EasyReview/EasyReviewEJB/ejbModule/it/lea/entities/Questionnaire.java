package it.lea.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.ArrayList;
import java.util.Date;

@Entity
@Table(name = "questionnaire", schema = "db_easyr")

@NamedQueries({
		@NamedQuery(name = "Questionnaire.getQuestOfToday", query = "SELECT q FROM Questionnaire q WHERE q.date=CURRENT_DATE"),
		@NamedQuery(name = "Questionnaire.getQuestByDate", query = "SELECT q FROM Questionnaire q WHERE q.date= ?1") })

public class Questionnaire implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToMany(mappedBy = "questionnaire", cascade= CascadeType.REMOVE)
	private List<FilledForm> forms;

	@Temporal(TemporalType.DATE)
	@Column(name = "date_questionnaire")
	private Date date;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@OneToMany(mappedBy = "questionnaire", fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	private List<Question> questions = new ArrayList<Question>();

	public Questionnaire() {

	}

	public Questionnaire(Date date, Product product, List<Question> questions) {
		super();
		this.date = date;
		this.product = product;
		this.questions = questions;
	}

	public Questionnaire(Date date, Product product) {
		super();
		this.date = date;
		this.product = product;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<FilledForm> getForms() {
		return forms;
	}

	public void setForms(List<FilledForm> forms) {
		this.forms = forms;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public void addFilledForm(FilledForm form) {
		getForms().add(form);
	}

	public void addQuestion(Question question) {
		getQuestions().add(question);
		question.setQuestionnaire(this);
	}

}
