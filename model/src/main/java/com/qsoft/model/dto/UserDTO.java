package com.qsoft.model.dto;

import com.qsoft.model.common.Users;

import java.io.Serializable;

/**
 * User: quynhtq
 * Date: 12/5/13
 */
public class UserDTO implements Serializable
{
    private Long webId;
    private String name;
    private String password;

    //constructor
    public UserDTO()
    {
    }

    public UserDTO(Users users)
    {
        this.webId = users.getWebId();
        this.name = users.getUserName();
        this.password = users.getPassword();
    }
    //getter and setter

    public Long getWebId()
    {
        return webId;
    }

    public void setWebId(Long webId)
    {
        this.webId = webId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
