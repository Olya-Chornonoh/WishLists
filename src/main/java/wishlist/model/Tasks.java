package wishlist.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "tasks")
public class Tasks implements Serializable {

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "category_id", nullable = true)
	private Categorylist categories;

	public Categorylist getCategories() {
		return categories;
	}

	public void setCategories(Categorylist categories) {
		this.categories = categories;
	}

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private Users user;

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "done")
	private Boolean done;

	public Boolean getDone() {
		return done;
	}

	public void setDone(Boolean done) {
		this.done = done;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
