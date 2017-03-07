package com.eeyuva.screens.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Aximsoft on 3/7/2017.
 */

public class CatagoryList {
    @SerializedName("STATUS_CODE")
    @Expose
    private Integer sTATUSCODE;
    @SerializedName("STATUS_INFO")
    @Expose
    private String sTATUSINFO;
    @SerializedName("RESPONSE")
    @Expose
    private List<Catagory> catagoryList = null;

    public List<Catagory> getCatagoryList() {
        return catagoryList;
    }

    public void setCatagoryList(List<Catagory> catagoryList) {
        this.catagoryList = catagoryList;
    }



    public Integer getSTATUSCODE() {
        return sTATUSCODE;
    }

    public void setSTATUSCODE(Integer sTATUSCODE) {
        this.sTATUSCODE = sTATUSCODE;
    }

    public String getSTATUSINFO() {
        return sTATUSINFO;
    }

    public void setSTATUSINFO(String sTATUSINFO) {
        this.sTATUSINFO = sTATUSINFO;
    }


    public class Catagory {

        @SerializedName("categoryid")
        @Expose
        private String categoryid;
        @SerializedName("categoryname")
        @Expose
        private String categoryname;

        public String getCategoryid() {
            return categoryid;
        }

        public void setCategoryid(String categoryid) {
            this.categoryid = categoryid;
        }

        public String getCategoryname() {
            return categoryname;
        }

        public void setCategoryname(String categoryname) {
            this.categoryname = categoryname;
        }

    }

}
