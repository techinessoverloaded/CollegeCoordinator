package com.techiness.collegecoordinator.helpers;

import com.techiness.collegecoordinator.abstraction.Identifiable;
import com.techiness.collegecoordinator.enums.LetterType;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;

public class Letter extends Observable implements Serializable, Identifiable, Comparable<Letter>
{
    private String id;
    private String requesterId;
    private String receiverId;
    private LetterType letterType;
    private Date date;
    private String reason;
    private boolean isGranted;
    private boolean isNotifiedToRequester;

    public Letter(int id, String requesterId, String recevierId, LetterType letterType, String reason)
    {
        this.id = String.valueOf(id);
        this.requesterId = requesterId;
        this.receiverId = recevierId;
        this.letterType = letterType;
        this.date = Calendar.getInstance().getTime();
        this.reason = reason;
        this.isGranted = this.isNotifiedToRequester = false;
    }

    @Override
    public String getId()
    {
        return id+"$"+requesterId+"~"+receiverId;
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
        setChanged();
        notifyObservers();
    }

    public boolean getIsNotifiedToRequester()
    {
        return isNotifiedToRequester;
    }

    public void setIsNotifiedToRequester(boolean notifiedToRequester)
    {
        isNotifiedToRequester = notifiedToRequester;
        setChanged();
        notifyObservers();
    }

    public String getReceiverId()
    {
        return receiverId;
    }

    public void setReceiverId(String receiverId)
    {
        this.receiverId = receiverId;
    }

    @Override
    public int compareTo(Letter o)
    {
        return getId().compareTo(o.getId());
    }
}
