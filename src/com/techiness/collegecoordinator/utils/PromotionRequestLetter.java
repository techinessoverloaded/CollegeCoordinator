package com.techiness.collegecoordinator.utils;

import com.techiness.collegecoordinator.abstraction.RequestLetter;

public final class PromotionRequestLetter extends RequestLetter
{
    private String destinationDeptId;

    public PromotionRequestLetter(String requesterId, String receiverId, String requesterDeptId, String reasonForRequest, String destinationDeptId)
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
        return "PromotionRequestLetter"+super.toString()+", \ndestinationDeptId = " + destinationDeptId+" ]";
    }
}
