package org.dav.portfoliotracker.repository;

import org.dav.portfoliotracker.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

}
