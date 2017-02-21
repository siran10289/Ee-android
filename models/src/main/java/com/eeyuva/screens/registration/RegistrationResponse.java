package com.eeyuva.screens.registration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hari on 06/09/16.
 */
public class RegistrationResponse {

    @SerializedName("status_code")
    @Expose
    private String statusCode;
    @SerializedName("status_info")
    @Expose
    private String statusInfo;
    @SerializedName("userid")
    @Expose
    private Integer userid;

    /**
     *
     * @return
     * The statusCode
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     *
     * @param statusCode
     * The status_code
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     *
     * @return
     * The statusInfo
     */
    public String getStatusInfo() {
        return statusInfo;
    }

    /**
     *
     * @param statusInfo
     * The status_info
     */
    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }

    /**
     *
     * @return
     * The userid
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     *
     * @param userid
     * The userid
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
