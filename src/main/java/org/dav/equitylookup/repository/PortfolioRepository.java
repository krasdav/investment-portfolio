package org.dav.equitylookup.repository;

import org.dav.equitylookup.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

}
