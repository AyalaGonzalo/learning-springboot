/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.news.repository;

import com.egg.news.entities.Journalist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Ayala gonzalo
 */
public interface JournalistRepository extends JpaRepository<Journalist, String> {
    
    @Query("SELECT j from Journalist j WHERE j.userName = :uName")
    public Journalist findByName(@Param("uName")String uName);
    
    
    @Query("SELECT j from Journalist j WHERE j.id = :id")
    public Journalist findBiId(@Param("id") String id);
    
}
