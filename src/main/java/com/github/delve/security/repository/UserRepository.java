package com.github.delve.security.repository;

import com.github.delve.security.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    Optional<User> findByUsername(final String username);

    Boolean existsByUsername(final String username);

    Boolean existsByEmail(final String email);

    @Query("select user.name from User user " +
            "where user.id = :id ")
    String getUserNameById(@Param("id") final Long id);
}
