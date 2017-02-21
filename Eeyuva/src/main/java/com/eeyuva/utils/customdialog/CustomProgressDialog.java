package com.eeyuva.utils.customdialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.eeyuva.R;

/**
 * Created by Mohan Raj S on 1/2/16.
 */
public class CustomProgressDialog extends android.app.ProgressDialog implements Animation.AnimationListener {
    Context context;
    private Animation zoomInAnimation;
    ImageView loaderImageView;

    public static CustomProgressDialog getInstance(Context context) {
        CustomProgressDialog ProgressDialog = new CustomProgressDialog(context, com.eeyuva.R.style.progress_style);
        ProgressDialog.setIndeterminate(true);
        ProgressDialog.setCancelable(false);
        return ProgressDialog;
    }

    public CustomProgressDialog(Context context) {
        super(context);
        this.context = context;
    }

    CustomProgressDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.eeyuva.R.layout.view_custom_progress_dialog);
//        loaderImageView = (ImageView) findViewById(R.id.loaderImageView);
//        zoomInAnimation = AnimationUtils.loadAnimation(context, com.eeyuva.R.anim.zoom_in);
//        zoomInAnimation.setAnimationListener(this);
//        zoomInAnimation.setRepeatCount(-1);
//        zoomInAnimation.setRepeatMode(Animation.INFINITE);
        getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        getWindow().setDimAmount(0.0f);

//        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    @Override
    public void show() {
        super.show();
//        loaderImageView.setAnimation(zoomInAnimation);
    }

    @Override
    public void dismiss() {
        super.dismiss();
//        loaderImageView.clearAnimation();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
//        loaderImageView.startAnimation(zoomInAnimation);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}