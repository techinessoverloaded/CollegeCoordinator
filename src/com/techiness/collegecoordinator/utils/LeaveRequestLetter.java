package com.techiness.collegecoordinator.utils;

import com.techiness.collegecoordinator.abstraction.RequestLetter;

import java.util.Arrays;

public final class LeaveRequestLetter extends RequestLetter
{
    private String[] leaveRequestedDates;

    public LeaveRequestLetter(String requesterId, String receiverId, String reasonForRequest, String[] leaveRequestedDates)
    {
        super(requesterId, receiverId, reasonForRequest);
        this.leaveRequestedDates = leaveRequestedDates;
    }

    public String[] getLeaveRequestedDates()
    {
        return leaveRequestedDates;
    }

    public void setLeaveRequestedDates(String[] leaveRequestedDates)
    {
        this.leaveRequestedDates = leaveRequestedDates;
    }

    @Override
    public String toString()
    {
        return "LeaveRequestLetter"+super.toString()+", \nleaveRequestedDates = "+ Arrays.toString(leaveRequestedDates) +" ]";
    }
}
