package com.colosseo.repository;

import com.colosseo.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsById(Long userId);
    boolean existsByEmail(String email);
//    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

}
