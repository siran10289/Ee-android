package com.eeyuva.screens.profile.notification;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eeyuva.ButterAppCompatActivity;
import com.eeyuva.R;
import com.eeyuva.screens.DetailPage.model.CommentsList;
import com.eeyuva.screens.authentication.LoginActivity;
import com.eeyuva.screens.gridpages.GridHomeActivity;
import com.eeyuva.screens.home.CatagoryList;
import com.eeyuva.screens.home.HomeActivity;
import com.eeyuva.screens.home.ResponseList;
import com.eeyuva.screens.home.loadmore.RoundedTransformation;
import com.eeyuva.screens.navigation.FragmentDrawer;
import com.eeyuva.screens.profile.DaggerProfileComponent;
import com.eeyuva.screens.profile.ProfileComponent;
import com.eeyuva.screens.profile.ProfileContract;
import com.eeyuva.screens.profile.ProfileModule;
import com.eeyuva.screens.profile.alerts.AlertActivity;
import com.eeyuva.screens.profile.alerts.AlertAdapter;
import com.eeyuva.screens.profile.model.AlertList;
import com.eeyuva.screens.profile.model.CommentResponse;
import com.eeyuva.screens.profile.model.NewsResponse;
import com.eeyuva.screens.profile.model.NotificationResponse;
import com.eeyuva.screens.profile.model.ProfileResponse;
import com.eeyuva.screens.searchpage.SearchActivity;
import com.eeyuva.utils.AppDialogManager;
import com.eeyuva.utils.Constants;
import com.eeyuva.utils.Utils;
import com.eeyuva.utils.customdialog.DialogUtils;
import com.eeyuva.utils.listeners.DialogListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

import static com.eeyuva.screens.home.HomeActivity.mModuleList;

/**
 * Created by hari on 01/10/16.
 */

public class NotificationActivity extends ButterAppCompatActivity implements ProfileContract.View, ProfileContract.AdapterCallBack,DialogListener {

    private static Dialog dialog;
    @Inject
    ProfileContract.Presenter mPresenter;

    ProfileComponent mComponent;

    @Bind(R.id.tool_bar)
    Toolbar mToolbar;

    @Bind(R.id.imgHome)
    ImageView imgHome;
    @Bind(R.id.imgList)
    ImageView imgList;
    @Bind(R.id.imgPhoto)
    ImageView imgPhoto;
    @Bind(R.id.imgViedo)
    ImageView imgViedo;
    @Bind(R.id.imgComment)
    ImageView imgComment;
    FragmentDrawer drawerFragment;


    @Bind(R.id.orderlist)
    RecyclerView mRecyclerView;

    @Bind(R.id.mTxtSave)
    TextView mTxtSave;

    NotificationAdapter mAlertAdapter;

