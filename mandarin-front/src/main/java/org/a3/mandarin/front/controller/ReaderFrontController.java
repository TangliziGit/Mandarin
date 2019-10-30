package org.a3.mandarin.front.controller;

import org.a3.mandarin.common.annotation.Permission;
import org.a3.mandarin.common.dao.repository.*;
import org.a3.mandarin.common.entity.*;
import org.a3.mandarin.common.enums.PermissionType;
import org.a3.mandarin.common.util.StringUtil;
import org.a3.mandarin.front.model.BookModel;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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

    @Resource
    private NewsRepository newsRepository;

    @GetMapping({"", "/", "/index", "/home"})
    public String index(Map<String, Object> map){
        List<News> newsList = newsRepository.findAll(
                PageRequest.of(0, 2, Sort.by("date").descending())).getContent();

        for (News news: newsList)
            news.setContent(StringUtil.escapeHtmlTag(news.getContent()));

        map.put("newsList", newsList);

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

    @GetMapping("/news")
    public String news(@RequestParam(value = "id", defaultValue = "1") Integer id,
                       Map<String, Object> map){
        News news = newsRepository.findById(id).orElse(null);
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                        .withLocale(Locale.UK)
                        .withZone(ZoneId.systemDefault());

        map.put("news", news);
        map.put("date", formatter.format(news.getDate()));

        return "reader/news";
    }

    @GetMapping("/newslist")
    public String newsList(Map<String, Object> map){
        List<News> newsList = newsRepository.findAll();

        map.put("newsList", newsList);

        return "reader/newslist";
    }
}
