package com.eeyuva.screens.searchpage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hari on 16/09/16.
 */
public class SearchResponse {


    @SerializedName("responseHeader")
    @Expose
    private ResponseHeader responseHeader;
    @SerializedName("response")
    @Expose
    private ListResponse response;

    /**
     *
     * @return
     * The responseHeader
     */
    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    /**
     *
     * @param responseHeader
     * The responseHeader
     */
    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    /**
     *
     * @return
     * The response
     */
    public ListResponse getResponse() {
        return response;
    }

    /**
     *
     * @param response
     * The response
     */
    public void setResponse(ListResponse response) {
        this.response = response;
    }
}
