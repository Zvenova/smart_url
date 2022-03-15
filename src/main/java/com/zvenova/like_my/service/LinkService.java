package com.zvenova.like_my.service;

import com.zvenova.like_my.domain.entity.Link;
import com.zvenova.like_my.exception.link.LinkDoesNotExistsException;
import com.zvenova.like_my.exception.link.LinkIsAlreadyPresentException;
import com.zvenova.like_my.repository.LinkRepository;
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

    public Link saveLink(Link linkToSave) throws LinkIsAlreadyPresentException {

        if (isLinkPresentByFullLink(linkToSave.getFullLink()))
            throw new LinkIsAlreadyPresentException(linkToSave.getFullLink());
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

    private boolean isLinkPresentByFullLink(String fullLink) {

        return linkRepository.findByFullLink(fullLink).isPresent();
    }

    private boolean isLinkPresentById(Long id) {

        return linkRepository.existsById(id);
    }
}
