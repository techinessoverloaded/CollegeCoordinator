package com.techiness.collegecoordinator.helpers;

import com.techiness.collegecoordinator.abstraction.RequestLetter;

public final class DemotionRequestLetter extends RequestLetter
{
    private String destinationDeptId;

    public DemotionRequestLetter(String requesterId, String receiverId, String reasonForRequest, String destinationDeptId)
    {
        super(requesterId, receiverId, reasonForRequest);
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
        return "DemotionRequestLetter" + super.toString()+ ", \ndestinationDeptId = " + destinationDeptId + " ]";
    }
}
