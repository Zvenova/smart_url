package com.zvenova.like_my.repository;

import com.zvenova.like_my.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>  {
}
