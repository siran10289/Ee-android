package com.eeyuva.utils.customdialog;

import android.app.*;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.eeyuva.R;


/**
 * Created by hari on 4/7/16.
 */
public class DialogUtils {

    private static AlertDialog mDialog;


    private static CustomProgressDialog sLoadingDialog;

    public static void setLoadingDialog(Context context, String message) {
        sLoadingDialog = CustomProgressDialog.getInstance(context);
        sLoadingDialog.show();
    }

    public static void dismissLoadingDialog() {
        if (sLoadingDialog != null) {
            try {
                if (sLoadingDialog.isShowing())
                    sLoadingDialog.dismiss();
            } catch (IllegalArgumentException iae) {
            }
            sLoadingDialog = null;
        }
    }

    public static void showDialog(Context context, String message, String txtPositive, String txtNegative, final DialogListener dialogListener) {
        showDialog(context, "", message, txtPositive, txtNegative, dialogListener,true);
    }

    public static void showDialog(Context context, String message, String txtNegative, final DialogListener dialogListener) {
        showDialog(context, "", message, "", txtNegative, dialogListener,true);
    }

    public static void showDialog(Context context, String message, String txtPositive ,final DialogListener dialogListener, boolean cancelable) {
        showDialog(context, "", message, txtPositive,"", dialogListener,cancelable);
    }

    public static void showDialog(Context context,String title, String message, String txtPositive ,final DialogListener dialogListener, boolean cancelable) {
        showDialog(context, title, message, txtPositive,"", dialogListener,cancelable);
    }



    public static void showDialog(Context context, String title, String message, String txtPositive, String txtNegative, final DialogListener dialogListener,boolean cancelable) {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
                //return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View dialogView = LayoutInflater.from(context).inflate(R.layout.view_custom_dialog, null);
            builder.setView(dialogView);
            TextView mTxtTitle = (TextView) dialogView.findViewById(R.id.mTxtTitle);
            TextView mTxtMessage = (TextView) dialogView.findViewById(R.id.mTxtMessage);
            TextView mTxtPositive = (TextView) dialogView.findViewById(R.id.mTxtPositive);
            TextView mTxtNegative = (TextView) dialogView.findViewById(R.id.mTxtNegative);

            if (title != null && title.length() != 0) {
                mTxtTitle.setText(title);
                mTxtTitle.setVisibility(View.VISIBLE);
            }
            if (txtPositive != null && txtPositive.length() != 0) {
                mTxtPositive.setText(txtPositive);
                mTxtPositive.setVisibility(View.VISIBLE);
            }
            if (message != null && message.length() != 0) {
                mTxtMessage.setText(Html.fromHtml(message));
                mTxtMessage.setVisibility(View.VISIBLE);
            }
            mTxtPositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialogListener != null)
                        dialogListener.onConfirm();
                    mDialog.dismiss();
                    return;
                }
            });

            if (txtNegative != null) {
                mTxtNegative.setVisibility(View.VISIBLE);
                mTxtNegative.setText(txtNegative);
                mTxtNegative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialogListener != null)
                            dialogListener.onCancel();
                        mDialog.dismiss();
                        return;
                    }
                });
            }
            mDialog = builder.create();
            mDialog.setCancelable(cancelable);
            mDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
