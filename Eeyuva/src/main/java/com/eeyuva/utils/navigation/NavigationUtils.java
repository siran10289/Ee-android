package com.eeyuva.utils.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.eeyuva.utils.Constants;

/**
 * Created by kavi on 19/07/16.
 */
public class NavigationUtils {

    public static void navigateToPlayStore(Context context){
        final String appPackageName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public static void startAndFinishActivity(Activity context, Class activity){
        startAndFinishActivity(context,activity,null);
    }

    public static void startAndFinishActivity(Activity context, Class activity, Bundle bundle){
        Intent intent =
                new Intent(context.getApplicationContext(), activity);
        if(bundle!=null)
            intent.putExtra(Constants.BUNDLE,bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        context.finish();
    }


    public static void startActivity(Activity context, Class activity){
       startActivity(context,activity,null);
    }


    public static void startActivity(Activity context, Class activity, Bundle bundle){
        Intent intent =
                new Intent(context.getApplicationContext(), activity);
        if(bundle!=null)
            intent.putExtra(Constants.BUNDLE,bundle);
        context.startActivity(intent);
    }

    public static void startActivityForResult(Activity context, Class activity, Bundle bundle, int requestCode){
        Intent intent =
                new Intent(context.getApplicationContext(), activity);
        if(bundle!=null)
            intent.putExtra(Constants.BUNDLE,bundle);
        context.startActivityForResult(intent,requestCode);
    }


}
