/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.news.services;

import com.egg.news.entities.Journalist;
import com.egg.news.entities.News;
import com.egg.news.myexception.MyException;
import com.egg.news.repository.JournalistRepository;
import com.egg.news.repository.NewsRepository;
import java.util.ArrayList;
import java.util.Collection;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ayala gonzalo
 */
@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepo;

    @Autowired
    private JournalistRepository journalistRepo;

    @Transactional
    public void buildBook(String title) throws MyException {

        validateTitle(title);
        News news = new News();

        news.setTitle(title);
        news.setReleaseDate(new Date());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        Journalist journalist = journalistRepo.findByName(name);
        news.setJournalist(journalist);

        newsRepo.save(news);

    }

    @Transactional
    public void createBookByAdmin(String title) throws MyException {

        validateTitle(title);

        News news = new News();

        news.setTitle(title);
        news.setReleaseDate(new Date());

        newsRepo.save(news);

    }

    @Transactional
    public void modifyNews(String isbn, String title) throws MyException {

        Optional<News> answer = newsRepo.findById(isbn);

        validateTitle(title);

        if (answer.isPresent()) {

            News news = answer.get();

            news.setTitle(title);
        }

    }

    public void validateTitle(String title) throws MyException {

        List<News> list = listAllNews();

        Iterator<News> it = list.iterator();

        while (it.hasNext()) {

            if (it.next().getTitle().equals(title)) {

                throw new MyException("This title already exist");

            }

        }

    }

    @Transactional(readOnly = true)
    public News getOne(String isbn) {

        return newsRepo.getOne(isbn);

    }

    @Transactional(readOnly = true)
    public List<News> listAllNews() {

        List<News> list = newsRepo.findAll();

        return list;
    }

    @Transactional
    public void deleteNews(String isbn) throws MyException {

        News news = newsRepo.getById(isbn);

        newsRepo.delete(news);

    }

}
