package com.eeyuva.screens.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Hari on 10/30/16.
 */
public class ImageFile {

    public String getmImageString() {
        return mImageString;
    }

    public void setmImageString(String mImageString) {
        this.mImageString = mImageString;
    }
    @SerializedName("postpicdata")
    @Expose
    public String mImageString;

    public ImageFile(String encodedString) {
        this.mImageString = encodedString;
    }
}
