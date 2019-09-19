package org.a3.mandarin.runner;

import org.a3.mandarin.common.aop.dao.repository.RoleRepository;
import org.a3.mandarin.common.aop.dao.repository.UserRepository;
import org.a3.mandarin.common.entity.Role;
import org.a3.mandarin.common.entity.User;
import org.a3.mandarin.common.enums.RoleType;
import org.a3.mandarin.common.util.RoleUtil;
import org.springframework.context.ApplicationContext;

import java.time.Instant;
import java.util.*;

public class Initializer {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public Initializer(ApplicationContext applicationContext){
        this.roleRepository=applicationContext.getBean(RoleRepository.class);
        this.userRepository=applicationContext.getBean(UserRepository.class);
    }

    public void init(){
        generateInitialData();
        generateMockData();
    }

    private void generateInitialData(){
        roleRepository.save(new Role(RoleType.READER.toString()));
        roleRepository.save(new Role(RoleType.LIBRARIAN.toString()));
        roleRepository.save(new Role(RoleType.ADMIN.toString()));
        RoleUtil.initRoles();
    }

    private void generateMockData(){
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
}
