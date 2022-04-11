package com.techiness.collegecoordinator.concrete;

import com.techiness.collegecoordinator.abstraction.RequestLetter;

public final class ResignationRequestLetter extends RequestLetter
{
    private String requestedQuitDate;

    public ResignationRequestLetter(String requesterId, String receiverId, String requesterDeptId, String reasonForRequest, String requestedQuitDate)
    {
        super(requesterId, receiverId, requesterDeptId, reasonForRequest);
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
