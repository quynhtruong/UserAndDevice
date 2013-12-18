package com.qsoft.model.dto;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * User: luult
 * Date: 12/13/13
 * Time: 5:18 PM
 */
public class RequestLatestDTO implements Serializable
{
    Long userId;
    Date latestTime;

    public RequestLatestDTO()
    {
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Date getLatestTime()
    {
        return latestTime;
    }

    public void setLatestTime(Date latestTime)
    {
        this.latestTime = latestTime;
    }
}
