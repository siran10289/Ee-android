package com.eeyuva.screens.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Hari on 10/30/16.
 */

public class ImageResponse {

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

    @SerializedName("STATUS_CODE")
    @Expose
    private Integer statusCode;
    @SerializedName("STATUS_INFO")
    @Expose
    private String statusInfo;
    @SerializedName("RESPONSE")
    @Expose
    private String statusResponse;
    /*@SerializedName("RESPONSE")
    @Expose
    private List<RESPONSE> rESPONSE = null;

    public class RESPONSE {

        @SerializedName("categoryid")
        @Expose
        private String categoryid;
        @SerializedName("categoryname")
        @Expose
        private String categoryname;

        public String getCategoryid() {
            return categoryid;
        }

        public void setCategoryid(String categoryid) {
            this.categoryid = categoryid;
        }

        public String getCategoryname() {
            return categoryname;
        }

        public void setCategoryname(String categoryname) {
            this.categoryname = categoryname;
        }

    }*/

}
