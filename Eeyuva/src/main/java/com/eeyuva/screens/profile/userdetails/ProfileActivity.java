package com.eeyuva.screens.profile.userdetails;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.eeyuva.screens.profile.model.AlertList;
import com.eeyuva.screens.profile.model.CommentResponse;
import com.eeyuva.screens.profile.model.NewsResponse;
import com.eeyuva.screens.profile.model.NotificationResponse;
import com.eeyuva.screens.profile.model.ProfileList;
import com.eeyuva.screens.profile.model.ProfileResponse;
import com.eeyuva.screens.searchpage.SearchActivity;
import com.eeyuva.utils.AppDialogManager;
import com.eeyuva.utils.Constants;
import com.eeyuva.utils.Utils;
import com.eeyuva.utils.listeners.DialogListener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by hari on 01/10/16.
 */

public class ProfileActivity extends ButterAppCompatActivity implements ProfileContract.View, IFragmentToActivity,DialogListener {


    @Inject
    ProfileContract.Presenter mPresenter;

    ProfileComponent mComponent;

    @Bind(R.id.tool_bar)
    Toolbar mToolbar;

    @Bind(R.id.imgProfile)
    ImageView imgProfile;

    @Bind(R.id.mTxtName)
    TextView mTxtName;

    @Bind(R.id.mTxtMail)
    TextView mTxtMail;

    @Bind(R.id.mTxtEditProfile)
    TextView mTxtEditProfile;

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
    TabLayout tabLayout;
    PagerAdapter adapter;
    ProfileList mProfileInfo;

