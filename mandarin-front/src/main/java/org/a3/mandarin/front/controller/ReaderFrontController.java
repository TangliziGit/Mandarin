package org.a3.mandarin.front.controller;

import org.a3.mandarin.common.annotation.Permission;
import org.a3.mandarin.common.dao.repository.*;
import org.a3.mandarin.common.entity.*;
import org.a3.mandarin.common.enums.PermissionType;
import org.a3.mandarin.front.model.BookModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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
    @Resource
    private UserRepository userRepository;
    @Resource
    private BorrowingFineHistoryRepository borrowingFineHistoryRepository;

    @GetMapping({"", "/", "/index", "/home"})
    public String index(){
        return "reader/index";
    }

    @GetMapping("/login")
    public String login(){
        return "reader/login";
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "q", defaultValue = "") String query,
                         Map<String, Object> map){
        map.put("query", query);

        return "reader/search";
    }

    @GetMapping("/account")
    @Permission(PermissionType.READER)
    public String account(Map<String, Object> map, HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");
        User reader = userRepository.findById(userId).orElse(null);
        Integer fine = borrowingFineHistoryRepository.findTotalFineAmountByUserId(userId);

        List<BorrowingHistory> borrowingHistories = reader.getBorrowingHistories();
        List<ReservingHistory> reservingHistories = reader.getReservingHistories();

        System.out.println(reader);
        System.out.println(borrowingHistories);
        System.out.println(reservingHistories);

        map.put("reader", reader);
        map.put("fine", fine);
        map.put("borrowingHistories", borrowingHistories);
        map.put("reservingHistories", reservingHistories);

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

    @GetMapping("/retrieve")
    public String retrievePassword(){
        return "reader/retrieve";
    }
}