    RecyclerView.LayoutManager mLayoutManager;
    public List<ResponseList> mModuleList = new ArrayList<ResponseList>();
    @Bind(R.id.search_view)
    LinearLayout ll_searchView;
    @Bind(R.id.btnOk)
    Button btnDoSearch;
    @Bind(R.id.btnSearch)
    EditText etSearch;
    EditText mEdtModule;
    private String moduleID,categorayID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        initComponent();
        mPresenter.setView(this);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

    }

    public void initComponent() {
        mComponent = DaggerProfileComponent.builder()
                .appComponent(getApplicationComponent())
                .profileModule(new ProfileModule(this))
                .build();
        mComponent.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mModuleList = mPresenter.getModules();
        mPresenter.getUserAlerts();
        drawerFragment.setActivity(this);
        drawerFragment.setList(mModuleList);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        drawerFragment.setImage(mPresenter.getUserdetails());


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_search:
                Utils.getSearchQuery(NotificationActivity.this,etSearch,btnDoSearch,ll_searchView);
                break;
            case R.id.action_add:
                showModuleVideoPhoto(null);
                break;

        }

        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.imgHome)
    public void onHomeClick() {
        Intent intent = new Intent(NotificationActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.imgList)
    public void onListClick() {
        moveNext(1);
    }

    @OnClick(R.id.imgPhoto)
    public void onPhotoClick() {
        moveNext(2);
    }

    @OnClick(R.id.imgViedo)
    public void onVideoClick() {
        moveNext(3);
    }

    @OnClick(R.id.mTxtSave)
    public void onSaveClick() {
        mPresenter.updateNotification(getModuleId());
    }

    private String getModuleId() {
        String modid = "";
        if (mAlertAdapter.getAlertList().size() != 0) {
            for (ResponseList rs : mAlertAdapter.getAlertList()) {
                if (rs.isSelected()) {
                    modid = modid + "," + rs.getModuleid();
                }
            }
            if(!modid.isEmpty()&&modid!=null) {
                modid = modid.substring(1, modid.length());
            }
        }
        return modid;
    }

    public void moveNext(int i) {
        Intent intent = new Intent(NotificationActivity.this, GridHomeActivity.class);
        intent.putExtra("index", i);
        startActivity(intent);
    }

    AlertDialog mDialog;

    public void showDialog() {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
                //return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.search_dialog, null);
            builder.setView(dialogView);

            final EditText mEdtSearch = (EditText) dialogView.findViewById(R.id.btnSearch);
            Button mBtnok = (Button) dialogView.findViewById(R.id.btnOk);
            String sKeyword;

            mBtnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String sKeyword;
                    sKeyword = mEdtSearch.getText().toString().trim();
                    if (sKeyword != null && sKeyword.length() != 0) {
                        mDialog.dismiss();
                        Intent intent =  new Intent(NotificationActivity.this, SearchActivity.class);
                        intent.putExtra("keyword", sKeyword);
                        startActivity(intent);
                        return;
                    }
                }
            });
            mDialog = builder.create();
            mDialog.setCancelable(true);
            mDialog.show();
            mDialog.getWindow().setGravity(Gravity.TOP);
            Window window = mDialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.verticalMargin = .055f;
            wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(wlp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gotoHome(View v) {
        Intent intent =  new Intent(NotificationActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void setImage(String url) {

    }

    @Override
    public void setUserDetails(ProfileResponse responseBody) {

    }

    @Override
    public void setAdapter(List<AlertList> alertList) {
        try {
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new GridLayoutManager(this, 1);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAlertAdapter = new NotificationAdapter(this, this, mModuleList);
            mRecyclerView.setAdapter(mAlertAdapter);
            mAlertAdapter.notifyDataSetChanged();
            mPresenter.getSaveNotification();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setCommentAdapter(CommentResponse responseBody) {

    }

    @Override
    public void setNewsAdapter(NewsResponse responseBody) {

    }

    @Override
    public void setNotificationAdapter(NotificationResponse responseBody) {

    }

    @Override
    public void setCommentsListToAdapter(List<CommentsList> response) {

    }

    @Override
    public void goToLogin() {
        Intent intent =  new Intent(NotificationActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void updateSaveModules(String notificationModules) {
        if (notificationModules != null)
            mAlertAdapter.setCheckedItem(notificationModules);
    }

    @Override
    public void showArticleDeletedStatus(String message) {

    }


    @Override
    public void getComments(String moduleid, String artid) {

    }


    public void showModuleVideoPhoto(final File photoFile) {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_module_photo, null);
            builder.setView(dialogView);

            final TextView mBtnTakePhoto = (TextView) dialogView.findViewById(R.id.mBtnTakePhoto);
            ImageView mImgProfile = (ImageView) dialogView.findViewById(R.id.mImgProfile);
            TextView mBtnGallery = (TextView) dialogView.findViewById(R.id.mBtnGallery);
            TextView mBtnor = (TextView) dialogView.findViewById(R.id.mBtnor);
            mEdtModule = (EditText) dialogView.findViewById(R.id.mEdtModule);
            final EditText mEdtTitle = (EditText) dialogView.findViewById(R.id.mEdtTitle);
            final EditText mEdtDesc = (EditText) dialogView.findViewById(R.id.mEdtDesc);
            if (photoFile != null) {
                mBtnTakePhoto.setText("Post");
                mEdtModule.setVisibility(View.VISIBLE);
                mEdtTitle.setVisibility(View.VISIBLE);
                mEdtDesc.setVisibility(View.VISIBLE);
                mBtnGallery.setVisibility(View.GONE);
                mBtnor.setVisibility(View.GONE);
            }

            mBtnTakePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    if(mBtnTakePhoto.getText().toString().trim().equalsIgnoreCase("Post")) {
                        if(Utils.validateUserPost(NotificationActivity.this,mEdtModule.getText().toString().trim(),
                                mEdtTitle.getText().toString().trim(),
                                mEdtDesc.getText().toString().trim())) {
                            mDialog.dismiss();
                            mPresenter.uploadImageOrVideo(photoFile, mEdtModule.getText().toString().trim(),
                                    mEdtTitle.getText().toString().trim(),
                                    mEdtDesc.getText().toString().trim(), moduleID, categorayID);
                        }else{
                            Utils.makeToast(NotificationActivity.this,"Please enter all the details");
                        }
                    }
                    else {
                        mPresenter.snapPhotoClick();
                    }

                }
            });

            mBtnGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    mPresenter.pickFromGalleryClick();
                }
            });
            mEdtModule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppDialogManager.ModuleChooserDialog(NotificationActivity.this,NotificationActivity.this,mPresenter.getModules());
                }
            });


            mDialog = builder.create();
            mDialog.setCancelable(true);
            mDialog.show();
            mDialog.getWindow().setGravity(Gravity.TOP);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Window window = mDialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.verticalMargin = .1f;
            window.setAttributes(wlp);
            if (photoFile != null) {
                Picasso.with(this).load(photoFile).transform(new RoundedTransformation(10, 0)).into(mImgProfile);
            }
            mImgProfile.setDrawingCacheEnabled(false); // clear drawing cache
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void setPhoto(File photoFile) {
        showModuleVideoPhoto(photoFile);
    }
    @Override
    public void setCatagoryList(CatagoryList catagoryListPojo) {
        AppDialogManager.catagoryChooserDialog(NotificationActivity.this,NotificationActivity.this,catagoryListPojo.getCatagoryList());
    }

    @Override
    public void showProfileImageUpdateAlert(String message) {

    }

    @Override
    public void showAlertForNoficationSettings(String message) {

       showDialog(this,null, message, getString(R.string.txt_ok),null);
    }


    @Override
    public void onDialogClosedByModuleClick(ResponseList moduleObject) {
        mEdtModule.setText(moduleObject.getTitle());
        moduleID=moduleObject.getModuleid();
        mPresenter.getCatagoryDetails(moduleObject.getModuleid());
    }

    @Override
    public void onDialogClosedByCatagoryClick(CatagoryList.Catagory catagoryObject) {
        categorayID=catagoryObject.getCategoryid();
    }

    public void showDialog(Context context, String title, String message, String txtPositive, String txtNegative) {

        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                //return;
            }
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
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
                    dialog.dismiss();
                    Intent intent =  new Intent(NotificationActivity.this, HomeActivity.class);
                    startActivity(intent);

                    return;
                }
            });

            if (txtNegative != null) {
                mTxtNegative.setVisibility(View.VISIBLE);
                mTxtNegative.setText(txtNegative);
                mTxtNegative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                        return;
                    }
                });
            }
            dialog = builder.create();
            dialog.setCancelable(false);
            dialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
