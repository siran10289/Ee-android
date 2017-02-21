package com.eeyuva.screens.DetailPage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hari on 19/09/16.
 */
public class CommentsList {



    @SerializedName("commentid")
    @Expose
    private String commentid;
    @SerializedName("moduleId")
    @Expose
    private String moduleId;
    @SerializedName("entityid")
    @Expose
    private String entityid;
    @SerializedName("commentDescription")
    @Expose
    private String commentDescription;
    @SerializedName("commentby")
    @Expose
    private String commentby;
    @SerializedName("commentDate")
    @Expose
    private String commentDate;
    @SerializedName("moderatedDate")
    @Expose
    private String moderatedDate;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @SerializedName("status")
    @Expose
    private String status;

    /**
     *
     * @return
     * The commentid
     */
    public String getCommentid() {
        return commentid;
    }

    /**
     *
     * @param commentid
     * The commentid
     */
    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }

    /**
     *
     * @return
     * The moduleId
     */
    public String getModuleId() {
        return moduleId;
    }

    /**
     *
     * @param moduleId
     * The moduleId
     */
    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

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
     * The commentDescription
     */
    public String getCommentDescription() {
        return commentDescription;
    }

    /**
     *
     * @param commentDescription
     * The commentDescription
     */
    public void setCommentDescription(String commentDescription) {
        this.commentDescription = commentDescription;
    }

    /**
     *
     * @return
     * The commentby
     */
    public String getCommentby() {
        return commentby;
    }

    /**
     *
     * @param commentby
     * The commentby
     */
    public void setCommentby(String commentby) {
        this.commentby = commentby;
    }

    /**
     *
     * @return
     * The commentDate
     */
    public String getCommentDate() {
        return commentDate;
    }

    /**
     *
     * @param commentDate
     * The commentDate
     */
    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    /**
     *
     * @return
     * The moderatedDate
     */
    public String getModeratedDate() {
        return moderatedDate;
    }

    /**
     *
     * @param moderatedDate
     * The moderatedDate
     */
    public void setModeratedDate(String moderatedDate) {
        this.moderatedDate = moderatedDate;
    }
}
