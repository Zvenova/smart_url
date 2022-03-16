package com.zvenova.like_my.service;

import com.zvenova.like_my.base.BaseTestWithoutDB;
import com.zvenova.like_my.domain.entity.Link;
import com.zvenova.like_my.api.exception.link.LinkDoesNotExistsException;
import com.zvenova.like_my.api.exception.link.LinkIsAlreadyPresentException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LinkServiceTest extends BaseTestWithoutDB {

    @Autowired
    private LinkService linkService;

    @Nested
    public class TestFindLinkByFullLink {

        @Test
        public void whenLinkIsPresent() throws LinkDoesNotExistsException {

            Link testLink = getTestLink();
            doReturn(Optional.of(testLink)).when(linkRepository).findByFullLink(testLink.getFullLink());

            Link linkByFullLink = linkService.findByFullLink(testLink.getFullLink());
            assertEquals(testLink, linkByFullLink);
            verify(linkRepository, times(1)).findByFullLink(testLink.getFullLink());
        }

        @Test
        public void whenLinkNotPresent() {

            Link testLink = getTestLink();
            doReturn(Optional.empty()).when(linkRepository).findByFullLink(testLink.getFullLink());

            assertThrows(LinkDoesNotExistsException.class,
                    () -> linkService.findByFullLink(testLink.getFullLink()));
            verify(linkRepository, times(1)).findByFullLink(testLink.getFullLink());
        }
    }

    @Nested
    public class TestSaveLink {

        @Test
        public void whenLinkIsPresent() {

            Link testLink = getTestLink();
            doReturn(Optional.of(testLink)).when(linkRepository).findByFullLink(testLink.getFullLink());

            assertThrows(LinkIsAlreadyPresentException.class, () -> linkService.saveLink(testLink));
            verify(linkRepository, times(1)).findByFullLink(testLink.getFullLink());
            verify(linkRepository, times(0)).save(testLink);
        }

        @Test
        public void whenLinkNotPresent() throws LinkDoesNotExistsException {

            Link testLink = getTestLink();
            doReturn(testLink).when(linkRepository).save(testLink);

            Link linkFromDB = linkService.saveLink(testLink);

            assertEquals(testLink, linkFromDB);
            assertDoesNotThrow(() -> linkService.saveLink(testLink));
            verify(linkRepository, times(2)).findByFullLink(testLink.getFullLink());
            verify(linkRepository, times(2)).save(testLink);
        }
    }

    @Nested
    public class TestDeleteLink {

        @Test
        public void whenLinkIsPresent() {

            Link testLink = getTestLink();
            doNothing().when(linkRepository).deleteById(testLink.getId());
            doReturn(true).when(linkRepository).existsById(testLink.getId());

            assertDoesNotThrow(() -> linkService.deleteLink(testLink.getId()));
            verify(linkRepository, times(1)).deleteById(testLink.getId());
            verify(linkRepository, times(1)).existsById(testLink.getId());
        }

        @Test
        public void whenLinkNotPresent() {

            Link testLink = getTestLink();
            doNothing().when(linkRepository).deleteById(testLink.getId());
            doReturn(false).when(linkRepository).existsById(testLink.getId());

            assertThrows(LinkDoesNotExistsException.class, () -> linkService.deleteLink(testLink.getId()));
            verify(linkRepository, times(0)).deleteById(testLink.getId());
            verify(linkRepository, times(1)).existsById(testLink.getId());
        }
    }

    @Nested
    public class TestFindLinkById {

        @Test
        public void whenLinkIsPresent() throws LinkDoesNotExistsException {

            Link testLink = getTestLink();
            doReturn(Optional.of(testLink)).when(linkRepository).findById(testLink.getId());

            Link linkFromDB = linkService.findById(testLink.getId());
            assertDoesNotThrow(() -> linkService.findById(testLink.getId()));
            assertEquals(testLink, linkFromDB);
            verify(linkRepository, times(2)).findById(testLink.getId());
        }

        @Test
        public void whenLinkNotPresent() {

            Link testLink = getTestLink();
            doReturn(Optional.empty()).when(linkRepository).findById(testLink.getId());

            assertThrows(LinkDoesNotExistsException.class, () -> linkService.findById(testLink.getId()));
            verify(linkRepository, times(1)).findById(testLink.getId());
        }
    }

    @Nested
    public class TestFindLinkBySmartLink {

        @Test
        public void whenLinkIsPresent() throws LinkDoesNotExistsException {

            Link testLink = getTestLink();
            doReturn(Optional.of(testLink)).when(linkRepository).findBySmartLink(testLink.getSmartLink());

            Link linkFromDB = linkService.findBySmartLink(testLink.getSmartLink());

            assertEquals(testLink, linkFromDB);
            assertDoesNotThrow(() -> linkService.findBySmartLink(testLink.getSmartLink()));
            verify(linkRepository, times(2)).findBySmartLink(testLink.getSmartLink());
        }

        @Test
        public void whenLinkNotPresent() {

            Link testLink = getTestLink();
            doReturn(Optional.empty()).when(linkRepository).findBySmartLink(testLink.getSmartLink());

            assertThrows(LinkDoesNotExistsException.class, () -> linkService.findBySmartLink(testLink.getSmartLink()));
            verify(linkRepository, times(1)).findBySmartLink(testLink.getSmartLink());
        }
    }

    private Link getTestLink() {

        return Link.builder().id(12L).fullLink("https://www.crunchyroll.com/ru/tower-of-god").smartLink("l.m/tower").build();
    }
}
