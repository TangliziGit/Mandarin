package org.a3.mandarin.runner;

import org.a3.mandarin.common.dao.repository.*;
import org.a3.mandarin.common.entity.*;
import org.a3.mandarin.common.enums.IncomeType;
import org.a3.mandarin.common.enums.RoleType;
import org.a3.mandarin.common.util.RoleUtil;
import org.a3.mandarin.common.util.SettingUtil;
import org.springframework.context.ApplicationContext;

import java.time.Duration;
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
    private final IncomeRepository incomeRepository;

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
        this.incomeRepository=applicationContext.getBean(IncomeRepository.class);
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
        settingRepository.save(new Setting(SettingUtil.BOOK_RETURN_PERIOD, 30.0));
        settingRepository.save(new Setting(SettingUtil.DEPOSIT, 300.0));
    }

    private void generateMockUsers(){
        User librarian=new User("librarian", "18681941718", "librarian@mandarin.com", Instant.now(), "passwd");
        librarian.getRoles().add(RoleUtil.librarianRole);
        userRepository.save(librarian);

        User admin=new User("admin", "18681941717", "admin@mandarin.com", Instant.now(), "passwd");
        admin.getRoles().add(RoleUtil.adminRole);
        userRepository.save(admin);

        User reader1=new User("reader1", "18681941716", "reader1@mandarin.com", Instant.now(), "passwd");
        reader1.getRoles().add(RoleUtil.readerRole);
        userRepository.save(reader1);
        incomeRepository.save(new Income(IncomeType.DEPOSIT.name(), settingRepository.findByName(SettingUtil.DEPOSIT).getValue(), reader1, Instant.now()));

        User reader2=new User("reader2", "18681941715", "reader2@mandarin.com", Instant.now(), "passwd");
        reader2.getRoles().add(RoleUtil.readerRole);
        userRepository.save(reader2);
        incomeRepository.save(new Income(IncomeType.DEPOSIT.name(), settingRepository.findByName(SettingUtil.DEPOSIT).getValue(), reader2, Instant.now()));
    }

    private void generateMockBooks(){
        Category category1=new Category("science");
        Category category2=new Category("technology");

        Book book11=new Book();
        Book book12=new Book();
        Book book2=new Book();

        BookDescription bookDescription1=new BookDescription("9780393351378", "The Science of Interstellar", "Kip Thorne", 24.95,
                "Floor 2 - Shelf 1", 2014, "W. W. Norton & Company", "A journey through the otherworldly science behind Christopher Nolan’s highly anticipated film, Interstellar, from executive producer and theoretical physicist Kip Thorne.", category1);
        BookDescription bookDescription2=new BookDescription("9780132856201", "Computer Networking", "James F. Kurose / Keith W. Ross", 171.60,
                "Floor 2 - Shelf 1", 2012, "Pearson", "Computer Networking continues with an early emphasis on application-layer paradigms and application programming interfaces (the top layer), encouraging a hands-on experience with protocols and networking concepts, before working down the protocol stack to more abstract layers.", category2);

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
        Book book1=bookRepository.findById(1).orElse(null);
        Book book2=bookRepository.findById(2).orElse(null);
        User reader=userRepository.findByName("reader1");
        User librarian=userRepository.findByName("librarian");

        Instant tenDayBefore=Instant.now().minus(Duration.ofDays(10));
        ReservingHistory reservingHistory=new ReservingHistory(book1, reader, tenDayBefore);
        DeletingHistory deletingHistory=new DeletingHistory(book1, librarian, tenDayBefore);
        BorrowingHistory borrowingHistory1=new BorrowingHistory(book1, reader, tenDayBefore);
        BorrowingHistory borrowingHistory2=new BorrowingHistory(book2, reader, tenDayBefore);
        BorrowingFineHistory borrowingFineHistory1=new BorrowingFineHistory(tenDayBefore);
        BorrowingFineHistory borrowingFineHistory2=new BorrowingFineHistory(tenDayBefore);

        borrowingHistory1.setBorrowingFineHistory(borrowingFineHistory1);
        borrowingFineHistory1.setBorrowingHistory(borrowingHistory1);
        borrowingHistory2.setBorrowingFineHistory(borrowingFineHistory2);
        borrowingFineHistory2.setBorrowingHistory(borrowingHistory2);

        reservationHistoryRepository.save(reservingHistory);
        deletingHistoryRepository.save(deletingHistory);
        borrowingHistoryRepository.save(borrowingHistory1);
        borrowingHistoryRepository.save(borrowingHistory2);
    }
}
