package com.techiness.collegecoordinator.utils;

import com.techiness.collegecoordinator.abstraction.RequestLetter;

public final class TCRequestLetter extends RequestLetter
{
    private String currentDeptID;

    public TCRequestLetter(String requesterId, String receiverId, String reasonForRequest, String currentDeptID)
    {
        super(requesterId, receiverId, reasonForRequest);
        this.currentDeptID = currentDeptID;
    }

    public String getCurrentDeptID()
    {
        return currentDeptID;
    }

    public void setCurrentDeptID(String currentDeptID)
    {
        this.currentDeptID = currentDeptID;
    }

    @Override
    public String toString()
    {
        return "TCRequestLetter"+super.toString()+", \ncurrentDeptId = "+ currentDeptID +" ]";
    }
}
