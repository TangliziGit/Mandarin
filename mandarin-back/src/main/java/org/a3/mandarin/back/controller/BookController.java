package org.a3.mandarin.back.controller;

import org.a3.mandarin.back.exception.ApiNotFoundException;
import org.a3.mandarin.back.model.RESTfulResponse;
import org.a3.mandarin.common.annotation.Permission;
import org.a3.mandarin.common.dao.repository.*;
import org.a3.mandarin.common.entity.*;
import org.a3.mandarin.common.enums.PermissionType;
import org.a3.mandarin.common.util.RoleUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpSession;

import javax.annotation.Resource;
import java.time.Duration;
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
    @Resource
    private BorrowingHistoryRepository borrowingHistoryRepository;
    @Resource
    private ReservationHistoryRepository reservationHistoryRepository;

    @PostMapping("/book")
    @ResponseBody
    @Transactional
    @Permission({PermissionType.LIBRARIAN})
    public ResponseEntity<RESTfulResponse<List<Integer>>> addBook(@RequestParam String ISBN,
                                                                  @RequestParam String title,
                                                                  @RequestParam String author,
                                                                  @RequestParam Double price,
                                                                  @RequestParam String coverUrl,
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

        BookDescription bookDescription = new BookDescription(ISBN, title, author, price, coverUrl,
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

    @PostMapping("/book/borrow/{id}")
    @ResponseBody
    @Transactional
    @Permission({PermissionType.LIBRARIAN})
    public ResponseEntity<RESTfulResponse> borrowBook(@PathVariable("id") Integer targetBookId,
                                                      @RequestParam Integer targetReaderId){
        Book targetBook=bookRepository.findById(targetBookId).orElse(null);
        User user = userRepository.findById(targetReaderId).orElse(null);

        if (null == user)
            throw new ApiNotFoundException("no such user");
        if (!user.getRoles().contains(RoleUtil.readerRole))
            throw new ApiNotFoundException("only reader can borrow books");
        if (user.getBorrowingHistories().size()>=3)
            throw new ApiNotFoundException("the number of this reader's books has reached the limit of 3 book");

        if (null == targetBook)
            throw new ApiNotFoundException("no such book");
        if (bookRepository.isOnBorrowing(targetBookId))
            throw new ApiNotFoundException("this book is on borrowing");
        if (bookRepository.isDeleted(targetBookId))
            throw new ApiNotFoundException("this book is deleted");
        if (bookRepository.isOnReserving(targetBookId)){
            List<ReservingHistory> reservingHistories = targetBook.getReservingHistories();
            ReservingHistory reservingHistory = reservingHistories.get(reservingHistories.size() - 1);

            if (!reservingHistory.getReader().equals(user))
                throw new ApiNotFoundException("this book is reserved by other reader");
            else {
                // TODO: should be handled in task schedule
                reservingHistory.setFetched(true);
                reservingHistory.setReservingEndTime(Instant.now());
            }
        }

        BorrowingHistory borrowingHistory = new BorrowingHistory(targetBook, user, Instant.now());
        borrowingHistoryRepository.save(borrowingHistory);

        return ResponseEntity.ok(RESTfulResponse.ok());
    }

    @PostMapping("/book/return/{id}")
    @ResponseBody
    @Transactional
    @Permission({PermissionType.LIBRARIAN})
    public ResponseEntity<RESTfulResponse> returnBook(@PathVariable("id") Integer targetBookId){
        Book targetBook=bookRepository.findById(targetBookId).orElse(null);
        if (null == targetBook)
            throw new ApiNotFoundException("no such book");
        if (bookRepository.isDeleted(targetBookId))
            throw new ApiNotFoundException("It seems you take this book back? " +
                    "It's too late, we have deleted this book already. " +
                    "You can take this book back. " +
                    "Because our sponsor did not mention this condition.");

        if (bookRepository.isOnReserving(targetBookId))
            throw new ApiNotFoundException("this book is on reserving, but not borrowed");

        if (!bookRepository.isOnBorrowing(targetBookId))
            throw new ApiNotFoundException("this book has not been borrowed");

        List<BorrowingHistory> borrowingHistories = targetBook.getBorrowingHistories();
        BorrowingHistory borrowingHistory = borrowingHistories.get(borrowingHistories.size()-1);
        borrowingHistory.setBorrowingEndTime(Instant.now());

        bookRepository.save(targetBook);

        return ResponseEntity.ok(RESTfulResponse.ok());
    }


    @PostMapping("/book/reserve/{id}")
    @ResponseBody
    @Transactional
    @Permission({PermissionType.READER})
    public ResponseEntity<RESTfulResponse> reserveBook(@PathVariable("id") Integer targetBookId,
                                                       HttpSession session){
        Book targetBook=bookRepository.findById(targetBookId).orElse(null);
        if (null == targetBook )
            throw new ApiNotFoundException("no such book");

        Integer readerId = (Integer) session.getAttribute("userId");
        User reader = userRepository.findById(readerId).orElse(null);
        if (null == reader)
            throw new ApiNotFoundException("no such reader");

        if (bookRepository.isOnReserving(targetBookId))
            throw new ApiNotFoundException("this book is on reserving");
        if (bookRepository.isOnBorrowing(targetBookId))
            throw new ApiNotFoundException("this book is on borrowing");
        if (bookRepository.isDeleted(targetBookId))
            throw new ApiNotFoundException("this book has been deleted");

        // this code is useless, because book is already reserved
        // if (bookRepository.isOnReserving(targetBookId)){
        //     List<ReservingHistory> reservingHistories = targetBook.getReservingHistories();
        //     ReservingHistory lastReservingHistory  = reservingHistories.get(reservingHistories.size() - 1);
        //     Instant limitation = lastReservingHistory.getReservingStartTime().plus(Duration.ofHours(2));

        //     // maintain book reserving history
        //     if (Instant.now().isAfter(limitation)){
        //         // reservation failed
        //         lastReservingHistory.setFetched(false);
        //         lastReservingHistory.setReservingEndTime(limitation);
        //     }else{
        //         // on reserving
        //         throw new ApiNotFoundException("this book in on reserving");
        //     }
        // }

        ReservingHistory reservingHistory = new ReservingHistory(targetBook, reader, Instant.now());
        reservationHistoryRepository.save(reservingHistory);

        return ResponseEntity.ok(RESTfulResponse.ok());
    }
}

