package com.techiness.collegecoordinator.helpers;

import com.techiness.collegecoordinator.abstraction.Identifiable;
import com.techiness.collegecoordinator.enums.LetterType;
import java.io.Serializable;
import java.util.Date;

public class Letter implements Serializable, Identifiable, Comparable<Letter>
{
    private String id;
    private String requesterId;
    private LetterType letterType;
    private Date date;
    private String reason;
    private boolean isGranted;
    private boolean isNotifiedToRequester;

    public Letter(int id, String requesterId, LetterType letterType, Date date, String reason)
    {
        this.id = String.valueOf(id);
        this.requesterId = requesterId;
        this.letterType = letterType;
        this.date = date;
        this.reason = reason;
        this.isGranted = this.isNotifiedToRequester = false;
    }


    @Override
    public String getId()
    {
        return id +"$"+requesterId;
    }

    @Override
    public void setId(String id)
    {
        this.id = id;
    }

    public String getRequesterId()
    {
        return requesterId;
    }

    public void setRequesterId(String requesterId)
    {
        this.requesterId = requesterId;
    }

    public LetterType getLetterType()
    {
        return letterType;
    }

    public void setLetterType(LetterType letterType)
    {
        this.letterType = letterType;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public String getReason()
    {
        return reason;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }

    public boolean getIsGranted()
    {
        return isGranted;
    }

    public void setIsGranted(boolean granted)
    {
        isGranted = granted;
    }

    public boolean getIsNotifiedToRequester()
    {
        return isNotifiedToRequester;
    }

    public void setIsNotifiedToRequester(boolean notifiedToRequester)
    {
        isNotifiedToRequester = notifiedToRequester;
    }

    @Override
    public int compareTo(Letter o)
    {
        return getId().compareTo(o.getId());
    }
}
