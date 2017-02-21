package com.eeyuva.screens.profile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hari on 08/10/16.
 */

public class ProfileResponse {
    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }

    public List<ProfileList> getmProfileList() {
        return mProfileList;
    }

    public void setmProfileList(List<ProfileList> mProfileList) {
        this.mProfileList = mProfileList;
    }

    @SerializedName("STATUS_CODE")
    @Expose
    private Integer statusCode;
    @SerializedName("STATUS_INFO")
    @Expose
    private String statusInfo;
    @SerializedName("RESPONSE")
    @Expose
    private List<ProfileList> mProfileList = new ArrayList<ProfileList>();
    private String emailID;

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }
}
