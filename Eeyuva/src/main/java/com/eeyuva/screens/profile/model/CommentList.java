package com.eeyuva.screens.profile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hari on 08/10/16.
 */
public class CommentList {

    @SerializedName("artid")
    @Expose
    private String artid;
    @SerializedName("artitle")
    @Expose
    private String artitle;
    @SerializedName("artimg")
    @Expose
    private String artimg;

    public String getArtid() {
        return artid;
    }

    public void setArtid(String artid) {
        this.artid = artid;
    }

    public String getArtitle() {
        return artitle;
    }

    public void setArtitle(String artitle) {
        this.artitle = artitle;
    }

    public String getArtimg() {
        return artimg;
    }

    public void setArtimg(String artimg) {
        this.artimg = artimg;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getModulename() {
        return modulename;
    }

    public void setModulename(String modulename) {
        this.modulename = modulename;
    }

    public String getCommentdate() {
        return commentdate;
    }

    public void setCommentdate(String commentdate) {
        this.commentdate = commentdate;
    }

    public String getCommentid() {
        return commentid;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }

    public String getModuleid() {
        return moduleid;
    }

    public void setModuleid(String moduleid) {
        this.moduleid = moduleid;
    }

    @SerializedName("comment")
    @Expose

    private String comment;
    @SerializedName("modulename")
    @Expose
    private String modulename;
    @SerializedName("commentdate")
    @Expose
    private String commentdate;
    @SerializedName("commentid")
    @Expose
    private String commentid;
    @SerializedName("moduleid")
    @Expose
    private String moduleid;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @SerializedName("status")
    @Expose
    private String status;
}
