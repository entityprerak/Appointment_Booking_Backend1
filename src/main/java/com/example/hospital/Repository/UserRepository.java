package com.example.hospital.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hospital.entitities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
}
