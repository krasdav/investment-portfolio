package org.dav.equitylookup.repository;

import org.dav.equitylookup.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
