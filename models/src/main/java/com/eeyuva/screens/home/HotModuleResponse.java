package com.eeyuva.screens.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hari on 09/09/16.
 */
public class HotModuleResponse {

    @SerializedName("STATUS_CODE")
    @Expose
    private Integer statusCode;
    @SerializedName("STATUS_INFO")
    @Expose
    private String statusInfo;
    @SerializedName("RESPONSE")
    @Expose
    private List<ModuleList> moduleList = new ArrayList<ModuleList>();

    /**
     *
     * @return
     * The statusCode
     */
    public Integer getStatusCode() {
        return statusCode;
    }

    /**
     *
     * @param statusCode
     * The status_code
     */
    public void setStatusCode(Integer statusCode) {
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
     * The moduleList
     */
    public List<ModuleList> getModuleList() {
        return moduleList;
    }

    /**
     *
     * @param moduleList
     * The moduleList
     */
    public void setModuleList(List<ModuleList> moduleList) {
        this.moduleList = moduleList;
    }
}
