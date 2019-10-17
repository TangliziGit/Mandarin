package org.a3.mandarin.front.controller;

import org.a3.mandarin.common.annotation.Permission;
import org.a3.mandarin.common.dao.repository.BookDescriptionRepository;
import org.a3.mandarin.common.dao.repository.BookRepository;
import org.a3.mandarin.common.entity.Book;
import org.a3.mandarin.common.entity.BookDescription;
import org.a3.mandarin.common.enums.PermissionType;
import org.a3.mandarin.front.model.BookModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/reader")
public class ReaderFrontController {
    @Resource
    private BookDescriptionRepository bookDescriptionRepository;
    @Resource
    private BookRepository bookRepository;

    @GetMapping("{path}")
    public String map(@PathVariable("path") String path){
        return "reader/"+path;
    }

    @GetMapping("/account")
    @Permission(PermissionType.READER)
    public String account(){
        return "reader/account";
    }

    @GetMapping("/book/{isbn}")
    public String searchDetail(@PathVariable String isbn, Map<String, Object> map){
        BookDescription bookDescription = bookDescriptionRepository.findByISBN(isbn);
        Integer copyNumber = bookRepository.findByBookDescription_ISBN(isbn).size();

        System.out.println(bookDescription);
        if (bookDescription == null)
            return "reader/404";

        map.put("book", bookDescription);
        map.put("copyNumber", copyNumber);

        List<BookModel> bookModels = new ArrayList<>();
        for (Book copy: bookDescription.getBooks())
            bookModels.add(new BookModel(copy,
                    bookRepository.isDeleted(copy.getBookId()),
                    bookRepository.isOnBorrowing(copy.getBookId()),
                    bookRepository.isOnReserving(copy.getBookId())
            ));
        map.put("bookModels", bookModels);

        for (BookModel bookModel: bookModels)
            System.out.println(bookModel);

        return "reader/book";
    }
}
