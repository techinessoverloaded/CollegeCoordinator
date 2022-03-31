package com.techiness.collegecoordinator.helpers;

import com.techiness.collegecoordinator.abstraction.RequestLetter;

public final class DeptChangeRequestLetter extends RequestLetter
{
    private String destinationDeptId;

    public DeptChangeRequestLetter(String requesterId, String receiverId, String reasonForRequest, String destinationDeptId) {
        super(requesterId, receiverId, reasonForRequest);
        this.destinationDeptId = destinationDeptId;
    }

    public String getDestinationDeptId() {
        return destinationDeptId;
    }

    public void setDestinationDeptId(String destinationDeptId) {
        this.destinationDeptId = destinationDeptId;
    }
}
