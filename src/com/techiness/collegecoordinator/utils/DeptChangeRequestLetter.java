package com.techiness.collegecoordinator.utils;

import com.techiness.collegecoordinator.abstraction.RequestLetter;

public final class DeptChangeRequestLetter extends RequestLetter
{
    private String destinationDeptId;
    private String currentDeptId;

    public DeptChangeRequestLetter(String requesterId, String receiverId, String reasonForRequest, String currentDeptId, String destinationDeptId)
    {
        super(requesterId, receiverId, reasonForRequest);
        this.currentDeptId = currentDeptId;
        this.destinationDeptId = destinationDeptId;
    }

    public String getCurrentDeptId()
    {
        return currentDeptId;
    }

    public void setCurrentDeptId(String currentDeptId)
    {
        this.currentDeptId = currentDeptId;
    }

    public String getDestinationDeptId()
    {
        return destinationDeptId;
    }

    public void setDestinationDeptId(String destinationDeptId)
    {
        this.destinationDeptId = destinationDeptId;
    }

    @Override
    public String toString()
    {
        return "DeptChangeRequestLetter" + super.toString() +
                ", \ndestinationDeptId = " + destinationDeptId +
                ", \ncurrentDeptId = " + currentDeptId + " ]";
    }
}
