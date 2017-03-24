package com.eeyuva.screens.DetailPage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hari on 14/09/16.
 */
public class ArticleDetail {

    @SerializedName("seqid")
    @Expose
    private String seqid;
    @SerializedName("articleid")
    @Expose
    private String articleid;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("createdby")
    @Expose
    private String createdby;
    @SerializedName("createddate")
    @Expose
    private String createddate;
    @SerializedName("golivestatus")
    @Expose
    private String golivestatus;
    @SerializedName("livedate")
    @Expose
    private String livedate;
    @SerializedName("picpath")
    @Expose
    private String picpath;
    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("innerpic")
    @Expose
    private String innerpic;
    @SerializedName("thumbimg")
    @Expose
    private String thumbimg;
    @SerializedName("galleryimg")
    @Expose
    private String galleryimg;

    @SerializedName("modulename")
    private String modulename;

    @SerializedName("categoryid")
    private String categoryid;
    @SerializedName("likecount")
    private String likecount;

    @SerializedName("commentcount")
    @Expose
    private Integer commentcount;
    @SerializedName("like_status")
    @Expose
    private Integer likeStatus;
    @SerializedName("dislike_status")
    @Expose
    private Integer dislikeStatus;
    @SerializedName("share_link")
    @Expose
    private String shareLink;
    @SerializedName("videopath")
    @Expose
    private String videopath;


    public String getDislikecount() {
        return dislikecount;
    }

    public void setDislikecount(String dislikecount) {
        this.dislikecount = dislikecount;
    }

    public String getLikecount() {
        return likecount;
    }

    public void setLikecount(String likecount) {
        this.likecount = likecount;
    }

    @SerializedName("dislikecount")
    private String dislikecount;

    public String getModulename() {
        return modulename;
    }

    public void setModulename(String modulename) {
        this.modulename = modulename;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    /**
     * @return The seqid
     */
    public String getSeqid() {
        return seqid;
    }

    /**
     * @param seqid The seqid
     */
    public void setSeqid(String seqid) {
        this.seqid = seqid;
    }

    /**
     * @return The articleid
     */
    public String getArticleid() {
        return articleid;
    }

    /**
     * @param articleid The articleid
     */
    public void setArticleid(String articleid) {
        this.articleid = articleid;
    }

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
     * @return The summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @param summary The summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return The createdby
     */
    public String getCreatedby() {
        return createdby;
    }

    /**
     * @param createdby The createdby
     */
    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    /**
     * @return The createddate
     */
    public String getCreateddate() {
        return createddate;
    }

    /**
     * @param createddate The createddate
     */
    public void setCreateddate(String createddate) {
        this.createddate = createddate;
    }

    /**
     * @return The golivestatus
     */
    public String getGolivestatus() {
        return golivestatus;
    }

    /**
     * @param golivestatus The golivestatus
     */
    public void setGolivestatus(String golivestatus) {
        this.golivestatus = golivestatus;
    }

    /**
     * @return The livedate
     */
    public String getLivedate() {
        return livedate;
    }

    /**
     * @param livedate The livedate
     */
    public void setLivedate(String livedate) {
        this.livedate = livedate;
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

    /**
     * @return The tags
     */
    public String getTags() {
        return tags;
    }

    /**
     * @param tags The tags
     */
    public void setTags(String tags) {
        this.tags = tags;
    }

    /**
     * @return The innerpic
     */
    public String getInnerpic() {
        return innerpic;
    }

    /**
     * @param innerpic The innerpic
     */
    public void setInnerpic(String innerpic) {
        this.innerpic = innerpic;
    }

    /**
     * @return The thumbimg
     */
    public String getThumbimg() {
        return thumbimg;
    }

    /**
     * @param thumbimg The thumbimg
     */
    public void setThumbimg(String thumbimg) {
        this.thumbimg = thumbimg;
    }

    /**
     * @return The galleryimg
     */
    public String getGalleryimg() {
        return galleryimg;
    }

    /**
     * @param galleryimg The galleryimg
     */
    public void setGalleryimg(String galleryimg) {
        this.galleryimg = galleryimg;
    }
    public Integer getCommentcount() {
        return commentcount;
    }

    public void setCommentcount(Integer commentcount) {
        this.commentcount = commentcount;
    }

    public Integer getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(Integer likeStatus) {
        this.likeStatus = likeStatus;
    }

    public Integer getDislikeStatus() {
        return dislikeStatus;
    }

    public void setDislikeStatus(Integer dislikeStatus) {
        this.dislikeStatus = dislikeStatus;
    }

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
    }
    public String getVideopath() {
        return videopath;
    }

    public void setVideopath(String videopath) {
        this.videopath = videopath;
    }


}