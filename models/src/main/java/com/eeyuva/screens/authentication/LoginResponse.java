package com.eeyuva.screens.authentication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hari on 05/09/16.
 */
public class LoginResponse {

    @SerializedName("useremail")
    @Expose
    private String useremail;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("status_code")
    @Expose
    private String statusCode;
    @SerializedName("userid")
    @Expose
    private Integer userid;
    @SerializedName("picpath")
    @Expose
    private String picpath;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("status_info")
    @Expose
    private String statusInfo;

    /**
     *
     * @return
     * The useremail
     */
    public String getUseremail() {
        return useremail;
    }

    /**
     *
     * @param useremail
     * The useremail
     */
    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    /**
     *
     * @return
     * The firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     *
     * @param firstname
     * The firstname
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

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

    /**
     *
     * @return
     * The picpath
     */
    public String getPicpath() {
        return picpath;
    }

    /**
     *
     * @param picpath
     * The picpath
     */
    public void setPicpath(String picpath) {
        this.picpath = picpath;
    }

    /**
     *
     * @return
     * The lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     *
     * @param lastname
     * The lastname
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
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

}
