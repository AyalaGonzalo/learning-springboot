/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.news.services;

import com.egg.news.entities.Image;
import com.egg.news.myexception.MyException;
import com.egg.news.repository.ImageRepository;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Ayala gonzalo
 */
@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepo;

    public Image uploadImage(MultipartFile imageFile) throws IOException {

        try{
            if (imageFile != null) {

                Image image = new Image();
                image.setMime(imageFile.getContentType());
                image.setName(imageFile.getName());
                image.setContent(imageFile.getBytes());
                return imageRepo.save(image);

            }
        }catch(IOException ex){
        
            System.err.println(ex.getMessage());
        
        }
        return null;
		
    }

    public Image update(MultipartFile imageFile, String idImage) throws MyException, IOException {

        try {

            Image image = new Image();

            if (idImage != null) {

                Optional<Image> answer = imageRepo.findById(idImage);

                if (answer.isPresent()) {

                    image = answer.get();

                }

            }

            image.setMime(imageFile.getContentType());
            image.setName(imageFile.getName());
            image.setContent(imageFile.getBytes());

            return imageRepo.save(image);

        } catch (IOException ex) {

            System.err.println(ex.getMessage());
           
        }
        
         return null;

    }

}
