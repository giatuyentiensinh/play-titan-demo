package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "lists", query = "SELECT l FROM User l")
public class User {

	@Id
	@GeneratedValue
	public Integer id;
	public String name;
}
