package com.example.charitymanagementsystem;

public class RequestModel {
    String id;
    String requestText;
    boolean status;
    int count;
    String requestDate;
    String acceptDate;
    String userId;
    String sponsorUid;

    public RequestModel() {
    }

    public RequestModel(String id, String requestText, boolean status, int count, String requestDate, String acceptDate, String userId, String sponsorUid) {
        this.id = id;
        this.requestText = requestText;
        this.status = status;
        this.count = count;
        this.requestDate = requestDate;
        this.acceptDate = acceptDate;
        this.userId = userId;
        this.sponsorUid = sponsorUid;
    }

    public String getSponsorUid() {
        return sponsorUid;
    }

    public void setSponsorUid(String sponsorUid) {
        this.sponsorUid = sponsorUid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestText() {
        return requestText;
    }

    public void setRequestText(String requestText) {
        this.requestText = requestText;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(String acceptDate) {
        this.acceptDate = acceptDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
