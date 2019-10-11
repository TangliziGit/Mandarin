package org.a3.mandarin.back.controller;

import org.a3.mandarin.back.exception.ApiNotFoundException;
import org.a3.mandarin.back.model.RESTfulResponse;
import org.a3.mandarin.common.annotation.Permission;
import org.a3.mandarin.common.dao.repository.*;
import org.a3.mandarin.common.entity.*;
import org.a3.mandarin.common.enums.PermissionType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpSession;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class BookController {

    @Resource
    private BookRepository bookRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private BookDescriptionRepository bookDescriptionRepository;
    @Resource
    private DeletingHistoryRepository deletingHistoryRepository;
    @Resource
    private CategoryRepository categoryRepository;

    @PostMapping("/book")
    @ResponseBody
    @Transactional
    @Permission({PermissionType.LIBRARIAN})
    public ResponseEntity<RESTfulResponse<List<Integer>>> addBook(@RequestParam String ISBN,
                                                                  @RequestParam String title,
                                                                  @RequestParam String author,
                                                                  @RequestParam Double price,
                                                                  @RequestParam String location,
                                                                  @RequestParam String categoryName,
                                                                  @RequestParam Integer publishYear,
                                                                  @RequestParam String publisher,
                                                                  @RequestParam String summary,
                                                                  @RequestParam Integer copyNumber){
        BookDescription tmpBookDescription = bookDescriptionRepository.findById(ISBN).orElse(null);
        Category category=categoryRepository.findByCategoryNameEquals(categoryName);

        if (null == category)
            throw new ApiNotFoundException("no such category");

        if (null != tmpBookDescription)
            throw new ApiNotFoundException("such book exists");

        if (copyNumber <= 0)
            throw new ApiNotFoundException("please enter a correct copy number");

        BookDescription bookDescription = new BookDescription(ISBN, title,  author, price,
                location, publishYear, publisher, summary, category);
        List<Integer> bookIdList=new ArrayList<>();

        for (int count=0; count<copyNumber; count++) {
            Book book=new Book(bookDescription);
            bookDescription.getBooks().add(book);
            bookRepository.saveAndFlush(book);
            bookIdList.add(book.getBookId());
        }
        bookDescriptionRepository.save(bookDescription);

        RESTfulResponse<List<Integer>> response=RESTfulResponse.ok();
        response.setData(bookIdList);
        System.out.println(bookIdList);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/book/{id}")
    @ResponseBody
    @Transactional
    @Permission({PermissionType.LIBRARIAN})
    public ResponseEntity<RESTfulResponse> updateBook(@PathVariable("id") Integer bookId,
                                                      @RequestParam String title,
                                                      @RequestParam String author,
                                                      @RequestParam Double price,
                                                      @RequestParam String location,
                                                      @RequestParam String categoryName,
                                                      @RequestParam Integer publishYear,
                                                      @RequestParam String publisher,
                                                      @RequestParam String summary){
        Book book=bookRepository.findById(bookId).orElse(null);
        Category category=categoryRepository.findByCategoryNameEquals(categoryName);

        if (null == category)
            throw new ApiNotFoundException("no such category");

        if (null == book)
            throw new ApiNotFoundException("no such book");

        BookDescription bookDescription = book.getBookDescription();

        bookDescription.setTitle(title);
        bookDescription.setAuthor(author);
        bookDescription.setPrice(price);
        bookDescription.setLocation(location);
        bookDescription.setCategory(category);
        bookDescription.setPublisher(publisher);
        bookDescription.setPublishYear(publishYear);
        bookDescription.setSummary(summary);

        bookDescriptionRepository.save(bookDescription);
        return ResponseEntity.ok(RESTfulResponse.ok());

    }

    @DeleteMapping("/book/{id}")
    @ResponseBody
    @Transactional
    @Permission({PermissionType.LIBRARIAN})
    public ResponseEntity<RESTfulResponse> deleteBook(@PathVariable("id") Integer targetBookId,
                                                      HttpSession session){
        Book targetBook=bookRepository.findById(targetBookId).orElse(null);

        Integer operatorUserId=(Integer) session.getAttribute("userId");
        User librarian = userRepository.findById(operatorUserId).orElse(null);

        if (null != deletingHistoryRepository.findByBook_BookId(targetBookId))
            throw new ApiNotFoundException("such book have been deleted");

        DeletingHistory deletingHistory = new DeletingHistory(targetBook, librarian, Instant.now());
        deletingHistoryRepository.save(deletingHistory);

        return ResponseEntity.ok(RESTfulResponse.ok());
    }


}

