/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.news.repository;

import com.egg.news.entities.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Ayala gonzalo
 */
public interface AdminRepository extends JpaRepository<AdminUser,String>{
    
    @Query("SELECT a from AdminUser a WHERE a.userName = :uName")
    public String findByName(@Param("uName") String uName);
    
}
