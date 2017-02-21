package com.eeyuva.screens.DetailPage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Hari on 12/31/16.
 */

public class SmallServerResponse {
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

    public String getStatusResponse() {
        return statusResponse;
    }

    public void setStatusResponse(String statusResponse) {
        this.statusResponse = statusResponse;
    }

    @SerializedName("status_code")
    private Integer statusCode;
    @SerializedName("status_info")
    private String statusInfo;
    @SerializedName("response")
    private String statusResponse;
}
