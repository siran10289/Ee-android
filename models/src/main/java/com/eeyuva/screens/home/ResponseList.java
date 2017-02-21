package com.eeyuva.screens.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hari on 06/09/16.
 */
public class ResponseList {

    @SerializedName("moduleid")
    @Expose
    private String moduleid;
    @SerializedName("orderid")
    @Expose
    private String orderid;
    @SerializedName("title")
    @Expose
    private String title;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    private boolean selected;


    /**
     *
     * @return
     * The moduleid
     */
    public String getModuleid() {
        return moduleid;
    }

    /**
     *
     * @param moduleid
     * The moduleid
     */
    public void setModuleid(String moduleid) {
        this.moduleid = moduleid;
    }

    /**
     *
     * @return
     * The orderid
     */
    public String getOrderid() {
        return orderid;
    }

    /**
     *
     * @param orderid
     * The orderid
     */
    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
