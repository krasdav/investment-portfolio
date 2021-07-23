package org.dav.equitylookup.repository;

import org.dav.equitylookup.model.Stock;
import org.dav.equitylookup.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    @Query("SELECT u FROM User u where u.id = 1")
    User findFirstUser();

}
