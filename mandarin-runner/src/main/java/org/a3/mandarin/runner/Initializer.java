package org.a3.mandarin.runner;

import org.a3.mandarin.common.dao.repository.*;
import org.a3.mandarin.common.entity.*;
import org.a3.mandarin.common.enums.RoleType;
import org.a3.mandarin.common.util.RoleUtil;
import org.springframework.context.ApplicationContext;

import java.time.Instant;

class Initializer {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BookRepository bookRepository;
    private CategoryRepository categoryRepository;
    private BookDescriptionRepository bookDescriptionRepository;
    private ReservationHistoryRepository reservationHistoryRepository;
    private DeletingHistoryRepository deletingHistoryRepository;
    private BorrowingHistoryRepository borrowingHistoryRepository;

    Initializer(ApplicationContext applicationContext){
        this.roleRepository=applicationContext.getBean(RoleRepository.class);
        this.userRepository=applicationContext.getBean(UserRepository.class);
        this.bookRepository=applicationContext.getBean(BookRepository.class);
        this.categoryRepository=applicationContext.getBean(CategoryRepository.class);
        this.bookDescriptionRepository=applicationContext.getBean(BookDescriptionRepository.class);
        this.reservationHistoryRepository=applicationContext.getBean(ReservationHistoryRepository.class);
        this.deletingHistoryRepository=applicationContext.getBean(DeletingHistoryRepository.class);
        this.borrowingHistoryRepository=applicationContext.getBean(BorrowingHistoryRepository.class);
    }

    void init(){
        generateInitialData();
        generateMockUsers();
        generateMockBooks();
        generateMockUserBookRelation();
    }

    private void generateInitialData(){
        roleRepository.save(new Role(RoleType.READER.toString()));
        roleRepository.save(new Role(RoleType.LIBRARIAN.toString()));
        roleRepository.save(new Role(RoleType.ADMIN.toString()));
        RoleUtil.initRoles();
    }

    private void generateMockUsers(){
        User librarian=new User("librarian", "1234", "1234@1234.com", Instant.now(), "passwd");
        librarian.getRoles().add(RoleUtil.librarianRole);
        userRepository.save(librarian);

        User admin=new User("admin", "2234", "2234@2234.com", Instant.now(), "passwd");
        admin.getRoles().add(RoleUtil.adminRole);
        userRepository.save(admin);

        User reader1=new User("reader1", "3234", "3234@2234.com", Instant.now(), "passwd");
        reader1.getRoles().add(RoleUtil.readerRole);
        userRepository.save(reader1);

        User reader2=new User("reader2", "4234", "4234@2234.com", Instant.now(), "passwd");
        reader2.getRoles().add(RoleUtil.readerRole);
        userRepository.save(reader2);
    }

    private void generateMockBooks(){
        Category category1=new Category("category1");
        Category category2=new Category("category2");

        Book book11=new Book("123456");
        Book book12=new Book("123456");
        Book book2=new Book("223456");

        BookDescription bookDescription1=new BookDescription("123456", "Book1", "Author1", 100, "F4-393-1510");
        BookDescription bookDescription2=new BookDescription("223456", "Book2", "Author2", 120, "F4-393-1511");

        bookDescription1.getBooks().add(book11);
        bookDescription1.getBooks().add(book12);
        bookDescription2.getBooks().add(book2);

        bookDescription1.setCategory(category1);
        bookDescription2.setCategory(category2);

        categoryRepository.saveAndFlush(category1);
        categoryRepository.saveAndFlush(category2);
        bookRepository.saveAndFlush(book11);
        bookRepository.saveAndFlush(book12);
        bookRepository.saveAndFlush(book2);
        bookDescriptionRepository.saveAndFlush(bookDescription1);
        bookDescriptionRepository.saveAndFlush(bookDescription2);
    }

    private void generateMockUserBookRelation(){
        Book book=bookRepository.findById(1).orElse(null);
        User reader=userRepository.findByName("reader1");
        User librarian=userRepository.findByName("librarian");

        ReservingHistory reservingHistory=new ReservingHistory(book, reader, Instant.now());
        DeletingHistory deletingHistory=new DeletingHistory(book, librarian, Instant.now());
        BorrowingHistory borrowingHistory=new BorrowingHistory(book, reader, Instant.now());
        BorrowingFineHistory borrowingFineHistory=new BorrowingFineHistory(100, Instant.now());

        borrowingHistory.setBorrowingFineHistory(borrowingFineHistory);

        reservationHistoryRepository.save(reservingHistory);
        deletingHistoryRepository.save(deletingHistory);
        borrowingHistoryRepository.save(borrowingHistory);
    }
}
