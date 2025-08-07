package com.blitz.resumescreener.repository;

import com.blitz.resumescreener.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data JPA will automatically create the query for this method
    // based on the method name.
    Optional<User> findByUsername(String username);
}