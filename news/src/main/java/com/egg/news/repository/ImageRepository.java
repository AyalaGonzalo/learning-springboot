/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.egg.news.repository;

import com.egg.news.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Ayala gonzalo
 */
public interface ImageRepository extends JpaRepository<Image,String> {
    
}
