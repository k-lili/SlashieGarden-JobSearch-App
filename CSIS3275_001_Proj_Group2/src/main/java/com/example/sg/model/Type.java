package com.example.sg.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="types")
public class Type {
		
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="type_id", nullable = false)
	private int typeId;
	
	@Column(name="type_description")
	private String typeDescription;

	@OneToMany(mappedBy = "type", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<UserType> userTypes = new HashSet<>();
	
	public Set<UserType> getUserTypes() {
		return userTypes;
	}

	public void setUserTypes(Set<UserType> userTypes) {
		this.userTypes = userTypes;
	}

	public void addTypes(UserType userType) {
		this.userTypes.add(userType);
		userType.setType(this);
	}
	
	public Type() {
	}
	
	public Type(int typeId, String typeDescription) {
		this.typeId = typeId;
		this.typeDescription = typeDescription;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getTypeDescription() {
		return typeDescription;
	}

	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}

	public final static Type EMPLOYER = new Type(1, "Employer");
	public final static Type APPLICANT = new Type(2, "Applicant");
	public final static Type ADMINISTRATOR = new Type(3, "Administrator");
}