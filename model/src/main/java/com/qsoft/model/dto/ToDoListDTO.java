package com.qsoft.model.dto;

import com.qsoft.model.common.ToDoList;

import java.io.Serializable;

/**
 * User: quynhtq
 * Date: 12/5/13
 */
public class ToDoListDTO implements Serializable
{
    private Long webId;
    private String description;
    private Long userId;
    private String lastUpdated;
    private String clientId;
    //constructor
    public ToDoListDTO()
    {
    }

    public ToDoListDTO(ToDoList toDoList)
    {
        this.webId = toDoList.getWebId();
        this.description = toDoList.getDescription();
        this.lastUpdated = toDoList.getLastUpdated().toString();
        if (toDoList.getUsers() != null)
        {
            this.userId = toDoList.getUsers().getWebId();
        }
    }
    //getter and setter

    public String getClientId()
    {
        return clientId;
    }

    public void setClientId(String clientId)
    {
        this.clientId = clientId;
    }

    public Long getWebId()
    {
        return webId;
    }

    public void setWebId(Long webId)
    {
        this.webId = webId;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getLastUpdated()
    {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated)
    {
        this.lastUpdated = lastUpdated;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }
}
