package com.example.sg.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
@Table(name = "app_users")
public class AppUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userId;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;

	@Column(name="gender")
	private String gender;
	
	@Column(name="email")
	private String email;
	
	@Column(name="mobile_num")
	private String mobileNum;
	
	@Column(name="is_valid")
	private boolean isValid;
	
	@Column(name="created_date_time")
	private String createdDateTime;
	
	@OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<UserType> userTypes = new HashSet<>();
	
	//Mine!
//	@OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JsonIgnore
//	private Set<Job> jobs = new HashSet<>();
	
	public AppUser() {
		
	}
	
	public AppUser(String firstName, String lastName, String gender, String email, String mobileNum) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.email = email;
		this.mobileNum = mobileNum;
		this.isValid = true;
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
		this.createdDateTime = LocalDateTime.now().format(dateTimeFormatter);
	}
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public String getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(String createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	
	public Set<UserType> getUserTypes() {		
		return userTypes;
	}

	public void setUserTypes(Set<UserType> userTypes) {
		this.userTypes = userTypes;
	}
	
	public void addUserTypes(UserType userType) {
		this.userTypes.add(userType);
		userType.setAppUser(this);
	}

}
