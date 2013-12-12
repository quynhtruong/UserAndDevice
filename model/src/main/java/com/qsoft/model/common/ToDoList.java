package com.qsoft.model.common;

import javax.persistence.*;
import java.util.Date;

/**
 * User: quynhtq
 * Date: 12/4/13
 */
@Entity
@Table(name = "to_do_list")
public class ToDoList
{
    @Id
    @Column(name = "web_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long webId;

    @Column(name = "description")
    private String description;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "last_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    @PrePersist
    @PreUpdate
    public void onUpdate()
    {
        if (lastUpdated == null)
        {
            lastUpdated = new Date();
        }
        if (isDeleted == null)
        {
            isDeleted = true;
        }
    }
    //constructor

    public ToDoList()
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

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Boolean getDeleted()
    {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted)
    {
        isDeleted = deleted;
    }

    public Date getLastUpdated()
    {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated)
    {
        this.lastUpdated = lastUpdated;
    }

    public Users getUsers()
    {
        return users;
    }

    public void setUsers(Users users)
    {
        this.users = users;
    }
}
