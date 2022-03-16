package com.zvenova.smart_url.domain.repository;

import com.zvenova.smart_url.domain.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

    Optional<Link> findByFullLink(String fullLink);

    Optional<Link> findBySmartLink(String smartLink);

}
