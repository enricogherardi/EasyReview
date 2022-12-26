package it.lea.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "answer", schema = "db_easyr")
 public class Answer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	
	@ManyToOne
	@JoinColumn(name = "question_id")
	private Question question;
	
	
	@ManyToOne  
	@JoinColumn(name="form_id")
	private FilledForm form;
	
	
	private String response;
	
	public Answer() {

	}
	
	
	public Answer(String response, Question question, FilledForm form) {
		
		this.response=response;
		this.question=question;
		this.form=form;
		
	}
	

	public Answer(String response, Question question) {
		
		this.response=response;
		this.question=question;
		
		
	}
	 

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	
	public FilledForm getForm() {
		return form;
	}

	public void setForm(FilledForm form) {
		this.form = form;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
	
}
