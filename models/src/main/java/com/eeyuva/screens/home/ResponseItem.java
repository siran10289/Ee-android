package com.eeyuva.screens.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hari on 06/09/16.
 */
public class ResponseItem {

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
    @SerializedName("galleryimg")
    @Expose
    private String galleryimg;
    @SerializedName("thumbimg")
    @Expose
    private String thumbimg;
    @SerializedName("innerpic")
    @Expose
    private String innerpic;

    public boolean getLoadtype() {
        return loadtype;
    }

    public void setLoadtype(boolean loadtype) {
        this.loadtype = loadtype;
    }

    private boolean loadtype;

    public ResponseItem(boolean load) {
        this.loadtype=load;
    }

    /**
     *
     * @return
     * The seqid
     */
    public String getSeqid() {
        return seqid;
    }

    /**
     *
     * @param seqid
     * The seqid
     */
    public void setSeqid(String seqid) {
        this.seqid = seqid;
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
     * The summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     *
     * @param summary
     * The summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
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
     * The createdby
     */
    public String getCreatedby() {
        return createdby;
    }

    /**
     *
     * @param createdby
     * The createdby
     */
    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    /**
     *
     * @return
     * The createddate
     */
    public String getCreateddate() {
        return createddate;
    }

    /**
     *
     * @param createddate
     * The createddate
     */
    public void setCreateddate(String createddate) {
        this.createddate = createddate;
    }

    /**
     *
     * @return
     * The golivestatus
     */
    public String getGolivestatus() {
        return golivestatus;
    }

    /**
     *
     * @param golivestatus
     * The golivestatus
     */
    public void setGolivestatus(String golivestatus) {
        this.golivestatus = golivestatus;
    }

    /**
     *
     * @return
     * The livedate
     */
    public String getLivedate() {
        return livedate;
    }

    /**
     *
     * @param livedate
     * The livedate
     */
    public void setLivedate(String livedate) {
        this.livedate = livedate;
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
     * The galleryimg
     */
    public String getGalleryimg() {
        return galleryimg;
    }

    /**
     *
     * @param galleryimg
     * The galleryimg
     */
    public void setGalleryimg(String galleryimg) {
        this.galleryimg = galleryimg;
    }

    /**
     *
     * @return
     * The thumbimg
     */
    public String getThumbimg() {
        return thumbimg;
    }

    /**
     *
     * @param thumbimg
     * The thumbimg
     */
    public void setThumbimg(String thumbimg) {
        this.thumbimg = thumbimg;
    }

    /**
     *
     * @return
     * The innerpic
     */
    public String getInnerpic() {
        return innerpic;
    }

    /**
     *
     * @param innerpic
     * The innerpic
     */
    public void setInnerpic(String innerpic) {
        this.innerpic = innerpic;
    }

}
