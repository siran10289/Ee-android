package com.eeyuva.screens.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
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
import android.widget.TextView;

import com.eeyuva.ButterAppCompatActivity;
import com.eeyuva.R;
import com.eeyuva.screens.DetailPage.DetailActivity;
import com.eeyuva.screens.DetailPage.model.CommentsList;
import com.eeyuva.screens.authentication.LoginActivity;
import com.eeyuva.screens.gridpages.GridHomeActivity;
import com.eeyuva.screens.home.HomeActivity;
import com.eeyuva.screens.navigation.FragmentDrawer;
import com.eeyuva.screens.profile.model.AlertList;
import com.eeyuva.screens.profile.model.ChangePasswordResponse;
import com.eeyuva.screens.profile.model.CommentResponse;
import com.eeyuva.screens.profile.model.NewsResponse;
import com.eeyuva.screens.profile.model.NotificationResponse;
import com.eeyuva.screens.profile.model.ProfileResponse;
import com.eeyuva.screens.profile.userdetails.ProfileActivity;
import com.eeyuva.screens.searchpage.SearchActivity;
import com.eeyuva.utils.Constants;
import com.eeyuva.utils.customdialog.DialogListener;
import com.eeyuva.utils.customdialog.DialogUtils;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by hari on 01/10/16.
 */

public class ChangePasswordActivity extends ButterAppCompatActivity implements ProfileContract.View {


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

    @Bind(R.id.mBtnSave)
    TextView mBtnSave;

    @Bind(R.id.mEdtOldPassword)
    EditText mEdtOldPassword;

    @Bind(R.id.mEdtPassword)
    EditText mEdtPassword;

    @Bind(R.id.mEdtConfirmPassword)
    EditText mEdtConfirmPassword;


    FragmentDrawer drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
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
                break;

        }

        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.imgHome)
    public void onHomeClick() {
        Intent intent =
                new Intent(ChangePasswordActivity.this, HomeActivity.class);
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

    @OnClick(R.id.mBtnSave)
    public void onChangePasswordClick() {
        mPresenter.setChangePassword(mEdtOldPassword.getText().toString().trim(),
                mEdtPassword.getText().toString().trim(),
                mEdtConfirmPassword.getText().toString().trim());
    }

    public void moveNext(int i) {
        Intent intent =
                new Intent(ChangePasswordActivity.this, GridHomeActivity.class);
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
                                new Intent(ChangePasswordActivity.this, SearchActivity.class);
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
                new Intent(ChangePasswordActivity.this, HomeActivity.class);
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

    }

    @Override
    public void setCommentsListToAdapter(List<CommentsList> response) {

    }

    @Override
    public void goToLogin() {
        Intent intent =
                new Intent(ChangePasswordActivity.this, LoginActivity.class);
        intent.putExtra("from", Constants.CHANGEPASSWORD);
        startActivity(intent);
    }

    @Override
    public void updateSaveModules(String notificationModules) {

    }

    @Override
    public void showArticleDeletedStatus(String message) {

    }

    public void showListenerDialog(String msg) {
        DialogUtils.showDialog(this, msg, getString(R.string.sig__default_dialog_action_confirm), "", new DialogListener() {
            @Override
            public void onConfirm() {
                Intent intent =
                        new Intent(ChangePasswordActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancel() {
                return;
            }
        });
    }


}
