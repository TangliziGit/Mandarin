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
    private final NewsRepository newsRepository;

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
        this.newsRepository=applicationContext.getBean(NewsRepository.class);
    }

    void init(){
        generateInitialData();
        generateMockUsers();
        generateMockBooks();
        generateMockUserBookRelation();
        generateMockNews();
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
        Book book21=new Book();
        Book book22=new Book();
        Book book31=new Book();
        Book book32=new Book();

        BookDescription bookDescription1=new BookDescription("9780393351378", "The Science of Interstellar", "Kip Thorne", 24.95, "http://106.13.1.40:8081/img/s27824536.jpg",
                "Floor 2 - Shelf 1", 2014, "W. W. Norton & Company", "A journey through the otherworldly science behind Christopher Nolan’s highly anticipated film, Interstellar, from executive producer and theoretical physicist Kip Thorne.", category1);
        BookDescription bookDescription2=new BookDescription("9780132856201", "Computer Networking", "James F. Kurose / Keith W. Ross", 171.60, "http://106.13.1.40:8081/img/s24966915.jpg",
                "Floor 1 - Shelf 2", 2012, "Pearson", "Computer Networking continues with an early emphasis on application-layer paradigms and application programming interfaces (the top layer), encouraging a hands-on experience with protocols and networking concepts, before working down the protocol stack to more abstract layers.", category2);
        BookDescription bookDescription3=new BookDescription("9780134177298", "Core Java, Volume II (10th Edition) : Advanced Features", "Cay S. Horstmann", 59.99, "http://106.13.1.40:8081/img/s28069282.jpg",
                "Floor 2 - Shelf 3", 2016, "Prentice Hall", "Core Java® has long been recognized as the leading, no-nonsense tutorial and reference for experienced programmers who want to write robust Java code for real-world applications. Now, Core Java®, Volume II—Advanced Topics, Tenth Edition, has been extensively updated to reflect the most eagerly awaited and innovative version of Java in years: Java SE 8. Rewritten and reorganized...Core Java® has long been recognized as the leading, no-nonsense tutorial and reference for experienced programmers who want to write robust Java code for real-world applications. Now, Core Java®, Volume II—Advanced Topics, Tenth Edition, has been extensively updated to reflect the most eagerly awaited and innovative version of Java in years: Java SE 8. Rewritten and reorganized to illuminate powerful new Java features, idioms, and best practices for enterprise and desktop development, it contains hundreds of up-to-date example programs—all carefully crafted for easy understanding and practical applicability.Writing for serious programmers solving real-world problems, Cay Horstmann deepens your understanding of today’s Java language and library. In this second of two updated volumes, he offers in-depth coverage of expert-level topics including the new Streams API and date/time/calendar library, advanced Swing, security, code processing, and more. This guide will help youUse the new Streams library to process collections more flexibly and efficientlyEfficiently access files and directories, read/write binary or text data, and serialize objectsWork with Java SE 8’s regular expression packageMake the most of XML in Java: parsing, validation, XPath, document generation, XSL, and moreEfficiently connect Java programs to network servicesProgram databases with JDBC 4.2Elegantly overcome date/time programming complexities with the new java.time APIWrite internationalized programs with localized dates/times, numbers, text, and GUIsProcess code with the scripting API, compiler API, and annotation processorsEnforce security via class loaders, bytecode verification, security managers, permissions, user authentication, digital signatures, code signing, and encryptionMaster advanced Swing components for lists, tables, trees, text, and progress indicatorsProduce high-quality drawings with the Java 2D APIUse JNI native methods to leverage code in other languagesIf you’re an experienced programmer moving to Java SE 8, Core Java®, Tenth Edition, will be your reliable, practical companion—now and for many years to come.Look for the companion volume, Core Java®, Volume I—Fundamentals, Tenth Edition (ISBN-13: 978-0-13-417730-4), for foundational coverage of Java 8 language concepts, UI programming, objects, generics, collections, lambda expressions, concurrency, functional programming, and more.Cay S. Horstmann is author of Core Java ® for the Impatient (2015), Java SE 8 for the Really Impatient (2014), and Scala for the Impatient (2012), all from Addison-Wesley. He has written more than a dozen other books for professional programmers and computer science students. He is a professor of computer science at San Jose State University and is a Java Champion.", category2);
        BookDescription bookDescription4=new BookDescription("9780262035613", "Deep Learning : Adaptive Computation and Machine Learning series", "Ian Goodfellow / Yoshua Bengio / Aaron Courville", 72.00, "http://106.13.1.40:8081/img/s29133163.jpg",
                "Floor 2 - Shelf 4", 2016, "The MIT Press", "Written by three experts in the field, Deep Learning is the only comprehensive book on the subject.\\\" -- Elon Musk, co-chair of OpenAI; co-founder and CEO of Tesla and SpaceXDeep learning is a form of machine learning that enables computers to learn from experience and understand the world in terms of a hierarchy of concepts. Because the computer gathers knowledge from experie...\\\"Written by three experts in the field, Deep Learning is the only comprehensive book on the subject.\\\" -- Elon Musk, co-chair of OpenAI; co-founder and CEO of Tesla and SpaceXDeep learning is a form of machine learning that enables computers to learn from experience and understand the world in terms of a hierarchy of concepts. Because the computer gathers knowledge from experience, there is no need for a human computer operator to formally specify all the knowledge that the computer needs. The hierarchy of concepts allows the computer to learn complicated concepts by building them out of simpler ones; a graph of these hierarchies would be many layers deep. This book introduces a broad range of topics in deep learning.The text offers mathematical and conceptual background, covering relevant concepts in linear algebra, probability theory and information theory, numerical computation, and machine learning. It describes deep learning techniques used by practitioners in industry, including deep feedforward networks, regularization, optimization algorithms, convolutional networks, sequence modeling, and practical methodology; and it surveys such applications as natural language processing, speech recognition, computer vision, online recommendation systems, bioinformatics, and videogames. Finally, the book offers research perspectives, covering such theoretical topics as linear factor models, autoencoders, representation learning, structured probabilistic models, Monte Carlo methods, the partition function, approximate inference, and deep generative models.Deep Learning can be used by undergraduate or graduate students planning careers in either industry or research, and by software engineers who want to begin using deep learning in their products or platforms. A website offers supplementary material for both readers and instructors.Ian Goodfellow is Research Scientist at OpenAI. Yoshua Bengio is Professor of Computer Science at the Université de Montréal. Aaron Courville is Assistant Professor of Computer Science at the Université de Montréal.", category2);

        bookDescription1.getBooks().add(book11);
        bookDescription1.getBooks().add(book12);
        bookDescription2.getBooks().add(book2);
        bookDescription3.getBooks().add(book21);
        bookDescription3.getBooks().add(book22);
        bookDescription4.getBooks().add(book31);
        bookDescription4.getBooks().add(book32);

        book11.setBookDescription(bookDescription1);
        book12.setBookDescription(bookDescription1);
        book2.setBookDescription(bookDescription2);
        book21.setBookDescription(bookDescription3);
        book22.setBookDescription(bookDescription3);
        book31.setBookDescription(bookDescription4);
        book32.setBookDescription(bookDescription4);

        // bookDescription1.setCategory(category1);
        // bookDescription2.setCategory(category2);

        categoryRepository.saveAndFlush(category1);
        categoryRepository.saveAndFlush(category2);
        // bookDescriptionRepository.saveAndFlush(bookDescription1);
        // bookDescriptionRepository.saveAndFlush(bookDescription2);
        bookRepository.saveAndFlush(book11);
        bookRepository.saveAndFlush(book12);
        bookRepository.saveAndFlush(book2);
        bookRepository.saveAndFlush(book21);
        bookRepository.saveAndFlush(book22);
        bookRepository.saveAndFlush(book31);
        bookRepository.saveAndFlush(book32);
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

    private void generateMockNews(){
        User librarian = userRepository.findByName("librarian");
        News news1 = new News();
        News news2 = new News();

        news1.setTitle("Notice on holding digital textbook construction and publishing exchange meeting");
        news1.setContent("For the further implementation of the national education conference spirit, explore the deepening the reform of digital instructional innovation model, promote new media and the integration of traditional culture, with the development of teaching resources sharing needs, on the morning of November 1, and higher education press office jointly sponsored digital textbook construction and publishing communication meeting. ");
        news1.setDate(Instant.now());
        news1.setUser(librarian);

        news2.setTitle("\"China National Knowledge Infrastructure\" function and use method information session");
        news2.setContent("Scientific research and paper writing are inseparable from scientific and technological information retrieval and database resources. In order to facilitate teachers and students to effectively use various database resources to carry out their work and study, the graduate school specially invites \"cnknet\" technical personnel to introduce functions and services of various data resources and answer questions from teachers and students on site. This paper will focus on the application of the academic misconduct detection system of cnki and how to interpret the detection report. Welcome to join teachers and students.");
        news2.setDate(Instant.now());
        news2.setUser(librarian);

        newsRepository.save(news1);
        newsRepository.save(news2);
    }
}
