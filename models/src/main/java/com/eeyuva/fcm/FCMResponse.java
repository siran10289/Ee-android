
package com.eeyuva.fcm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class FCMResponse implements Serializable {

    @SerializedName("notification")
    @Expose
    private Notification notification;
    @SerializedName("data")
    @Expose
    private Data data;

    /**
     * 
     * @return
     *     The notification
     */
    public Notification getNotification() {
        return notification;
    }

    /**
     * 
     * @param notification
     *     The notification
     */
    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    /**
     * 
     * @return
     *     The data
     */
    public Data getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(Data data) {
        this.data = data;
    }

}
