package com.qsoft.service;

import com.qsoft.model.dto.UserResponseDTO;
import com.qsoft.service.impl.UserServiceImpl;
import org.junit.Test;

import static org.junit.Assert.assertNull;

/**
 * Created by quynhtq on 9/24/14.
 */
public class UserServiceTest
{
    UserService userService;

    @Test
    public void test()
    {
        userService = new UserServiceImpl();
        UserResponseDTO userResponseDTO = userService.addAndUpdateUser(null);
        assertNull(userResponseDTO);
    }
}
