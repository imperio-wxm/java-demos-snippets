package com.wxmimperio.spring.repository;

import com.wxmimperio.spring.beans.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByName(String name);

    User findByNameAndAge(String name, Integer age);

    @Query("select u from User u where u.name = :name and u.id = :id")
    User withNameAndIdQuery(@Param("name") String name, @Param("id") Integer id);
}
