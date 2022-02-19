package com.zvenova.like_my.repository;

import com.zvenova.like_my.domain.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

    Optional<Link> findByFullLinkEquals(String username);

    Link findBySmartLinkEquals(String smartLink);

    boolean existsBySmartLinkEquals(String smartLink);
}
