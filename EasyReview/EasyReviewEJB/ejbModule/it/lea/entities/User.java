package it.lea.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "usr", schema = "db_easyr")
@NamedQueries({
		@NamedQuery(name = "User.checkCredentials", query = "SELECT u FROM User u  WHERE u.username = ?1 and u.password = ?2"),
		@NamedQuery(name = "User.getLeaderboard", query = "SELECT u FROM User u WHERE u.points>0 ORDER BY u.points DESC"),
		@NamedQuery(name = "User.hasDoneDailyQuestionnaire", query = "SELECT u FROM User u, FilledForm f WHERE f.user = u AND f.date = CURRENT_DATE AND u.id = ?1"),
		@NamedQuery(name = "User.hasDoneQuestionnaireByDate", query = "SELECT u FROM User u, FilledForm f WHERE f.user = u AND f.date = ?1"),
		@NamedQuery(name = "User.hasOpenedQuestionnaireByDate", query = "SELECT u FROM User u, Log l WHERE l.user = u ") })

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String username;
	private String email;
	private String password;
	private Boolean banned;

	@Column(name = "daily_points")
	private Integer points;

	@OneToMany(mappedBy = "user")
	private List<FilledForm> forms = new ArrayList<FilledForm>();

	@OneToMany(mappedBy = "user")
	private List<Log> logs;

	public User() {

	}

	public User(String username, String email, String password) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.banned = false;
		this.points = 0;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getBanned() {
		return banned;
	}

	public void setBanned(Boolean banned) {
		this.banned = banned;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public List<FilledForm> getForms() {
		return forms;
	}

	public void setForms(List<FilledForm> forms) {
		this.forms = forms;
	}

	public List<Log> getLogs() {
		return logs;
	}

	public void setLogs(List<Log> logs) {
		this.logs = logs;
	}

}
