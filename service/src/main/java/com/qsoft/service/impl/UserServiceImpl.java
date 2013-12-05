package com.qsoft.service.impl;

import com.qsoft.constant.Constants;
import com.qsoft.dao.UserDAO;
import com.qsoft.model.common.Users;
import com.qsoft.model.dto.UserDTO;
import com.qsoft.model.dto.UserResponseDTO;
import com.qsoft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: quynhtq
 * Date: 12/5/13
 */
@Component
@Transactional
public class UserServiceImpl implements UserService
{
    @Autowired
    UserDAO userDAO;

    public UserResponseDTO addAndUpdateUser(UserDTO userDTO)
    {
        UserResponseDTO userResponseDTO;
        if (userDTO.getName() == null || userDTO.getPassword() == null ||
                "".equals(userDTO.getName()) || "".equals(userDTO.getPassword()))
        {
            userResponseDTO = createResponseDTO(Constants.FAIL, Constants.PARAMETER_INVALID);
            return userResponseDTO;
        }
        if (userDTO.getWebId() != null)
        {
            Users users = userDAO.findOne(userDTO.getWebId());
            if (users == null)
            {
                userResponseDTO = createResponseDTO(Constants.FAIL, Constants.USER_DOES_NOT_EXIST);
                return userResponseDTO;
            }
        }
        Users users = new Users();
        users.setUserName(userDTO.getName());
        users.setPassword(userDTO.getPassword());
        userDAO.save(users);
        userResponseDTO = createResponseDTO(Constants.SUCCESS, Constants.SUCCESS);
        userResponseDTO.setUserDTO(new UserDTO(users));
        return userResponseDTO;
    }

    public UserResponseDTO getUser(Long userId)
    {
        UserResponseDTO userResponseDTO;
        if (userId == null)
        {
            userResponseDTO = createResponseDTO(Constants.FAIL, Constants.USER_DOES_NOT_EXIST);
            return userResponseDTO;
        }
        Users users = userDAO.findOne(userId);
        if (users == null)
        {
            userResponseDTO = createResponseDTO(Constants.FAIL, Constants.USER_DOES_NOT_EXIST);
            return userResponseDTO;
        }
        userResponseDTO = createResponseDTO(Constants.SUCCESS, Constants.SUCCESS);
        userResponseDTO.setUserDTO(new UserDTO(users));
        return userResponseDTO;
    }

    public UserResponseDTO createResponseDTO(String result, String message)
    {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setMessage(message);
        userResponseDTO.setResult(result);
        return userResponseDTO;
    }
}
