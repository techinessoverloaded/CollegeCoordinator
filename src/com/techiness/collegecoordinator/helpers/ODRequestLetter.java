package com.techiness.collegecoordinator.helpers;

import com.techiness.collegecoordinator.abstraction.RequestLetter;
import java.util.Date;

public final class ODRequestLetter extends RequestLetter
{
    private Date[] odRequestedDates;

    public ODRequestLetter(String requesterId, String receiverId, String reasonForRequest, Date[] odRequestedDates)
    {
        super(requesterId, receiverId, reasonForRequest);
        this.odRequestedDates = odRequestedDates;
    }

    public Date[] getOdRequestedDates()
    {
        return odRequestedDates;
    }

    public void setOdRequestedDates(Date[] odRequestedDates)
    {
        this.odRequestedDates = odRequestedDates;
    }
}
