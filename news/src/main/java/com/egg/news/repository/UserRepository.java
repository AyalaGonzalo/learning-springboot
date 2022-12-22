/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.news.repository;

import com.egg.news.entities.NewUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Ayala gonzalo
 */
public interface UserRepository extends JpaRepository<NewUser,String>{
    
    
	@Query("SELECT u FROM NewUser u WHERE u.userName = :uName")
	public NewUser findByUName(@Param("uName")String uName);
	
    
    
}
