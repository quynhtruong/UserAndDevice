package com.qsoft.model.common;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * User: quynhtq
 * Date: 12/4/13
 */
@Entity
@Table(name = "device")
public class Device implements Serializable
{
    @Id
    @Column(name = "web_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long webId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    @Column(name = "name")
    private String deviceName;

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

    public Device()
    {
    }

    //setter and getter

    public Long getWebId()
    {
        return webId;
    }

    public void setWebId(Long webId)
    {
        this.webId = webId;
    }

    public Users getUsers()
    {
        return users;
    }

    public void setUsers(Users users)
    {
        this.users = users;
    }

    public String getDeviceName()
    {
        return deviceName;
    }

    public void setDeviceName(String deviceName)
    {
        this.deviceName = deviceName;
    }

    public Date getLastUpdated()
    {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated)
    {
        this.lastUpdated = lastUpdated;
    }
}
