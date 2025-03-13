package com.example.hospital.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.hospital.entitities.User;


public interface UserRegistrationRepository extends  JpaRepository<User, Long>{
	
    public User findByEmail(String email);
	
	public User findByUsername(String username);
	
	public User findByEmailAndPassword(String email, String password);
	
	public List<User> findProfileByEmail(String email);

	

}
