package com.user.realm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

@XmlRootElement
@Entity
@Table(name = "realm", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Realm implements Serializable {

	@Id
    @GeneratedValue
    private int id;
	
	@NotEmpty
	@Column(name="name", unique=true)
	private String name;
	
	private String key;
	private String description;
	
	public Realm() {
	}
	
	public Realm(int id, String name, String key, String description) {
		super();
		this.id = id;
		this.name = name;
		this.key = key;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	@XmlAttribute(name="id")
	public void setId(int id) {
		this.id = id;
	}

	@XmlAttribute(name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    public interface Existing {
    }
 
    public interface New {
    }
    
}
