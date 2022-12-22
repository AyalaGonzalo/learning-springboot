/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.news.repository;

import com.egg.news.entities.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 *
 * @author Ayala gonzalo
 */
public interface NewsRepository extends JpaRepository<News, String> {
    
    @Query("SELECT n FROM News n WHERE n.title = :title")
    public News searchByTitle(@Param("title") String title);
	
    
}
