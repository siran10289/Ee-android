package com.eeyuva.screens.navigation;

/**
 * Created by hari on 07/09/16.
 */
public class AppSetting {
    public int icon;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String title;

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public boolean isHeader;

    public AppSetting(String title) {
        this.title = title;
    }

    public AppSetting(String title, int icon,boolean state) {
        this.title = title;
        this.icon = icon;
        this.isHeader=state;
    }
}
