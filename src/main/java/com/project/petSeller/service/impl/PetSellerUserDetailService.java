package com.project.petSeller.service.impl;

import com.project.petSeller.model.entity.UserEntity;
import com.project.petSeller.model.entity.UserRoleEntity;
import com.project.petSeller.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * PetSellerUserDetailService е имплементация на UserDetailsService от Spring Security.
 * Този клас зарежда детайлите на потребителя от базата данни, използвайки UserRepository,
 * и преобразува данните във формат, който може да бъде използван от Spring Security.
 */
public class PetSellerUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    // Конструктор, който инжектира UserRepository, използван за достъп до базата данни за потребителите
    public PetSellerUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Търси потребителя по имейл в базата данни.
     * Ако потребителят е намерен, преобразува го във формат UserDetails за Spring Security.
     * При липса на потребител хвърля UsernameNotFoundException.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(email)
                .map(PetSellerUserDetailService::map)
                .orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found!"));
    }

    /**
     * Преобразува UserEntity обект във UserDetails обект за Spring Security.
     * Задава имейл, парола и роли на потребителя.
     */
    private static UserDetails map(UserEntity userEntity) {
        return User
                .withUsername(userEntity.getEmail())
                .password(userEntity.getPassword())
                .authorities(userEntity.getRoles().stream().map(PetSellerUserDetailService::map).toList())
                .build();
    }

    /**
     * Преобразува UserRoleEntity обект във GrantedAuthority обект,
     * като добавя "ROLE_" префикс към името на ролята.
     */
    private static GrantedAuthority map(UserRoleEntity userRoleEntity) {
        return new SimpleGrantedAuthority(
                "ROLE_" + userRoleEntity.getRole().name()
        );
    }
}
