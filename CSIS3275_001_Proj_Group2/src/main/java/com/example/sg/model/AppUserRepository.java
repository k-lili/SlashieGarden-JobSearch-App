package com.example.sg.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
	List<AppUser> findByFirstName(String firstName);
//	List<AppUser> findByLastName(String lastName);
	List<AppUser> findByLastNameContainingIgnoreCase(String lastName);
	Optional<AppUser> findByEmail(String email);
	List<AppUser> findByMobileNum(String mobileNum);
	
	Optional<AppUser> findByUserId(String userId);
}
