package org.a3.mandarin.back.controller;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.a3.mandarin.back.exception.ApiForbiddenException;
import org.a3.mandarin.back.model.BookModel;
import org.a3.mandarin.back.model.RESTfulResponse;
import org.a3.mandarin.common.annotation.Permission;
import org.a3.mandarin.common.dao.repository.BookQueryRepository;
import org.a3.mandarin.common.dao.repository.UserQueryRepository;
import org.a3.mandarin.common.entity.*;
import org.a3.mandarin.common.enums.PermissionType;
import org.a3.mandarin.common.util.RoleUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class SearchController {

    @Resource
    private UserQueryRepository userQueryRepository;

    @Resource
    private BookQueryRepository bookQueryRepository;

    @PostMapping("/search/reader")
    @ResponseBody
    @Transactional
    @Permission(PermissionType.LIBRARIAN)
    public ResponseEntity<RESTfulResponse<List<User>>> searchReader(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit,
            @RequestParam(value = "userId", required = false) Integer userId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(value = "email", required = false) String email){
        if (null == userId && null == name && null == phoneNumber && null == email)
            throw new ApiForbiddenException("please enter any parameters");

        PageRequest pageRequest=PageRequest.of(page, limit, Sort.by("signUpTime"));

        List<User> readers=findUserByInformationWithPageRequest(RoleUtil.readerRole, userId, name, phoneNumber, email, pageRequest);
        RESTfulResponse<List<User>> response=RESTfulResponse.ok();
        response.setData(readers);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/search/librarian")
    @ResponseBody
    @Transactional
    @Permission(PermissionType.ADMIN)
    public ResponseEntity<RESTfulResponse<List<User>>> searchLibrarian(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit,
            @RequestParam(value = "userId", required = false) Integer userId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(value = "email", required = false) String email){
        if (null == userId && null == name && null == phoneNumber && null == email)
            throw new ApiForbiddenException("please enter any parameters");

        PageRequest pageRequest=PageRequest.of(page, limit, Sort.by("signUpTime"));

        List<User> librarians=findUserByInformationWithPageRequest(RoleUtil.librarianRole, userId, name, phoneNumber, email, pageRequest);
        RESTfulResponse<List<User>> response=RESTfulResponse.ok();
        response.setData(librarians);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/search/book")
    @ResponseBody
    @Transactional
    @Permission({PermissionType.LIBRARIAN, PermissionType.READER})
    public ResponseEntity<RESTfulResponse<List<BookModel>>> searchBook(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit,
            @RequestParam(value = "isbn", required = false) String isbn,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "bookId", required = false) Integer bookId,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "category", required = false) String categoryName){
        QBook qBook= QBook.book;
        PageRequest pageRequest=PageRequest.of(page, limit, Sort.by("bookId"));
        BooleanExpression expression=qBook.bookId.isNotNull();

        if (null != bookId) expression=expression.and(qBook.bookId.eq(bookId));
        if (null != isbn) expression=expression.and(qBook.bookDescription.ISBN.eq(isbn));
        if (null != title) expression=expression.and(qBook.bookDescription.title.eq(title));
        if (null != author) expression=expression.and(qBook.bookDescription.author.eq(author));
        if (null != categoryName) expression=expression.and(qBook.bookDescription.category.categoryName.eq(categoryName));

        Page<Book> bookPage=bookQueryRepository.findAll(expression, pageRequest);
        List<BookModel> bookModels=new ArrayList<>();

        for (Book book: bookPage.getContent())
            bookModels.add(new BookModel(book));

        RESTfulResponse<List<BookModel>> response=RESTfulResponse.ok();
        response.setData(bookModels);

        return ResponseEntity.ok(response);
    }

    private List<User> findUserByInformationWithPageRequest(
            Role role,
            Integer userId,
            String name,
            String phoneNumber,
            String email,
            PageRequest pageRequest){
        QUser qUser=QUser.user;
        BooleanExpression expression=qUser.roles.contains(role);

        if (null != name) expression=expression.and(qUser.name.contains(name));
        if (null != email) expression=expression.and(qUser.email.eq(email));
        if (null != userId) expression=expression.and(qUser.userId.eq(userId));
        if (null != phoneNumber) expression=expression.and(qUser.phoneNumber.eq(phoneNumber));

        Page<User> userPage=userQueryRepository.findAll(expression, pageRequest);
        return userPage.getContent();
    }
}