    boolean mProfile = false;
    boolean mPhotoVideo = false;
    SwipeRefreshLayout mSwipeRefreshLayout;
    int mnflag, dobflag;
    String gender;
    CheckBox cbMobilePrivate;
    CheckBox cbDOBPrivate;
    String emailID;
    EditText mEdtModule;
    private String moduleID,categorayID;
    private  LinearLayout ll_upload_image;
    private File profilePhotoFile=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initComponent();
        mPresenter.setView(this);
        Bundle args = null;

        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getProfile();
            }
        });
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ArrayList<String> tabs = new ArrayList<>();
        tabs.add("Personal");
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new PagerAdapter(getSupportFragmentManager(), tabs);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


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
        mPresenter.getProfile();

        drawerFragment.setActivity(this);
        drawerFragment.setList(mPresenter.getModules());

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
                showDialog();
                break;
            case R.id.action_add:
                showModuleVideoPhoto(null);
                break;

        }

        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.imgHome)
    public void onHomeClick() {
        Intent intent =
                new Intent(ProfileActivity.this, HomeActivity.class);
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

    public void moveNext(int i) {
        Intent intent =
                new Intent(ProfileActivity.this, GridHomeActivity.class);
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
                        Intent intent =
                                new Intent(ProfileActivity.this, SearchActivity.class);
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
        Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void setImage(String url) {
        Picasso.with(this).load("http://eeyuva.com/static/userpics/" + url).transform(new RoundedTransformation(100, 0)).placeholder(getResources().getDrawable(R.drawable.ic_profile_default)).resize(80, 80).into(imgProfile);
        drawerFragment.setUpdatedProfileImage("http://eeyuva.com/static/userpics/" + url);
    }

    @Override
    public void setUserDetails(ProfileResponse responseBody) {
        try {
            Log.e("ProfileResponse:",new Gson().toJson(responseBody).toString());
            emailID=responseBody.getEmailID();
            mProfileInfo = responseBody.getmProfileList().get(0);
            mTxtName.setText(responseBody.getmProfileList().get(0).getFname());
            mTxtMail.setText(responseBody.getmProfileList().get(0).getLname());
            Fragment fragment = adapter.getFragment(tabLayout.getSelectedTabPosition());
            if (fragment != null)
                ((TabFragment1) fragment).onRefresh(responseBody);
            mSwipeRefreshLayout.setRefreshing(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setAdapter(List<AlertList> alertList) {

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
    public void setPhoto(File photoFile) {
        if (mProfile) {
            profilePhotoFile=photoFile;
            showUpdatePhoto(photoFile);
        } else if (mPhotoVideo)
        {
            showModuleVideoPhoto(photoFile);
        }
    }

    @Override
    public void setCommentsListToAdapter(List<CommentsList> response) {

    }

    @Override
    public void goToLogin() {
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.putExtra("from", Constants.PROFILE);
        startActivity(intent);
    }

    @Override
    public void updateSaveModules(String notificationModules) {

    }

    @Override
    public void showArticleDeletedStatus(String message) {

    }



    @Override
    public void showToast(String msg) {

    }

    @Override
    public void communicateToFragment2() {

    }

    @Override
    public void communicateToStuffsActivity(String moduleid, String artid) {

    }

    @OnClick(R.id.mTxtEditProfile)
    public void editProfileClick() {
        showCommentDialog();
    }

    @OnClick(R.id.imgProfile)
    public void updateProfileClick() {
        showUpdatePhoto(null);
    }

    public void showCommentDialog() {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_profile, null);
            builder.setView(dialogView);

            RelativeLayout LayRating = (RelativeLayout) dialogView.findViewById(R.id.LayRating);
            TextView mBtnok = (TextView) dialogView.findViewById(R.id.btnOk);
            TextView txtRate = (TextView) dialogView.findViewById(R.id.mTxtRate);

            final EditText mEdtFName = (EditText) dialogView.findViewById(R.id.mEdtFName);
            final EditText mEdtLName = (EditText) dialogView.findViewById(R.id.mEdtLName);
            final EditText mEdtMobile = (EditText) dialogView.findViewById(R.id.mEdtMobile);
            final EditText mEdtMailid = (EditText) dialogView.findViewById(R.id.mEdtMailid);
            final EditText mEdtDob = (EditText) dialogView.findViewById(R.id.mEdtDOB);
            final EditText mEdtGender = (EditText) dialogView.findViewById(R.id.mEdtGender);
            final EditText mEdtAbout = (EditText) dialogView.findViewById(R.id.mEdtAbout);
            final CheckBox cbMobilePrivate=(CheckBox)dialogView.findViewById(R.id.cbMobilePrivate);
            final CheckBox cbDOBPrivate=(CheckBox)dialogView.findViewById(R.id.cbDOBPrivate);

            mEdtFName.setText(mProfileInfo.getFname());
            mEdtLName.setText(mProfileInfo.getLname());
            mEdtMobile.setText(mProfileInfo.getMobile());
            mEdtMailid.setText(emailID);
            mEdtDob.setText(mProfileInfo.getDob());
            if(mProfileInfo.getGender().equalsIgnoreCase("1")){
                mEdtGender.setText("Male");
            }else if(mProfileInfo.getGender().equalsIgnoreCase("0")){
                mEdtGender.setText("Female");

            }
            mEdtAbout.setText(mProfileInfo.getAboutMe());
            mEdtMailid.setEnabled(false);

            mBtnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   Utils.hideSoftKeyBoard(ProfileActivity.this);
                    if(mEdtGender.getText().toString().toLowerCase().equalsIgnoreCase("male")){
                        gender="1";
                    }else if(mEdtGender.getText().toString().toLowerCase().equalsIgnoreCase("female")){
                        gender="0";
                    }
                    mPresenter.setUpdateProfile(mEdtFName.getText().toString().trim(),
                            mEdtLName.getText().toString().trim(),
                            mEdtMobile.getText().toString().trim(),
                            gender,
                            mEdtDob.getText().toString().trim(),
                            mEdtAbout.getText().toString().trim(),mnflag,dobflag);
                    mDialog.dismiss();


                }
            });
            cbMobilePrivate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        mnflag=1;
                    }else{
                        mnflag=0;
                    }
                }
            });
            cbDOBPrivate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        dobflag=1;
                    }else{
                        dobflag=0;
                    }
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



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showUpdatePhoto(File photoFile) {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }

            mProfile = true;
            mPhotoVideo = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_update_photo, null);
            builder.setView(dialogView);

            TextView mBtnTakePhoto = (TextView) dialogView.findViewById(R.id.mBtnTakePhoto);
            ImageView mImgProfile = (ImageView) dialogView.findViewById(R.id.mImgProfile);
            TextView mBtnGallery = (TextView) dialogView.findViewById(R.id.mBtnGallery);
            ll_upload_image=(LinearLayout)dialogView.findViewById(R.id.ll_upload_image);
            TextView tvCancel=(TextView)dialogView.findViewById(R.id.mBtnCancel);
            TextView tvUse=(TextView)dialogView.findViewById(R.id.mBtnUse);

            mBtnTakePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    mPresenter.snapPhotoClick();
                }
            });

            mBtnGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    mPresenter.pickFromGalleryClick();
                }
            });
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            });
            tvUse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(profilePhotoFile!=null) {
                        mDialog.dismiss();
                        mPresenter.uploadImage(profilePhotoFile);
                    }
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
    public void showProfileImageUpdateAlert(String message) {
        Toast.makeText(ProfileActivity.this,message, Toast.LENGTH_SHORT).show();
        onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    public void showModuleVideoPhoto(final File photoFile) {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            mProfile = false;
            mPhotoVideo = true;

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
                    if(mBtnTakePhoto.getText().toString().trim().equalsIgnoreCase("Post"))
                        mPresenter.uploadImageOrVideo(photoFile, mEdtModule.getText().toString().trim(),
                                mEdtTitle.getText().toString().trim(),
                                mEdtDesc.getText().toString().trim(),moduleID,categorayID);
                    else
                    mPresenter.snapPhotoClick();

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
                    AppDialogManager.ModuleChooserDialog(ProfileActivity.this,ProfileActivity.this,mPresenter.getModules());
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
    public void setCatagoryList(CatagoryList catagoryListPojo) {
        AppDialogManager.catagoryChooserDialog(ProfileActivity.this,ProfileActivity.this,catagoryListPojo.getCatagoryList());
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

}
