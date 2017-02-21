package com.eeyuva.screens.profile.userdetails;

/**
 * Created by hari on 08/10/16.
 */
public interface IFragmentToActivity {
    void showToast(String msg);

    void communicateToFragment2();

    void communicateToStuffsActivity(String moduleid, String artid);
}
