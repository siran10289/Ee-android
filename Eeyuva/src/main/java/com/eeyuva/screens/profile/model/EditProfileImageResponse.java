package com.eeyuva.screens.profile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Aximsoft on 3/9/2017.
 */

public class EditProfileImageResponse {
    @SerializedName("STATUS_CODE")
    @Expose
    private Integer sTATUSCODE;
    @SerializedName("STATUS_INFO")
    @Expose
    private String sTATUSINFO;
    @SerializedName("RESPONSE")
    @Expose
    private List<String> rESPONSE = null;

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

    public List<String> getRESPONSE() {
        return rESPONSE;
    }

    public void setRESPONSE(List<String> rESPONSE) {
        this.rESPONSE = rESPONSE;
    }
}
