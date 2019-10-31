package org.a3.mandarin.back.controller;

import org.a3.mandarin.back.exception.ApiNotFoundException;
import org.a3.mandarin.back.model.RESTfulResponse;
import org.a3.mandarin.common.annotation.Permission;
import org.a3.mandarin.common.dao.repository.CategoryRepository;
import org.a3.mandarin.common.entity.Category;
import org.a3.mandarin.common.enums.PermissionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/api")
public class CategoryController {

    @Resource
    private CategoryRepository categoryRepository;

    @GetMapping("/category")
    @ResponseBody
    @Transactional
    @Permission({PermissionType.LIBRARIAN, PermissionType.READER})
    public ResponseEntity<RESTfulResponse<List<Category>>> getCategories(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit){
        Page<Category> categories=categoryRepository.findAll(PageRequest.of(page, limit));
        RESTfulResponse<List<Category>> response=RESTfulResponse.ok();
        response.setData(categories.getContent());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/category")
    @ResponseBody
    @Transactional
    @Permission(PermissionType.LIBRARIAN)
    public ResponseEntity<RESTfulResponse> addCategory(@RequestParam String categoryName){
        Category category=new Category(categoryName);
        categoryRepository.save(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(RESTfulResponse.ok());
    }

    @DeleteMapping("/category/{id}")
    @ResponseBody
    @Transactional
    @Permission({ PermissionType.LIBRARIAN})
    public ResponseEntity<RESTfulResponse> updateCategory(@PathVariable("id") Integer categoryId){
        Category category=categoryRepository.findById(categoryId).orElse(null);

        if (null == category)
            throw new ApiNotFoundException("no such category");

        categoryRepository.delete(category);
        return ResponseEntity.ok(RESTfulResponse.ok());
    }

    @PutMapping("/category/{id}")
    @ResponseBody
    @Transactional
    @Permission({ PermissionType.LIBRARIAN})
    public ResponseEntity<RESTfulResponse> updateCategory(@PathVariable("id") Integer categoryId,
                                                          @RequestParam String categoryName){
        Category category=categoryRepository.findById(categoryId).orElse(null);

        if (null == category)
            throw new ApiNotFoundException("no such category");

        category.setCategoryName(categoryName);
        categoryRepository.save(category);
        return ResponseEntity.ok(RESTfulResponse.ok());
    }
}
