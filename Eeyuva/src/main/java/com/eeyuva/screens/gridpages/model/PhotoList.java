package com.eeyuva.screens.gridpages.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hari on 18/09/16.
 */
public class PhotoList {

    @SerializedName("trid")
    @Expose
    private String trid;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("gallerypic")
    @Expose
    private String gallerypic;
    @SerializedName("count")
    @Expose
    private Integer count;

    /**
     *
     * @return
     * The trid
     */
    public String getTrid() {
        return trid;
    }

    /**
     *
     * @param trid
     * The trid
     */
    public void setTrid(String trid) {
        this.trid = trid;
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
     * The gallerypic
     */
    public String getGallerypic() {
        return gallerypic;
    }

    /**
     *
     * @param gallerypic
     * The gallerypic
     */
    public void setGallerypic(String gallerypic) {
        this.gallerypic = gallerypic;
    }

    /**
     *
     * @return
     * The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     *
     * @param count
     * The count
     */
    public void setCount(Integer count) {
        this.count = count;
    }
}
