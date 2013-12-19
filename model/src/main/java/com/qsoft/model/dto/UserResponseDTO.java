package com.qsoft.model.dto;

import java.io.Serializable;

/**
 * User: quynhtq
 * Date: 12/5/13
 */
public class UserResponseDTO implements Serializable
{
    private String result;
    private String message;
    private UserDTO userDTO;
    //constructor

    public UserResponseDTO()
    {
    }

    //getter and setter

    public UserDTO getUserDTO()
    {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO)
    {
        this.userDTO = userDTO;
    }

    public String getResult()
    {
        return result;
    }

    public void setResult(String result)
    {
        this.result = result;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
