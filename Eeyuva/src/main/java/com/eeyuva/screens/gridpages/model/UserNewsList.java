package com.eeyuva.screens.gridpages.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hari on 18/09/16.
 */
public class UserNewsList {


    @SerializedName("picpath")
    @Expose
    private String picpath;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("moduleid")
    @Expose
    private String moduleid;
    @SerializedName("articleid")
    @Expose
    private String articleid;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("modulename")
    @Expose
    private String modulename;
    @SerializedName("entityId")
    @Expose
    private String entityId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("user_pic")
    @Expose
    private String userPic;

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
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

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
     * The articleid
     */
    public String getArticleid() {
        return articleid;
    }

    /**
     *
     * @param articleid
     * The articleid
     */
    public void setArticleid(String articleid) {
        this.articleid = articleid;
    }

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The date
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * @param date
     * The date
     */
    public void setDate(String date) {
        this.date = date;
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

    /**
     *
     * @return
     * The entityId
     */
    public String getEntityId() {
        return entityId;
    }

    /**
     *
     * @param entityId
     * The entityId
     */
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    /**
     *
     * @return
     * The username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     * The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     * The userPic
     */
    public String getUserPic() {
        return userPic;
    }

    /**
     *
     * @param userPic
     * The user_pic
     */
    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }


}
