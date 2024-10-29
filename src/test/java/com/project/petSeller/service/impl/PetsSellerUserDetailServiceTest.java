package com.project.petSeller.service.impl;

import com.project.petSeller.model.entity.UserEntity;
import com.project.petSeller.model.entity.UserRoleEntity;
import com.project.petSeller.model.enums.UserRoleEnum;
import com.project.petSeller.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PetsSellerUserDetailServiceTest {


    private PetsSellerUserDetailService serviceToTest;
    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setup() {

        serviceToTest = new PetsSellerUserDetailService(
                mockUserRepository
        );

    }

    @Test
    void testUserNotFound() {


       assertThrows(UsernameNotFoundException.class,
                () -> serviceToTest.loadUserByUsername("hachi@kuchev.com"));


    }

    @Test
    void testUserFoundException() {

        // Arrange
        UserEntity testUserEntity = createTestUser();

        when(mockUserRepository.findByEmail(testUserEntity.getEmail()))
                .thenReturn(Optional.of(testUserEntity));

        // Act
       UserDetails userDetails = serviceToTest.loadUserByUsername(testUserEntity.getEmail());

       // Assert
        assertNotNull(userDetails);
        assertEquals(
                testUserEntity.getEmail(),
                userDetails.getUsername(),
                "Username i not mapped to email.");

        assertEquals(testUserEntity.getPassword(), userDetails.getPassword());
        assertEquals(2, userDetails.getAuthorities().size());
        assertTrue(
                containsAuthority(userDetails, "ROLE_" + UserRoleEnum.ADMIN),
                "The user is not admin");
        assertTrue(
                containsAuthority(userDetails, "ROLE_" + UserRoleEnum.USER),
                "The user is not user");

    }

    private boolean containsAuthority(UserDetails userDetails, String expectedAuthority) {
        return userDetails
                .getAuthorities()
                .stream()
                .anyMatch(a -> expectedAuthority.equals(a.getAuthority()));
    }

    private static UserEntity createTestUser() {
        return new UserEntity()
                .setFirstName("firstName")
                .setLastName("lastName")
                .setEmail("pesho@softuni.bg")
                .setActive(false)
                .setPassword("topsecret")
                .setRoles(List.of(
                        new UserRoleEntity().setRole(UserRoleEnum.ADMIN),
                        new UserRoleEntity().setRole(UserRoleEnum.USER)
                ));
    }
}
