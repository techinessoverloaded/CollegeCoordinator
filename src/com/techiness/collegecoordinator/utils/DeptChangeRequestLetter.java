package com.techiness.collegecoordinator.utils;

import com.techiness.collegecoordinator.abstraction.RequestLetter;

public final class DeptChangeRequestLetter extends RequestLetter
{
    private String destinationDeptId;

    public DeptChangeRequestLetter(String requesterId, String receiverId, String requesterDeptId, String reasonForRequest, String destinationDeptId)
    {
        super(requesterId, receiverId, requesterDeptId, reasonForRequest);
        this.destinationDeptId = destinationDeptId;
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
                ", \ndestinationDeptId = " + destinationDeptId + " ]";
    }
}
