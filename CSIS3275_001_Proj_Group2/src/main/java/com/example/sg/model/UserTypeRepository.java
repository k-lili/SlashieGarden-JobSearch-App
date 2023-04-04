package com.example.sg.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Long> {
	Optional<UserType> findByAppUserUserIdAndTypeTypeId(String appUserUserId, String typeId);
	Optional<UserType> findByUserTypeId(String userTypeId);
}
