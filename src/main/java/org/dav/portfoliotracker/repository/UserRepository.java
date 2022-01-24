package org.dav.portfoliotracker.repository;

import org.dav.portfoliotracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u where u.email=:email")
    User findByEmail(String email);
}
