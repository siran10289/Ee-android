package com.eeyuva.screens.profile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Aximsoft on 2/24/2017.
 */

public class NotificationEditResponse {
    @SerializedName("STATUS_CODE")
    @Expose
    private Integer sTATUSCODE;
    @SerializedName("STATUS_INFO")
    @Expose
    private String sTATUSINFO;
    @SerializedName("RESPONSE")
    @Expose
    private String rESPONSE;

    public Integer getSTATUSCODE() {
        return sTATUSCODE;
    }

    public void setSTATUSCODE(Integer sTATUSCODE) {
        this.sTATUSCODE = sTATUSCODE;
    }

    public String getSTATUSINFO() {
        return sTATUSINFO;
    }

    public void setSTATUSINFO(String sTATUSINFO) {
        this.sTATUSINFO = sTATUSINFO;
    }

    public String getRESPONSE() {
        return rESPONSE;
    }

    public void setRESPONSE(String rESPONSE) {
        this.rESPONSE = rESPONSE;
    }

}
