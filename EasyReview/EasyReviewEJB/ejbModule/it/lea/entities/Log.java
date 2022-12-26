package it.lea.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "log", schema = "db_easyr")
@NamedQuery(name = "Log.hasOpenedQuestionnaireByDate", query = "SELECT DISTINCT l.user FROM Log l WHERE l.date = ?1 AND l.user.banned=false")
public class Log implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Temporal(TemporalType.DATE)
	@Column(name = "date_log")
	private Date date;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ts")
	private Date timestamp;

	public Log() {

	}

	public Log(User user, Date date, Date timestamp) {
		super();
		this.user = user;
		this.date = date;
		this.timestamp = timestamp;
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

}
