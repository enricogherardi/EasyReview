
package it.lea.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "filled_form", schema = "db_easyr")
@NamedQuery(name = "FilledForm.retrieveAnswersByDate", query = "SELECT f FROM FilledForm f  WHERE f.date = ?1")

public class FilledForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "questionnaire_id")
	private Questionnaire questionnaire;

	@OneToMany(mappedBy = "form", cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.EAGER)
	private List<Answer> answers = new ArrayList<Answer>();

	@Temporal(TemporalType.DATE)
	@Column(name = "date_form")
	private Date date;

	private Integer age;
	private String sex;
	private String expertice;

	private Integer score;

	public FilledForm() {

	}

	public FilledForm(User user, Questionnaire questionnaire, List<Answer> answers, Integer age, String sex,
			String expertice) {
		super();
		this.user = user;
		this.questionnaire = questionnaire;
		this.answers = answers;
		this.date = questionnaire.getDate();
		this.age = age;
		this.sex = sex;
		this.expertice = expertice;
		this.score = 0;
	}

	public FilledForm(User user, Questionnaire questionnaire, Integer age, String sex, String expertice) {
		super();
		this.user = user;
		this.questionnaire = questionnaire;
		this.date = questionnaire.getDate();
		this.age = age;
		this.sex = sex;
		this.expertice = expertice;
		this.score = 0;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

 
	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getExpertice() {
		return expertice;
	}

	public void setExpertice(String expertice) {
		this.expertice = expertice;
	}

	public void addAnswer(Answer answer) {
		getAnswers().add(answer);
		answer.setForm(this);
	}

}
