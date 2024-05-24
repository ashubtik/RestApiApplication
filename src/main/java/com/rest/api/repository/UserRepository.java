package com.rest.api.repository;

import com.rest.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.company = :company")
    List<User> findByCompany(@Param("company") String company);

    @Query("select u from User u where u.city = :city")
    List<User> findByCity(@Param("city") String city);

    @Query("select u from User u where u.company = :company and u.city = :city")
    List<User> findByCityAndCompany(@Param("company") String company, @Param("city") String city);

    @Query("select u from User u where u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);
}
