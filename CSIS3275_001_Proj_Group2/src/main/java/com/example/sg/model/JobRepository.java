package com.example.sg.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
//	List<Job> findByJobTitle(String jobTitle);
	List<Job> findByJobTitleContainingIgnoreCase(String jobTitle);
	Optional<Job> findById(String id);
}
