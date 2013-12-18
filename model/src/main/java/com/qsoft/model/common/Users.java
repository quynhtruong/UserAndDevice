package com.qsoft.model.common;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * User: quynhtq
 * Date: 12/4/13
 */
@Entity
@Table(name = "users")
public class Users
{
    @Id
    @Column(name = "web_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long webId;

    @Column(name = "name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "last_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;

    @PrePersist
    @PreUpdate
    public void onUpdate()
    {
        if (lastUpdated == null)
        {
            lastUpdated = new Date();
        }
    }

    //constructor

    public Users()
    {
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

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }


    public Date getLastUpdated()
    {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated)
    {
        this.lastUpdated = lastUpdated;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }
}
