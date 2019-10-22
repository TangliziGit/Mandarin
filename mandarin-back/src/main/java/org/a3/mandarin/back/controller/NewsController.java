package org.a3.mandarin.back.controller;

import org.a3.mandarin.back.exception.ApiNotFoundException;
import org.a3.mandarin.back.model.RESTfulResponse;
import org.a3.mandarin.common.annotation.Permission;
import org.a3.mandarin.common.dao.repository.NewsRepository;
import org.a3.mandarin.common.dao.repository.UserRepository;
import org.a3.mandarin.common.dto.IncomeSummary;
import org.a3.mandarin.common.entity.*;
import org.a3.mandarin.common.enums.PermissionType;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.List;

@Controller
@RequestMapping("/api")
public class NewsController {

    @Resource
    private NewsRepository newsRepository;

    @Resource
    private UserRepository userRepository;

    @PostMapping("/news/")
    @ResponseBody
    @Transactional
    @Permission(PermissionType.LIBRARIAN)
    public ResponseEntity<RESTfulResponse> addNews(@RequestParam String title,
                                                   @RequestParam String content,
                                                   HttpSession session ){

        Integer operatorUserId=(Integer) session.getAttribute("userId");
        User librarian = userRepository.findById(operatorUserId).orElse(null);//获取操作者

        News news= new News(title,content,librarian, Instant.now());
        newsRepository.save(news);

        return ResponseEntity.status(HttpStatus.CREATED).body(RESTfulResponse.ok());
    }


    @DeleteMapping("/news/{id}")
    @ResponseBody
    @Transactional
    @Permission(PermissionType.LIBRARIAN)
    public ResponseEntity<RESTfulResponse> deleteNews(@PathVariable("id") Integer targetNewsId){
       News targetNews = newsRepository.findById(targetNewsId).orElse(null);

        if (null == targetNews)
            throw new ApiNotFoundException("no such news");

        newsRepository.deleteById(targetNewsId);
        return ResponseEntity.status(HttpStatus.CREATED).body(RESTfulResponse.ok());
    }

    @PutMapping("/news/{id}")
    @ResponseBody
    @Transactional
    @Permission({PermissionType.LIBRARIAN})
    public ResponseEntity<RESTfulResponse> updateNews(@PathVariable("id") Integer targetNewsId ,
                                                      @RequestParam String title,
                                                      @RequestParam String content,
                                                      HttpSession session ){
       News news=newsRepository.findById(targetNewsId).orElse(null);

        Integer operatorUserId=(Integer) session.getAttribute("userId");
        User librarian = userRepository.findById(operatorUserId).orElse(null);//获取操作者

        if (null == news)
            throw new ApiNotFoundException("no such news");

        news.setContent(content);
        // news.setDate(Instant.now());
        news.setTitle(title);
        // news.setUser(librarian);

        newsRepository.save(news);
        return ResponseEntity.ok(RESTfulResponse.ok());

    }

    @GetMapping("/news")
    @ResponseBody
    @Transactional
    public ResponseEntity<RESTfulResponse<List<News>>> getNews(){

        List<News> newsList = newsRepository.findAll();
        RESTfulResponse<List<News>> response=RESTfulResponse.ok();
        response.setData(newsList);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/news/{id}")
    @ResponseBody
    @Transactional
    public ResponseEntity<RESTfulResponse<News>> getNewsById(@PathVariable("id") Integer targetNewsId ){
        News news= newsRepository.findById(targetNewsId).orElse(null);
        RESTfulResponse<News> response=RESTfulResponse.ok();
        response.setData(news);

        return ResponseEntity.ok(response);

    }
}
