package org.dav.equitylookup.repository;

import org.dav.equitylookup.model.Share;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShareRepository extends JpaRepository<Share,Long> {
}
