package com.eeyuva.screens.profile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hari on 08/10/16.
 */

public class AlertResponse {

    @SerializedName("STATUS_CODE")
    @Expose
    private Integer statusCode;

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

    public List<AlertList> getAlertList() {
        return alertList;
    }

    public void setAlertList(List<AlertList> alertList) {
        this.alertList = alertList;
    }

    @SerializedName("STATUS_INFO")
    @Expose
    private String statusInfo;
    @SerializedName("RESPONSE")
    @Expose
    private List<AlertList> alertList = new ArrayList<AlertList>();


}
