package com.zvenova.smart_url.service;

import com.zvenova.smart_url.domain.entity.Link;
import com.zvenova.smart_url.api.exception.link.LinkDoesNotExistsException;
import com.zvenova.smart_url.domain.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional  //TODO
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;

    public Link findByFullLink(String fullLink) throws LinkDoesNotExistsException {

        return linkRepository.findByFullLink(fullLink)
                .orElseThrow(() -> new LinkDoesNotExistsException(fullLink));
    }

    public Link saveLink(Link linkToSave) throws LinkDoesNotExistsException {

        if (isLinkPresentByFullLink(linkToSave.getFullLink()))
            return findByFullLink(linkToSave.getFullLink());
        return linkRepository.save(linkToSave);
    }

    public void deleteLink(Long idToDelete) throws LinkDoesNotExistsException {

        if (!isLinkPresentById(idToDelete))
            throw new LinkDoesNotExistsException(idToDelete.toString());

        linkRepository.deleteById(idToDelete);
    }

    public Link findById(Long id) throws LinkDoesNotExistsException {

        return linkRepository.findById(id).orElseThrow(() ->
                new LinkDoesNotExistsException(String.format("Link with id %s is not exist", id)));
    }

    public Link findBySmartLink(String smartLink) throws LinkDoesNotExistsException {

        return linkRepository.findBySmartLink(smartLink).orElseThrow(
                () -> new LinkDoesNotExistsException(smartLink)
        );
    }

    public boolean isLinkPresentByFullLink(String fullLink) {

        return linkRepository.findByFullLink(fullLink).isPresent();
    }

    public boolean isLinkPresentBySmartLink(String smartLink) {

        return linkRepository.findBySmartLink(smartLink).isPresent();
    }

    private boolean isLinkPresentById(Long id) {

        return linkRepository.existsById(id);
    }
}
