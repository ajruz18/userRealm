package com.user.realm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "realm")
@Entity
@Table(name = "userRealm")
public class UserRealm implements Serializable {

	@Id
	@SequenceGenerator(name = "USERREALM_ID_GENERATOR", sequenceName = "USERREALM_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERREALM_ID_GENERATOR")
	private int id;
	
	@NotEmpty
	@Column(name="name", unique=true)
	private String name;
	
	private String key;
	private String description;
	
	public UserRealm() {
	}
	
	public UserRealm(int id, String name, String key, String description) {
		super();
		this.id = id;
		this.name = name;
		this.key = key;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	@JacksonXmlProperty(isAttribute = true, localName = "id")
	public void setId(int id) {
		this.id = id;
	}

	@XmlAttribute(name="name")
	@JacksonXmlProperty(isAttribute = true, localName = "name")
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
