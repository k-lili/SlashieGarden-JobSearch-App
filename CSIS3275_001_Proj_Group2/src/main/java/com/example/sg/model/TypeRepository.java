package com.example.sg.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
	
	List<Type> findByTypeDescription(String typeDescription);
	Optional<Type> findByTypeId(String typeId);
}