package com.zvenova.like_my.service;

import com.zvenova.like_my.domain.entity.Link;
import com.zvenova.like_my.exception.link.LinkDoesNotExistsException;
import com.zvenova.like_my.exception.link.LinkIsAlreadyPresentException;
import com.zvenova.like_my.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;

    public Link loadLinkByFullLink(String fullLink) throws LinkDoesNotExistsException {

        return linkRepository.findByFullLinkEquals(fullLink)
                .orElseThrow(() -> new LinkDoesNotExistsException(fullLink));
    }

    public Link saveLink(Link linkToSave) throws LinkIsAlreadyPresentException {

        if (isLinkPresentByFullLink(linkToSave.getFullLink()))
            throw new LinkIsAlreadyPresentException(linkToSave.getFullLink());
        return linkRepository.save(linkToSave);
    }

    public void deleteLink(Link linkToDelete) throws LinkDoesNotExistsException {

        if (!isLinkPresentById(linkToDelete.getId()))
            throw new LinkDoesNotExistsException(linkToDelete.getFullLink());

        linkRepository.deleteById(linkToDelete.getId());
    }

    public Link findById(Long id) throws LinkDoesNotExistsException {

        return linkRepository.findById(id).orElseThrow(() ->
                new LinkDoesNotExistsException(id.toString()));
    }

    public Link findLinkBySmartLink(Link link) throws LinkDoesNotExistsException {

        if (!isLinkPresentBySmartLink(link.getSmartLink()))
            throw new LinkDoesNotExistsException(link.getFullLink());
        return linkRepository.findBySmartLinkEquals(link.getSmartLink());
    }

    private boolean isLinkPresentByFullLink(String fullLink) {

        return linkRepository.findByFullLinkEquals(fullLink).isPresent();
    }

    private boolean isLinkPresentById(Long id) {

        return linkRepository.existsById(id);
    }

    private boolean isLinkPresentBySmartLink(String smartLink) {

        return linkRepository.existsBySmartLinkEquals(smartLink);
    }
}
