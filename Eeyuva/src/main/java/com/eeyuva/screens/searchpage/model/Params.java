package com.eeyuva.screens.searchpage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hari on 16/09/16.
 */
public class Params {

    @SerializedName("sort")
    @Expose
    private String sort;
    @SerializedName("wt")
    @Expose
    private String wt;
    @SerializedName("start")
    @Expose
    private String start;
    @SerializedName("q")
    @Expose
    private String q;
    @SerializedName("rows")
    @Expose
    private String rows;

    /**
     *
     * @return
     * The sort
     */
    public String getSort() {
        return sort;
    }

    /**
     *
     * @param sort
     * The sort
     */
    public void setSort(String sort) {
        this.sort = sort;
    }

    /**
     *
     * @return
     * The wt
     */
    public String getWt() {
        return wt;
    }

    /**
     *
     * @param wt
     * The wt
     */
    public void setWt(String wt) {
        this.wt = wt;
    }

    /**
     *
     * @return
     * The start
     */
    public String getStart() {
        return start;
    }

    /**
     *
     * @param start
     * The start
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     *
     * @return
     * The q
     */
    public String getQ() {
        return q;
    }

    /**
     *
     * @param q
     * The q
     */
    public void setQ(String q) {
        this.q = q;
    }

    /**
     *
     * @return
     * The rows
     */
    public String getRows() {
        return rows;
    }

    /**
     *
     * @param rows
     * The rows
     */
    public void setRows(String rows) {
        this.rows = rows;
    }

}
