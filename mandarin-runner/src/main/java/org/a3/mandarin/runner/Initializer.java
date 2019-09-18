package org.a3.mandarin.runner;

import org.a3.mandarin.common.aop.dao.repository.RoleRepository;
import org.a3.mandarin.common.aop.dao.repository.UserRepository;
import org.a3.mandarin.common.entity.Role;
import org.a3.mandarin.common.enums.RoleType;
import org.springframework.context.ConfigurableApplicationContext;

public class Initializer {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public Initializer(ConfigurableApplicationContext applicationContext){
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

    }
}
