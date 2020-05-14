package wishlist.model;

import java.io.Serializable;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "users")

public class Users implements Serializable {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "username")
	private String username;
	@Column(name = "password")
	private String password;

	@OneToMany(mappedBy = "user")
	private Set<Categorylist> categories;

	public Set<Categorylist> getCategories() {
		return categories;
	}

	public void setCategories(Set<Categorylist> categories) {
		this.categories = categories;
	}

	@OneToMany(mappedBy = "user")
	private Set<Plans> plans;

	public Set<Plans> getPlans() {
		return plans;
	}

	public void setPlans(Set<Plans> plans) {
		this.plans = plans;
	}

	@OneToMany(mappedBy = "user")
	private Set<Tasks> tasks;

	public Set<Tasks> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Tasks> tasks) {
		this.tasks = tasks;
	}

	@OneToMany(mappedBy = "user")
	private Set<Purchases> purchases;

	public Set<Purchases> getPurchases() {
		return purchases;
	}

	public void setPurchases(Set<Purchases> purchases) {
		this.purchases = purchases;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public String getPasswd() {
		return password;
	}

	public void setPasswd(String password) {
		this.password = password;
	}
	
}
