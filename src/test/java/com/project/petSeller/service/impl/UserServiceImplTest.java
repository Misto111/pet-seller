package com.project.petSeller.service.impl;

import com.project.petSeller.model.dto.UserRegistrationDTO;
import com.project.petSeller.model.entity.UserEntity;
import com.project.petSeller.model.events.UserRegisteredEvent;
import com.project.petSeller.repository.UserRepository;
import com.project.petSeller.service.exception.InvalidPasswordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    private UserServiceImpl serviceToTest;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private PasswordEncoder mockPasswordEncoder;

    @Mock
    private ApplicationEventPublisher mockEventPublisher;

    @Mock
    private UserDetailsService mockUserDetailsService;

    @Captor
    private ArgumentCaptor<UserRegisteredEvent> eventCaptor;

    @BeforeEach
    void setup() {
        // Инициализация на класа за тестове с mock обекти
        serviceToTest = new UserServiceImpl(
                mockUserRepository,
                mockPasswordEncoder,
                mockEventPublisher,
                mockUserDetailsService
        );
    }

    @Test
    void testRegisterUserValidPasswordSuccess() {
        // Тест за успешна регистрация на потребител с валидни данни

        // Arrange: Подготовка на входни данни и mock поведение
        UserRegistrationDTO registrationDTO = new UserRegistrationDTO(
                "John",
                "Doe",
                "john.doe@example.com",
                "password123",
                "password123");

        when(mockPasswordEncoder.encode(registrationDTO.password()))
                .thenReturn("encodedPassword");

        // Act: Извикване на метода registerUser
        serviceToTest.registerUser(registrationDTO);

        // Assert: Проверка, че методите са извикани коректно
        verify(mockUserRepository).save(any(UserEntity.class));
        verify(mockEventPublisher).publishEvent(eventCaptor.capture());

        // Проверка на публикуваното събитие
        UserRegisteredEvent capturedEvent = eventCaptor.getValue();
        assertEquals("john.doe@example.com", capturedEvent.getUserEmail());
        assertEquals("John Doe", capturedEvent.getUserNames());
    }

    @Test
    void testRegisterUserPasswordsDoNotMatchThrowsException() {
        // Arrange: Подготовка на входни данни с несъвпадащи пароли
        UserRegistrationDTO registrationDTO = new UserRegistrationDTO(
                "John",
                "Doe",
                "john.doe@example.com",
                "password123",
                "differentPassword");

        // Act & Assert: Очакване на изключение
        assertThrows(InvalidPasswordException.class,
                () -> serviceToTest.registerUser(registrationDTO));

        // Проверка, че не се извикват методи за запис и публикуване
        verify(mockUserRepository, never()).save(any(UserEntity.class));
        verify(mockEventPublisher, never()).publishEvent(any());
    }

    @Test
    void testRegisterUserInvalidPasswordThrowsException() {
        // Тест за изключение при невалидна парола (твърде кратка)

        // Arrange: Подготовка на входни данни
        UserRegistrationDTO registrationDTO = new UserRegistrationDTO(
                "John",
                "Doe",
                "john.doe@example.com",
                "123",
                "123");

        // Act & Assert: Проверка, че се хвърля изключение и не се извикват методи за запис и публикуване
        assertThrows(InvalidPasswordException.class,
                () -> serviceToTest.registerUser(registrationDTO));

        verify(mockUserRepository, never()).save(any(UserEntity.class));
        verify(mockEventPublisher, never()).publishEvent(any());
    }

    @Test
    void testCreateUserIfNotExistUserDoesNotExistSavesUser() {
        // Тест за създаване на нов потребител, ако той не съществува

        // Arrange: Mock, който връща празен Optional (потребителят не съществува)
        when(mockUserRepository.findByEmail("jane.doe@example.com"))
                .thenReturn(Optional.empty());

        // Act: Извикване на метода createUserIfNotExist
        serviceToTest.createUserIfNotExist("jane.doe@example.com", "Jane Doe");

        // Assert: Проверка, че методът save е извикан с правилния потребител
        verify(mockUserRepository).save(argThat(user ->
                user.getEmail().equals("jane.doe@example.com") &&
                        user.getFirstName().equals("Jane") &&
                        user.getLastName().equals("Doe")));
    }

    @Test
    void testCreateUserIfNotExistUserExistsDoesNothing() {
        // Тест за случай, когато потребителят вече съществува

        // Arrange: Mock, който връща съществуващ потребител
        when(mockUserRepository.findByEmail("jane.doe@example.com"))
                .thenReturn(Optional.of(new UserEntity()));

        // Act: Извикване на метода createUserIfNotExist
        serviceToTest.createUserIfNotExist("jane.doe@example.com", "Jane Doe");

        // Assert: Проверка, че методът save не е извикан
        verify(mockUserRepository, never()).save(any());
    }

    @Test
    void testLoginValidUserAuthenticationCreated() {
        // Тест за успешно логване на потребител

        // Arrange: Подготовка на mock UserDetails обект и поведение
        String email = "test@example.com";
        UserDetails mockUserDetails = mock(UserDetails.class);
        when(mockUserDetails.getPassword()).thenReturn("encodedPassword");
        when(mockUserDetailsService.loadUserByUsername(email))
                .thenReturn(mockUserDetails);

        // Act: Извикване на метода login
        Authentication authentication = serviceToTest.login(email);

        // Assert: Проверка, че се връща валиден Authentication обект
        assertNotNull(authentication);
        assertEquals(mockUserDetails, authentication.getPrincipal());
        assertEquals("encodedPassword", authentication.getCredentials());
        verify(mockUserDetailsService).loadUserByUsername(email);
    }
}

