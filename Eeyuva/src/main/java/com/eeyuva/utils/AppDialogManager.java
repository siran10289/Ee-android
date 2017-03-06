package com.eeyuva.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eeyuva.R;
import com.eeyuva.screens.home.HomeActivity;
import com.eeyuva.screens.home.ResponseList;
import com.eeyuva.screens.home.loadmore.RoundedTransformation;
import com.eeyuva.utils.commonAdapters.MoudleListAdapter;
import com.eeyuva.utils.listeners.DialogListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AppDialogManager {

    public static void ModuleChooserDialog(final Activity context, Object object, List<ResponseList> alModules) {
        final DialogListener mCallback;
        LinearLayoutManager linearLayoutManager;
        RecyclerView recyclerView;
        MoudleListAdapter adapter;
        LinearLayout llcancel;
        final Dialog dialogAddModule;
        try {
            mCallback = (DialogListener) object;
        } catch (ClassCastException e) {
            throw new ClassCastException(object.toString() + " must implement DialogListener");
        }
        dialogAddModule = new Dialog(context);
        dialogAddModule.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogAddModule.setContentView(R.layout.dialog_select_module);
        dialogAddModule.setCanceledOnTouchOutside(true);
        dialogAddModule.setCancelable(true);
        dialogAddModule.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        //dialogAddModule.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        llcancel = (LinearLayout) dialogAddModule.findViewById(R.id.llcancel);
        recyclerView = (RecyclerView) dialogAddModule.findViewById(R.id.rv_modules);
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MoudleListAdapter(context, alModules,dialogAddModule,mCallback);
        recyclerView.setAdapter(adapter);
        llcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Event:","clicked");
                if(dialogAddModule!=null) {
                    Log.e("Event:","dialog not null");
                    dialogAddModule.dismiss();
                }else{
                    Log.e("Event:","dialog null");
                }
            }
        });
        dialogAddModule.show();
    }
    public static void ModuleChooserDialog1(final Activity activity, Object object, List<ResponseList> alModules) {
         AlertDialog mDialog = null;
         LinearLayout llcancel;
         RecyclerView recyclerView;
         LinearLayoutManager linearLayoutManager;
         MoudleListAdapter adapter;
        final DialogListener mCallback;
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            mCallback = (DialogListener) object;
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            View dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_select_module, null);
            builder.setView(dialogView);

            llcancel = (LinearLayout) dialogView.findViewById(R.id.llcancel);
            recyclerView = (RecyclerView) dialogView.findViewById(R.id.rv_modules);
            linearLayoutManager = new LinearLayoutManager(activity);
            recyclerView.setLayoutManager(linearLayoutManager);
            adapter = new MoudleListAdapter(activity, alModules,mDialog,mCallback);
            recyclerView.setAdapter(adapter);
            mDialog = builder.create();
            final AlertDialog finalMDialog = mDialog;
            llcancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(finalMDialog !=null) {
                        finalMDialog.dismiss();
                    }
                }
            });

            mDialog.setCancelable(true);
            mDialog.show();
            mDialog.getWindow().setGravity(Gravity.TOP);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Window window = mDialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.verticalMargin = .1f;
            window.setAttributes(wlp);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }




}
