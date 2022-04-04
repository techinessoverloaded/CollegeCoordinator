package com.techiness.collegecoordinator.abstraction;

import com.techiness.collegecoordinator.concrete.HoD;
import com.techiness.collegecoordinator.enums.UserType;
import com.techiness.collegecoordinator.helpers.AccountsManager;
import com.techiness.collegecoordinator.helpers.StringToEnumUtils;
import java.io.Serializable;
import static com.techiness.collegecoordinator.helpers.IOUtils.getDateFormatter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Observable;

public abstract class RequestLetter extends Observable implements Serializable, Identifiable, Comparable<RequestLetter>
{
    private String id;
    private String requesterId;
    private String receiverId;
    private String submittedDate;
    private String reasonForRequest;
    private String reasonForDisapproval;
    private boolean isApproved;
    private boolean toBeNotifiedToRequester;
    private boolean isNotifiedToRequester;

    public RequestLetter(String requesterId, String receiverId, String reasonForRequest)
    {
        this.requesterId = requesterId;
        this.receiverId = receiverId;
        this.submittedDate = LocalDate.now(ZoneId.systemDefault()).format(getDateFormatter());
        this.reasonForRequest = reasonForRequest;
        this.isApproved = this.isNotifiedToRequester = this.toBeNotifiedToRequester = false;
        this.reasonForDisapproval = "";
        deduceLetterIdFromReceiverId();
    }

    @Override
    public String getId()
    {
        return id+"$"+requesterId+"~"+receiverId;
    }

    @Override
    public void setId(String id)
    {
        this.id = id;
    }

    private void deduceLetterIdFromReceiverId()
    {
        UserType receiverUserType = StringToEnumUtils.getUserTypeFromUserId(receiverId);
        int letterCount = 1;
        switch (receiverUserType)
        {
            case ADMIN:
                letterCount = AccountsManager.getInstance().getAdmin().getLetters().size()+1;
                break;
            case HOD:
            case TRAINING_HEAD:
                letterCount = ((HoD)AccountsManager.getInstance().getUsers(receiverId)).getLetters().size()+1;
                break;
            default:
                break;
        }
        this.id = String.valueOf(letterCount);
    }
    public String getRequesterId()
    {
        return requesterId;
    }

    public void setRequesterId(String requesterId)
    {
        this.requesterId = requesterId;
    }

    public String getSubmittedDate()
    {
        return submittedDate;
    }

    public void setSubmittedDate(String submittedDate)
    {
        this.submittedDate = submittedDate;
    }

    public String getReasonForRequest()
    {
        return reasonForRequest;
    }

    public void setReasonForRequest(String reasonForRequest)
    {
        this.reasonForRequest = reasonForRequest;
    }

    public String getReasonForDisapproval()
    {
        return reasonForDisapproval;
    }

    public void setReasonForDisapproval(String reasonForDisapproval)
    {
        this.reasonForDisapproval = reasonForDisapproval;
    }

    public boolean isToBeNotifiedToRequester()
    {
        return toBeNotifiedToRequester;
    }

    public void setToBeNotifiedToRequester(boolean toBeNotifiedToRequester)
    {
        this.toBeNotifiedToRequester = toBeNotifiedToRequester;
    }

    public boolean isNotifiedToRequester()
    {
        return isNotifiedToRequester;
    }

    public void setNotifiedToRequester(boolean notifiedToRequester)
    {
        isNotifiedToRequester = notifiedToRequester;
    }

    public boolean getIsApproved()
    {
        return isApproved;
    }

    public void setIsApproved(boolean granted)
    {
        isApproved = granted;
        setChanged();
        notifyObservers();
    }

    public boolean getIsNotifiedToRequester()
    {
        return isNotifiedToRequester;
    }

    public void setIsNotifiedToRequester(boolean notifiedToRequester)
    {
        isNotifiedToRequester = notifiedToRequester;
        setChanged();
        notifyObservers();
    }

    public String getReceiverId()
    {
        return receiverId;
    }

    public void setReceiverId(String receiverId)
    {
        this.receiverId = receiverId;
    }

    @Override
    public int compareTo(RequestLetter o)
    {
        return getId().compareTo(o.getId());
    }

    @Override
    public String toString()
    {

        return " [ \nid = "+getId()+", \nrequesterId = "+requesterId+", \nreceiverId = "+receiverId+", \nsubmittedDate = "+submittedDate+
                ", \nreasonForRequest = "+reasonForRequest+", \nreasonForDisapproval = "+reasonForDisapproval+"," +
                ", \nisApproved = "+isApproved+", \ntoBeNotifiedToRequester = "+toBeNotifiedToRequester+", \nisNotifiedToRequester = "+ isNotifiedToRequester;
    }
}
