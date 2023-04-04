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
@Table(name = "statuses")
public class Status {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "status_description")
	private String statusDescription;
	
	@OneToMany(mappedBy = "status", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<Job> jobs = new HashSet<>();
	
	@OneToMany(mappedBy = "status", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<Application> applications = new HashSet<>();
	
	public Status() {}
	
	public Status(int id, String statusDescription) {
		this.id = id;
		this.statusDescription = statusDescription;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
	
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
		application.setStatus(this);
	}
	
	public void removeApplication(Application application) {
		this.applications.remove(application);
	}
	
	public void addJob(Job job) {
		this.jobs.add(job);
		job.setStatus(this);
	}
	
	public void removeJob(Job job) {
		this.jobs.remove(job);
	}

	public final static Status OPEN = new Status(1, "Open");
	public final static Status APPLIED = new Status(2, "Applied");
	public final static Status ACCEPTED = new Status(3, "Accepted");
	public final static Status NOT_SELECTED = new Status(4, "Not Selected");
	public final static Status HIRED = new Status(5, "Hired");
	public final static Status COMPLETED = new Status(6, "Completed");
	public final static Status CLOSED = new Status(7, "Closed");
	public final static Status CLOSED_BY_ADMIN = new Status(8, "Closed by Admin");
}