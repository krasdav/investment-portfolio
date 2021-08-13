package org.dav.equitylookup.service;

import org.dav.equitylookup.model.Share;

import java.util.List;

public interface ShareService {
    List<Share> getAllShares();
    void saveShare(Share share);
    void deleteShareById(long id);
}
