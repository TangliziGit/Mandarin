package org.a3.mandarin.runner;

import org.a3.mandarin.common.dao.repository.*;
import org.a3.mandarin.common.entity.*;
import org.a3.mandarin.common.enums.RoleType;
import org.a3.mandarin.common.util.RoleUtil;
import org.a3.mandarin.common.util.SettingUtil;
import org.springframework.context.ApplicationContext;

import java.time.Instant;

class Initializer {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final BookDescriptionRepository bookDescriptionRepository;
    private final ReservationHistoryRepository reservationHistoryRepository;
    private final DeletingHistoryRepository deletingHistoryRepository;
    private final BorrowingHistoryRepository borrowingHistoryRepository;
    private final SettingRepository settingRepository;

    Initializer(ApplicationContext applicationContext){
        this.roleRepository=applicationContext.getBean(RoleRepository.class);
        this.userRepository=applicationContext.getBean(UserRepository.class);
        this.bookRepository=applicationContext.getBean(BookRepository.class);
        this.categoryRepository=applicationContext.getBean(CategoryRepository.class);
        this.bookDescriptionRepository=applicationContext.getBean(BookDescriptionRepository.class);
        this.reservationHistoryRepository=applicationContext.getBean(ReservationHistoryRepository.class);
        this.deletingHistoryRepository=applicationContext.getBean(DeletingHistoryRepository.class);
        this.borrowingHistoryRepository=applicationContext.getBean(BorrowingHistoryRepository.class);
        this.settingRepository=applicationContext.getBean(SettingRepository.class);
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

        settingRepository.save(new Setting(SettingUtil.FINE, 1.0));
        settingRepository.save(new Setting(SettingUtil.PERIOD, 30.0));
        settingRepository.save(new Setting(SettingUtil.DEPOSIT, 300.0));
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

        Book book11=new Book();
        Book book12=new Book();
        Book book2=new Book();

        BookDescription bookDescription1=new BookDescription("123456", "Book1", "Author1", 100,
                "Floor 2 - Shelf 1", 2000, "Publisher", "summary", category1);
        BookDescription bookDescription2=new BookDescription("223456", "Book2", "Author2", 120,
                "Floor 2 - Shelf 1", 2000, "Publisher", "summary", category2);

        bookDescription1.getBooks().add(book11);
        bookDescription1.getBooks().add(book12);
        bookDescription2.getBooks().add(book2);

        book11.setBookDescription(bookDescription1);
        book12.setBookDescription(bookDescription1);
        book2.setBookDescription(bookDescription2);

        // bookDescription1.setCategory(category1);
        // bookDescription2.setCategory(category2);

        categoryRepository.saveAndFlush(category1);
        categoryRepository.saveAndFlush(category2);
        // bookDescriptionRepository.saveAndFlush(bookDescription1);
        // bookDescriptionRepository.saveAndFlush(bookDescription2);
        bookRepository.saveAndFlush(book11);
        bookRepository.saveAndFlush(book12);
        bookRepository.saveAndFlush(book2);
    }

    private void generateMockUserBookRelation(){
        Book book=bookRepository.findById(1).orElse(null);
        User reader=userRepository.findByName("reader1");
        User librarian=userRepository.findByName("librarian");

        ReservingHistory reservingHistory=new ReservingHistory(book, reader, Instant.now());
        DeletingHistory deletingHistory=new DeletingHistory(book, librarian, Instant.now());
        BorrowingHistory borrowingHistory=new BorrowingHistory(book, reader, Instant.now());
        BorrowingFineHistory borrowingFineHistory=new BorrowingFineHistory(Instant.now());

        borrowingHistory.setBorrowingFineHistory(borrowingFineHistory);
        borrowingFineHistory.setBorrowingHistory(borrowingHistory);

        reservationHistoryRepository.save(reservingHistory);
        deletingHistoryRepository.save(deletingHistory);
        borrowingHistoryRepository.save(borrowingHistory);
    }
}
