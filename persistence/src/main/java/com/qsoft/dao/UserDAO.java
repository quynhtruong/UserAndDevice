package com.qsoft.dao;

import com.qsoft.model.common.Users;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: quynhtq
 * Date: 12/4/13
 */
public interface UserDAO extends JpaRepository<Users,Long>
{
}
