package org.dav.equitylookup.service.implementation;

import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.model.Share;
import org.dav.equitylookup.repository.ShareRepository;
import org.dav.equitylookup.service.ShareService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShareServiceImpl implements ShareService {

    private final ShareRepository shareRepository;

    @Override
    public List<Share> getAllShares() {
        return shareRepository.findAll();
    }

    @Override
    public void saveShare(Share share) {
        shareRepository.save(share);
    }

    @Override
    public void deleteShareById(long id) {
        shareRepository.deleteById(id);
    }
}
