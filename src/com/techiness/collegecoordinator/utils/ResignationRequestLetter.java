package com.techiness.collegecoordinator.utils;

import com.techiness.collegecoordinator.abstraction.RequestLetter;

public final class ResignationRequestLetter extends RequestLetter
{
    private String requestedQuitDate;

    public ResignationRequestLetter(String requesterId, String receiverId, String reasonForRequest, String requestedQuitDate)
    {
        super(requesterId, receiverId, reasonForRequest);
        this.requestedQuitDate = requestedQuitDate;
    }

    public String getRequestedQuitDate()
    {
        return requestedQuitDate;
    }

    public void setRequestedQuitDate(String requestedQuitDate)
    {
        this.requestedQuitDate = requestedQuitDate;
    }

    @Override
    public String toString()
    {
        return "ResignationRequestLetter"+super.toString()+", \nrequestedQuitDate = "+ requestedQuitDate +" ]";
    }
}
