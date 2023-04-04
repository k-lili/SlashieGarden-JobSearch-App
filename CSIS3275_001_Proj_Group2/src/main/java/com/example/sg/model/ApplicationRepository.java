package com.example.sg.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long>{
	Optional<Application> findById(String id); 
	List<Application> findByJob(Job job);
}
