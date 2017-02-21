package com.eeyuva.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.eeyuva.screens.searchpage.SearchActivity;
import com.google.gson.Gson;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by kavi on 19/07/16.
 */
public class Utils {

    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z", Locale.US);
        String d = sdf.format(new Date());
        return d;
    }


    public static void printJson(Object o) {
        try {
            String str = ((new Gson()).toJson(o));
            Log.i("Request", "Request---- > " + str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static void hideSoftKeyBoard(Activity activity) {
        try{
            View view = activity.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }catch (Exception e){

        }
    }
    public static void closeInput(final View caller) {
        caller.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) caller.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(caller.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 100);
    }

    public static void startCallIntent(FragmentActivity activity, String phoneNo) {
        try {
            phoneNo = "+" + formatPhoneNo(phoneNo);
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phoneNo));
            activity.startActivity(callIntent);
        } catch (Exception ex) {
            Log.i("log", "log" + ex.toString());
        }
    }

    public static String formatPhoneNo(String phoneNumber) throws NumberParseException {

        return formatPhoneNo(getCountryISO(phoneNumber), phoneNumber);
    }

    public static String getCountryISO(String phoneNumber) {
        try {
            if (!phoneNumber.contains("+"))
                phoneNumber = "+" + phoneNumber;

            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
            // phone must begin with '+'
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(phoneNumber, "");
            String countryISO = phoneUtil.getRegionCodeForNumber(numberProto);
            return countryISO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String formatPhoneNo(String countryISO, String phoneNumber) {
        try {
            String mCountryID = countryISO.toUpperCase();

            if (mCountryID == null || mCountryID.equals(""))
                return phoneNumber;

            PhoneNumberUtil p = PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber pp;
            pp = p.parse(phoneNumber, mCountryID);
            Log.i("phone", "contact countryISO" + "" + pp.getCountryCode());
            Log.i("phone", "contact countryISO" + "" + pp.getNationalNumber());
            phoneNumber = "" + pp.getCountryCode() + pp.getNationalNumber();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return phoneNumber;
    }

    public static String getISOTime(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                    Locale.ENGLISH);
            Date parsedDate = sdf.parse(date);
//            Sept. 18, 2016, 9:09 a.m
            SimpleDateFormat print = new SimpleDateFormat("MMM. d, yyyy, HH:mm a");
            System.out.println(print.format(parsedDate));
            Log.i("mArticleImgList", "date" + print.format(parsedDate));
            return print.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.i("mArticleImgList", "date" + e);
            return date;
        }
    }
    public static void getSearchQuery(final Context mContext, final EditText editText, Button btnGo, final LinearLayout linearLayout){
        if(linearLayout.isShown()){
            collapse(linearLayout);
        }else{
            expand(linearLayout);
        }
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sKeyword;
                sKeyword = editText.getText().toString().trim();
                if (sKeyword != null && sKeyword.length() != 0) {

                    Intent intent = new Intent(mContext, SearchActivity.class);
                    intent.putExtra("keyword", sKeyword);
                    mContext.startActivity(intent);
                    collapse(linearLayout);

                }
            }
        });

    }
    public static void expand(final View v) {
        v.measure(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? LinearLayoutCompat.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }


}
