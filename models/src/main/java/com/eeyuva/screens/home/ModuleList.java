package com.eeyuva.screens.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hari on 09/09/16.
 */
public class ModuleList {

    @SerializedName("entityid")
    @Expose
    private String entityid;
    @SerializedName("picpath")
    @Expose
    private String picpath;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("modid")
    @Expose
    private String modid;
    @SerializedName("modulename")
    @Expose
    private String modulename;

    /**
     *
     * @return
     * The entityid
     */
    public String getEntityid() {
        return entityid;
    }

    /**
     *
     * @param entityid
     * The entityid
     */
    public void setEntityid(String entityid) {
        this.entityid = entityid;
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

    /**
     *
     * @return
     * The modid
     */
    public String getModid() {
        return modid;
    }

    /**
     *
     * @param modid
     * The modid
     */
    public void setModid(String modid) {
        this.modid = modid;
    }

    /**
     *
     * @return
     * The modulename
     */
    public String getModulename() {
        return modulename;
    }

    /**
     *
     * @param modulename
     * The modulename
     */
    public void setModulename(String modulename) {
        this.modulename = modulename;
    }
}
