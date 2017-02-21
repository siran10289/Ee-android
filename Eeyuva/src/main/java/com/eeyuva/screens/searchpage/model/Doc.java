package com.eeyuva.screens.searchpage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hari on 16/09/16.
 */
public class Doc {

    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("title")
    @Expose
    private List<String> title = new ArrayList<String>();
    @SerializedName("imgpath")
    @Expose
    private String imgpath;
    @SerializedName("keywords")
    @Expose
    private String keywords;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("entityid")
    @Expose
    private String entityid;
    @SerializedName("seqid")
    @Expose
    private Integer seqid;
    @SerializedName("movieId")
    @Expose
    private String movieId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("modid")
    @Expose
    private Integer modid;
    @SerializedName("_version_")
    @Expose
    private String version;

    public boolean isLoadtype() {
        return loadtype;
    }

    public void setLoadtype(boolean loadtype) {
        this.loadtype = loadtype;
    }

    private boolean loadtype;

    public Doc(boolean b) {
        this.loadtype=b;

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
     * @return The title
     */
    public List<String> getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(List<String> title) {
        this.title = title;
    }

    /**
     * @return The imgpath
     */
    public String getImgpath() {
        return imgpath;
    }

    /**
     * @param imgpath The imgpath
     */
    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    /**
     * @return The keywords
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     * @param keywords The keywords
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The entityid
     */
    public String getEntityid() {
        return entityid;
    }

    /**
     * @param entityid The entityid
     */
    public void setEntityid(String entityid) {
        this.entityid = entityid;
    }

    /**
     * @return The seqid
     */
    public Integer getSeqid() {
        return seqid;
    }

    /**
     * @param seqid The seqid
     */
    public void setSeqid(Integer seqid) {
        this.seqid = seqid;
    }

    /**
     * @return The movieId
     */
    public String getMovieId() {
        return movieId;
    }

    /**
     * @param movieId The movieId
     */
    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    /**
     * @return The date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return The modid
     */
    public Integer getModid() {
        return modid;
    }

    /**
     * @param modid The modid
     */
    public void setModid(Integer modid) {
        this.modid = modid;
    }

    /**
     * @return The version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version The _version_
     */
    public void setVersion(String version) {
        this.version = version;
    }
}
