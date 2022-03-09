package com.techiness.collegecoordinator.helpers;

import com.techiness.collegecoordinator.enums.LetterType;
import java.util.Date;

public class Letter
{
    private String requesterId;
    private LetterType letterType;
    private Date date;
    private String reason;
    private boolean isGranted;

    public Letter(String requesterId, LetterType letterType, Date date, String reason)
    {
        this.requesterId = requesterId;
        this.letterType = letterType;
        this.date = date;
        this.reason = reason;
        this.isGranted = false;
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
}
