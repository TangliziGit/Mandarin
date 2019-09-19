package org.a3.mandarin.runner;

import org.a3.mandarin.common.aop.dao.repository.RoleRepository;
import org.a3.mandarin.common.aop.dao.repository.UserRepository;
import org.a3.mandarin.common.entity.Role;
import org.a3.mandarin.common.entity.User;
import org.a3.mandarin.common.enums.RoleType;
import org.a3.mandarin.common.util.UserUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.Instant;

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
    }

    private void generateMockData(){
        User librarian=new User("librarian", "1234", "1234@1234.com", Instant.now(), "passwd");
        userRepository.saveAndFlush(librarian);
        UserUtil.setRoleByUserId(librarian.getUserId(), RoleType.LIBRARIAN);

        User admin=new User("admin", "2234", "2234@2234.com", Instant.now(), "passwd");
        userRepository.saveAndFlush(admin);
        UserUtil.setRoleByUserId(admin.getUserId(), RoleType.ADMIN);

        User reader1=new User("reader1", "3234", "3234@2234.com", Instant.now(), "passwd");
        userRepository.saveAndFlush(reader1);
        UserUtil.setRoleByUserId(reader1.getUserId(), RoleType.READER);

        User reader2=new User("reader2", "4234", "4234@2234.com", Instant.now(), "passwd");
        userRepository.saveAndFlush(reader2);
        UserUtil.setRoleByUserId(reader2.getUserId(), RoleType.READER);
    }
}
