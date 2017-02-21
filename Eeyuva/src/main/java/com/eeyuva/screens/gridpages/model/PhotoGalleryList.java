package com.eeyuva.screens.gridpages.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hari on 18/09/16.
 */
public class PhotoGalleryList {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("picpath")
    @Expose
    private String picpath;

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The picpath
     */
    public String getPicpath() {
        return picpath;
    }

    /**
     * @param picpath The picpath
     */
    public void setPicpath(String picpath) {
        this.picpath = picpath;
    }

}
