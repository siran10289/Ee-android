package com.eeyuva.screens.navigation;

import android.app.Activity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.eeyuva.R;


/**
 */
public class EeyuvaActionBarDrawerToggle extends ActionBarDrawerToggle {

    public EeyuvaActionBarDrawerToggle(Activity activity, final DrawerLayout drawerLayout, Toolbar toolbar, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
        super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);

        setHomeAsUpIndicator(R.drawable.ic_three_line_image);
        setDrawerIndicatorEnabled(false);

        setToolbarNavigationClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
}
