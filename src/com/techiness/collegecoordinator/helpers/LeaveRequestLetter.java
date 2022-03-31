package com.techiness.collegecoordinator.helpers;

import com.techiness.collegecoordinator.abstraction.RequestLetter;

import java.util.Date;

public final class LeaveRequestLetter extends RequestLetter
{
    private Date[] leaveRequestedDates;
    public LeaveRequestLetter(String requesterId, String receiverId, String reasonForRequest, Date[] leaveRequestedDates)
    {
        super(requesterId, receiverId, reasonForRequest);
        this.leaveRequestedDates = leaveRequestedDates;
    }

    public Date[] getLeaveRequestedDates()
    {
        return leaveRequestedDates;
    }

    public void setLeaveRequestedDates(Date[] leaveRequestedDates)
    {
        this.leaveRequestedDates = leaveRequestedDates;
    }
}
