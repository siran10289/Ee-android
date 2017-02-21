package com.eeyuva.screens.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hari on 06/09/16.
 */
public class GetArticleResponse {

    @SerializedName("STATUS_CODE")
    @Expose
    private Integer statusCode;
    @SerializedName("STATUS_INFO")
    @Expose
    private String statusInfo;
    @SerializedName("TOTAL_RECORDS")
    @Expose
    private Integer totalRecord;
    @SerializedName("RESPONSE")
    @Expose
    private List<ResponseItem> responseItem = new ArrayList<ResponseItem>();
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
     * The totalRecord
     */
    public Integer getTotalRecord() {
        return totalRecord;
    }

    /**
     *
     * @param totalRecord
     * The total_record
     */
    public void setTotalRecord(Integer totalRecord) {
        this.totalRecord = totalRecord;
    }

    /**
     *
     * @return
     * The responseItem
     */
    public List<ResponseItem> getResponseItem() {
        return responseItem;
    }

    /**
     *
     * @param responseItem
     * The responseItem
     */
    public void setResponseItem(List<ResponseItem> responseItem) {
        this.responseItem = responseItem;
    }
}
