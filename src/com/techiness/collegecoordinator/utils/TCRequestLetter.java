package com.techiness.collegecoordinator.utils;

import com.techiness.collegecoordinator.abstraction.RequestLetter;

public final class TCRequestLetter extends RequestLetter
{
    public TCRequestLetter(String requesterId, String receiverId, String requesterDeptId, String reasonForRequest)
    {
        super(requesterId, receiverId, requesterDeptId, reasonForRequest);
    }

    @Override
    public String toString()
    {
        return "TCRequestLetter"+super.toString()+" ]";
    }
}
