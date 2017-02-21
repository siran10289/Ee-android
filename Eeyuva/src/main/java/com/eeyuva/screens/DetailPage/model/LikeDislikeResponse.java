package com.eeyuva.screens.DetailPage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hari on 19/09/16.
 */
public class LikeDislikeResponse {

    @SerializedName("status_code")
    @Expose
    private Integer statusCode;
    @SerializedName("status_info")
    @Expose
    private String statusInfo;
    @SerializedName("count_like")
    @Expose
    private Integer countLike;
    @SerializedName("type")
    @Expose
    private String type;

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
     * The countLike
     */
    public Integer getCountLike() {
        return countLike;
    }

    /**
     *
     * @param countLike
     * The count_like
     */
    public void setCountLike(Integer countLike) {
        this.countLike = countLike;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }
}
