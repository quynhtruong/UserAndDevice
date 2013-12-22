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
    private String _id;
    private Boolean isDeleted = false;
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


    public String get_id()
    {
        return _id;
    }

    public void set_id(String _id)
    {
        this._id = _id;
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

    public Boolean getDeleted()
    {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted)
    {
        isDeleted = deleted;
    }
}
