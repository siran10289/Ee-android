package com.eeyuva.screens.searchpage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hari on 16/09/16.
 */
public class ListResponse {

    @SerializedName("numFound")
    @Expose
    private Integer numFound;
    @SerializedName("start")
    @Expose
    private Integer start;
    @SerializedName("docs")
    @Expose
    private List<Doc> docs = new ArrayList<Doc>();

    /**
     *
     * @return
     * The numFound
     */
    public Integer getNumFound() {
        return numFound;
    }

    /**
     *
     * @param numFound
     * The numFound
     */
    public void setNumFound(Integer numFound) {
        this.numFound = numFound;
    }

    /**
     *
     * @return
     * The start
     */
    public Integer getStart() {
        return start;
    }

    /**
     *
     * @param start
     * The start
     */
    public void setStart(Integer start) {
        this.start = start;
    }

    /**
     *
     * @return
     * The docs
     */
    public List<Doc> getDocs() {
        return docs;
    }

    /**
     *
     * @param docs
     * The docs
     */
    public void setDocs(List<Doc> docs) {
        this.docs = docs;
    }


}
