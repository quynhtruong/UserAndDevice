package com.qsoft.service;

import com.qsoft.model.dto.UserDTO;
import com.qsoft.model.dto.UserResponseDTO;

/**
 * User: quynhtq
 * Date: 12/5/13
 */
public interface UserService
{
    public UserResponseDTO addAndUpdateUser(UserDTO userDTO);
    public UserResponseDTO getUser(Long userId);
}
