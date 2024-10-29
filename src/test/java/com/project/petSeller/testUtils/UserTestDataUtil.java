package com.project.petSeller.testUtils;

import com.project.petSeller.model.entity.UserEntity;
import com.project.petSeller.model.enums.UserRoleEnum;
import com.project.petSeller.repository.UserRepository;
import com.project.petSeller.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserTestDataUtil {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;


    public UserEntity createTestUser(String email) {

        return createUser(email, List.of(UserRoleEnum.USER));


    }


    public UserEntity createTestAdmin(String email) {
        return createUser(email, List.of(UserRoleEnum.ADMIN));

    }

    private UserEntity createUser(String email, List<UserRoleEnum> roles) {

        var roleEntities = userRoleRepository.findAllByRoleIn(roles);

        UserEntity newUser = new UserEntity()
                .setActive(true)
                .setEmail(email)
                .setFirstName("Test user first")
                .setLastName("Test user last")
                .setRoles(
                        roleEntities
                );

        return userRepository.save(newUser);

    }
    public void cleanUp() {
        userRepository.deleteAll();

    }
}
