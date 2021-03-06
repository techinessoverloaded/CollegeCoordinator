package com.techiness.collegecoordinator.concrete;

import com.techiness.collegecoordinator.abstraction.RequestLetter;
import java.util.Arrays;

public final class ODRequestLetter extends RequestLetter
{
    private String[] odRequestedDates;

    public ODRequestLetter(String requesterId, String receiverId, String requesterDeptId,String reasonForRequest, String[] odRequestedDates)
    {
        super(requesterId, receiverId, requesterDeptId, reasonForRequest);
        this.odRequestedDates = odRequestedDates;
    }

    public String[] getOdRequestedDates()
    {
        return odRequestedDates;
    }

    public void setOdRequestedDates(String[] odRequestedDates)
    {
        this.odRequestedDates = odRequestedDates;
    }

    @Override
    public String toString() {
        return "ODRequestLetter"+super.toString()+", \nodRequestedDates = "+ Arrays.toString(odRequestedDates) +" ]";
    }
}
