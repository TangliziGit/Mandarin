package org.a3.mandarin.front.controller;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.a3.mandarin.common.annotation.Permission;
import org.a3.mandarin.common.dao.repository.NewsQueryRepository;
import org.a3.mandarin.common.dao.repository.NewsRepository;
import org.a3.mandarin.common.entity.News;
import org.a3.mandarin.common.entity.QNews;
import org.a3.mandarin.common.enums.PermissionType;
import org.a3.mandarin.common.util.StringUtil;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/librarian")
public class LibrarianManagementController {

    @Resource
    private NewsQueryRepository newsQueryRepository;

    @Resource
    private NewsRepository newsRepository;

    @GetMapping({"", "/", "/login"})
    public String login(){
        return "librarian/login";
    }

    @GetMapping({"/forgetPswd"})
    public String forgetPassword(){
        return "librarian/forgetPswd";
    }

    @GetMapping({"/postManage", "/postManage.html"})
    public String news(@RequestParam(value = "title", defaultValue = "") String title,
                       @RequestParam(value = "limit", defaultValue = "20") Integer limit,
                       @RequestParam(value = "page", defaultValue = "0") Integer page,
                       Map<String, Object> map){
        List<News> newsList;

        if (title.equals(""))
            newsList = newsRepository.findAll();
        else {
            QNews qNews = QNews.news;
            BooleanExpression expression = qNews.title.contains(title);

            newsList = newsQueryRepository.findAll(expression,
                    PageRequest.of(page, limit, Sort.by("date").descending())).getContent();
        }

        for (News news: newsList)
            news.setContent(StringUtil.escapeHtmlTag(news.getContent()));

        map.put("newsList", newsList);

        return "librarian/postManage";
    }

    @GetMapping("{path}")
    @Permission(PermissionType.LIBRARIAN)
    public String map(@PathVariable("path") String path){
        return "librarian/"+path;
    }
}
