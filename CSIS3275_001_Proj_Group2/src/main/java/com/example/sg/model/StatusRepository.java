package com.example.sg.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
	Optional<Status> findById(String id);
	Optional<Status> findByStatusDescription(String statusDescription);
}
