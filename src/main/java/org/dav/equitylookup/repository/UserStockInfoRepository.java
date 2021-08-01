package org.dav.equitylookup.repository;

import org.dav.equitylookup.model.UserStockInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStockInfoRepository extends JpaRepository<UserStockInfo,Long> {
}
