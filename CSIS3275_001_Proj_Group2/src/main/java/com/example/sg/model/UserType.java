package com.example.sg.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name="user_types")
public class UserType {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="usertype_id")
	private long userTypeId;
	
	@ManyToOne(fetch = FetchType.LAZY, optional=false) //Usertype is many, user is 1 
	@JoinColumn(name = "user_id", nullable=false)
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@JsonProperty("appUser")
	private AppUser appUser;
	
	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public void addAppUser(String firstName, String lastName, String gender, String email, String mobileNum) {
		appUser = new AppUser(firstName, lastName, gender, email, mobileNum);
	}

	@ManyToOne(fetch = FetchType.LAZY, optional=false, cascade = CascadeType.MERGE) //Usertype is many, type is 1 
	@JoinColumn(name = "type_id", nullable=false)
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@JsonProperty("type")
	private Type type = Type.EMPLOYER; //default employer
		
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void addType(int typeId) {
		switch (typeId) {
		case 1:
			this.type = Type.EMPLOYER;
			break;
		case 2:
			this.type = Type.APPLICANT;
			break;
		case 3:
			this.type = Type.ADMINISTRATOR;
			break;
		}
	}
	
	@OneToMany(mappedBy = "userType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<Job> jobs = new HashSet<>();
	
	@OneToMany(mappedBy = "userType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<Application> applications = new HashSet<>();
	
	public Set<Job> getJobs() {
		return jobs;
	}

	public void setJobs(Set<Job> jobs) {
		this.jobs = jobs;
	}

	public Set<Application> getApplications() {
		return applications;
	}

	public void setApplications(Set<Application> applications) {
		this.applications = applications;
	}
	
	public void addApplication(Application application) {
		this.applications.add(application);
		application.setUserType(this);
	}

	//constructor
	public UserType() {
	}
	
	public UserType(AppUser appUser, Type type) {
		this.appUser = appUser;
		this.type = type;
	}
	
	//getters and setters
	public long getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(long userTypeId) {
		this.userTypeId = userTypeId;
	}

}