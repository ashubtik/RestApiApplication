package com.rest.api.repository;

import com.rest.api.entity.Message;
import com.rest.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query("select u from Message u where u.user = :user")
    List<Message> findByUserId(@Param("user") User user);
}
