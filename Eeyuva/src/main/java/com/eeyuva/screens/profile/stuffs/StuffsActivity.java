package com.eeyuva.screens.profile.stuffs;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eeyuva.ButterAppCompatActivity;
import com.eeyuva.R;
import com.eeyuva.screens.DetailPage.CommentsLoadAdapter;
import com.eeyuva.screens.DetailPage.model.CommentsList;
import com.eeyuva.screens.authentication.LoginActivity;
import com.eeyuva.screens.gridpages.GridHomeActivity;
import com.eeyuva.screens.home.HomeActivity;
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
import com.eeyuva.screens.profile.userdetails.IFragmentToActivity;
import com.eeyuva.screens.profile.userdetails.ProfileActivity;
import com.eeyuva.screens.searchpage.SearchActivity;
import com.eeyuva.screens.splash.SplashActivity;
import com.eeyuva.utils.Constants;
import com.eeyuva.utils.customdialog.DialogListener;
import com.eeyuva.utils.customdialog.DialogUtils;
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

public class StuffsActivity extends ButterAppCompatActivity implements ProfileContract.View, IFragmentToActivity,MyArticleDeleteListener {


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
    TabLayout tabLayout;
    StuffPagerAdapter adapter;
    ProfileList mProfileInfo;
    private int mPrevState = 0;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private int currentItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stuffs);
        initComponent();
        mPresenter.setView(this);
        Bundle args = null;

        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ArrayList<String> tabs = new ArrayList<>();
        tabs.add("Comments");
        tabs.add("It's my post");
        tabs.add("Notifications");
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new StuffPagerAdapter(StuffsActivity.this,getSupportFragmentManager(), tabs);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        try {
            if (getIntent().getExtras().getString("mode").equalsIgnoreCase("noti")) {
                mPrevState = 1;
                viewPager.setCurrentItem(2);
                mPresenter.getNotification();
            } else {
                mPresenter.getComments();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (currentItem == 0) {
                    mPresenter.getComments();
                } else if (currentItem == 1)
                    mPresenter.getNews();
                else if (currentItem == 2)
                    mPresenter.getNotification();
            }
        });
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i("onTabSelected", "onTabSelected" + tab.getPosition());
                viewPager.setCurrentItem(tab.getPosition());
                currentItem = tab.getPosition();
                if (tab.getPosition() == 0) {
                    mPresenter.getComments();
                } else if (tab.getPosition() == 1)
                    mPresenter.getNews();
                else if (tab.getPosition() == 2)
                    mPresenter.getNotification();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.i("onTabUnselected", "onTabUnselected" + tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.i("onTabReselected", "onTabReselected" + tab.getPosition());
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
//        mPresenter.getComments();
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
                new Intent(StuffsActivity.this, HomeActivity.class);
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
                new Intent(StuffsActivity.this, GridHomeActivity.class);
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
                                new Intent(StuffsActivity.this, SearchActivity.class);
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
        Intent intent =
                new Intent(StuffsActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void setImage(String url) {

    }

    @Override
    public void setUserDetails(ProfileResponse responseBody) {
        try {
            mProfileInfo = responseBody.getmProfileList().get(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setAdapter(List<AlertList> alertList) {

    }

    @Override
    public void setCommentAdapter(CommentResponse responseBody) {
        mSwipeRefreshLayout.setRefreshing(false);

        Fragment fragment = adapter.getFragment(tabLayout.getSelectedTabPosition());
        if (fragment != null)
            ((CommentFrgament) fragment).onRefresh(responseBody);
    }

    @Override
    public void setNewsAdapter(NewsResponse responseBody) {
        mSwipeRefreshLayout.setRefreshing(false);

        Fragment fragment = adapter.getFragment(tabLayout
                .getSelectedTabPosition());
        if (fragment != null)
            ((NewsFrgament) fragment).onRefresh(responseBody);
    }

    @Override
    public void setNotificationAdapter(NotificationResponse responseBody) {
        mSwipeRefreshLayout.setRefreshing(false);

        Fragment fragment = adapter.getFragment(tabLayout
                .getSelectedTabPosition());
        if (fragment != null)
            ((NotificationFrgament) fragment).onRefresh(responseBody);
    }

    @Override
    public void setCommentsListToAdapter(List<CommentsList> response) {
        showViewCommentDialog(response);
    }

    @Override
    public void goToLogin() {
        Intent intent =
                new Intent(StuffsActivity.this, LoginActivity.class);
        intent.putExtra("from", Constants.STUFFS);
        startActivity(intent);
    }

    @Override
    public void updateSaveModules(String notificationModules) {

    }

    @Override
    public void showArticleDeletedStatus(String message) {
        Toast.makeText(StuffsActivity.this, message, Toast.LENGTH_SHORT).show();
        mPresenter.getNews();
    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void communicateToFragment2() {

    }

    @Override
    public void communicateToStuffsActivity(String moduleid, String artid) {
        mPresenter.getViewComments(moduleid, artid);
    }


    public void showViewCommentDialog(List<CommentsList> response) {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_view_comment, null);
            builder.setView(dialogView);

            RelativeLayout LayRating = (RelativeLayout) dialogView.findViewById(R.id.LayRating);
            RelativeLayout topLay = (RelativeLayout) dialogView.findViewById(R.id.topLay);
            Button mBtnok = (Button) dialogView.findViewById(R.id.btnOk);
            Button mBtnCancel = (Button) dialogView.findViewById(R.id.btnCancel);
            TextView txtRate = (TextView) dialogView.findViewById(R.id.mTxtRate);
            final EditText comments = (EditText) dialogView.findViewById(R.id.mEdtMailid);
            mBtnok.setVisibility(View.GONE);
            mBtnCancel.setVisibility(View.GONE);
            txtRate.setVisibility(View.GONE);
            comments.setVisibility(View.GONE);
            topLay.setVisibility(View.GONE);
            mlistview = (RecyclerView) dialogView.findViewById(R.id.orderlist);
            mlistview.setHasFixedSize(true);
            mLayoutManager = new GridLayoutManager(this, 1);
            mlistview.setLayoutManager(mLayoutManager);
            mCommentsAdapter = new CommentsLoadAdapter(this, response);
            mlistview.setAdapter(mCommentsAdapter);
            mCommentsAdapter.notifyDataSetChanged();
            mBtnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();

                }
            });
            mBtnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();

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

    RecyclerView mlistview;
    RecyclerView.LayoutManager mLayoutManager;
    CommentsLoadAdapter mCommentsAdapter;


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
            final EditText mEdtModule = (EditText) dialogView.findViewById(R.id.mEdtModule);
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
                    if (mBtnTakePhoto.getText().toString().trim().equalsIgnoreCase("Post"))
                        mPresenter.uploadImageOrVideo(photoFile, mEdtModule.getText().toString().trim(),
                                mEdtTitle.getText().toString().trim(),
                                mEdtDesc.getText().toString().trim());
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
    public void showListenerDialog(String errorMsg) {

    }

    @Override
    public void deleteArticle(String articleID) {
        mPresenter.deleteMyArticle(articleID);

    }
}
