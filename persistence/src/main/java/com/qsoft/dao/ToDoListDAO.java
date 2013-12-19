package com.qsoft.dao;

import com.qsoft.model.common.ToDoList;
import com.qsoft.model.common.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * User: quynhtq
 * Date: 12/4/13
 */
public interface ToDoListDAO extends JpaRepository<ToDoList, Long>
{
    @Query("select tdl from ToDoList tdl where tdl.users=:users")
    List<ToDoList> findByUsers(@Param("users") Users users);

    List<ToDoList> findByUsersAndLastUpdatedGreaterThan(@Param("users") Users users,@Param("date") Date date);
}
