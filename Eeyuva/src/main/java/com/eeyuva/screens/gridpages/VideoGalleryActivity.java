package com.eeyuva.screens.gridpages;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eeyuva.ButterAppCompatActivity;
import com.eeyuva.R;
import com.eeyuva.di.component.DaggerGridComponent;
import com.eeyuva.di.component.GridComponent;
import com.eeyuva.di.module.GridModule;
import com.eeyuva.screens.Upload;
import com.eeyuva.screens.authentication.LoginActivity;
import com.eeyuva.screens.gridpages.model.PhotoGalleryList;
import com.eeyuva.screens.gridpages.model.PhotoGalleryResponse;
import com.eeyuva.screens.gridpages.model.PhotoList;
import com.eeyuva.screens.gridpages.model.PhotoListResponse;
import com.eeyuva.screens.gridpages.model.UserNewsList;
import com.eeyuva.screens.gridpages.model.UserNewsListResponse;
import com.eeyuva.screens.home.CatagoryList;
import com.eeyuva.screens.home.HomeActivity;
import com.eeyuva.screens.home.ImageResponse;
import com.eeyuva.screens.home.ResponseList;
import com.eeyuva.screens.home.loadmore.ArticlesActivity;
import com.eeyuva.screens.home.loadmore.RoundedTransformation;
import com.eeyuva.screens.navigation.FragmentDrawer;
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

import static com.eeyuva.R.color.white;

/**
 * Created by hari on 17/09/16.
 */
public class VideoGalleryActivity extends ButterAppCompatActivity implements GridContract.View, GridContract.AdapterCallBack,DialogListener {

    @Inject
    GridContract.Presenter mPresenter;

    GridComponent mComponent;

    @Bind(R.id.tool_bar)
    Toolbar mToolbar;

    @Bind(R.id.txtHotStories)
    TextView mTxtHotStories;

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

    int mIndex;

    @Bind(R.id.orderlist)
    RecyclerView mRecyclerView;

    VideoGalleryAdapter mGridLoadAdapter;

    RecyclerView.LayoutManager mLayoutManager;

    List<ResponseList> mModuleList = new ArrayList<ResponseList>();
    String mModuleId;
    private String mTitle;
    private String mTrid;
    @Bind(R.id.search_view)
    LinearLayout ll_searchView;
    @Bind(R.id.btnOk)
    Button btnDoSearch;
    @Bind(R.id.btnSearch)
    EditText etSearch;
    EditText mEdtModule;
    private String moduleID,categorayID;
    @Bind(R.id.ll)
    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photolist);
        initComponent();
        mPresenter.setView(this);

        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(white));

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        mTitle = getIntent().getExtras().getString("title");
        mTrid = getIntent().getExtras().getString("trid");
        mTxtHotStories.setText(mTitle);
        mPresenter.getVideoGalleryList(Constants.GridGetVideoGallery, mTrid);

    }


    private void initAdapter() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mGridLoadAdapter = new GridLoadAdapter(this, mModuleList);
        mRecyclerView.setAdapter(mGridLoadAdapter);
        mGridLoadAdapter.notifyDataSetChanged();
    }

    @Override
    public void setAdapter(PhotoListResponse responseBody) {

    }

    @Override
    public void moveToGalleryView() {
        Intent intent = new Intent(VideoGalleryActivity.this, PhotoGalleryActivity.class);
        startActivity(intent);
    }

    @Override
    public void setAdapter(PhotoGalleryResponse responseBody) {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setBackgroundColor(Color.WHITE);
        ll.setVisibility(View.GONE);
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mGridLoadAdapter = new VideoGalleryAdapter(this, this, responseBody.getResponse());
        mRecyclerView.setAdapter(mGridLoadAdapter);
        mGridLoadAdapter.notifyDataSetChanged();
        if(responseBody.getResponse().size()==1){
            Intent intent = new Intent(VideoGalleryActivity.this, VideoPlayActivity.class);
            intent.putExtra("url", responseBody.getResponse().get(0).getPicpath());
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void setAdapter(UserNewsListResponse responseBody) {

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
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
        drawerFragment.setImage(mPresenter.getUserDetails());

    }

    private void initComponent() {
        mComponent = DaggerGridComponent.builder()
                .appComponent(getApplicationComponent())
                .gridModule(new GridModule(this))
                .build();
        mComponent.inject(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_search:
                Utils.getSearchQuery(VideoGalleryActivity.this,etSearch,btnDoSearch,ll_searchView);
                break;
            case R.id.action_add:
                if (mPresenter.getUserDetails() == null)
                    goToLogin();
                else
                    showModuleVideoPhoto(null, 2);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void goToLogin() {
        Intent intent =
                new Intent(VideoGalleryActivity.this, LoginActivity.class);
        intent.putExtra("from", Constants.VIDEOGALLERY);
        startActivity(intent);
    }

    @OnClick(R.id.imgHome)
    public void onHomeClick() {
        Intent intent =
                new Intent(VideoGalleryActivity.this, HomeActivity.class);
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

    @OnClick(R.id.imgComment)
    public void onCommentClick() {
    }

    public void moveNext(int i) {
        Intent intent =
                new Intent(VideoGalleryActivity.this, GridHomeActivity.class);
        intent.putExtra("index", i);
        startActivity(intent);
        finish();
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
                                new Intent(VideoGalleryActivity.this, SearchActivity.class);
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


    @Override
    public void setSelectItem(ResponseList rl) {
    }

    @Override
    public void setSelectItem(PhotoList rl) {

    }

    @Override
    public void setSelectItem(PhotoGalleryList rl) {
        Intent intent = new Intent(VideoGalleryActivity.this, VideoPlayActivity.class);
        intent.putExtra("url", rl.getPicpath());
        startActivity(intent);
    }

    @Override
    public void setSelectItem(UserNewsList rl) {

    }

    public void gotoHome(View v) {
        Intent intent =
                new Intent(VideoGalleryActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    boolean mPhoto = true;
    boolean mVideo = false;

    public void showModuleVideoPhoto(final File photoFile, int i) {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_module_photo, null);
            builder.setView(dialogView);

            final TextView mBtnTakePhoto = (TextView) dialogView.findViewById(R.id.mBtnTakePhoto);
            final TextView mTxtPhoto = (TextView) dialogView.findViewById(R.id.mTxtPhoto);
            final TextView mTxtVideo = (TextView) dialogView.findViewById(R.id.mTxtVideo);
            ImageView mImgProfile = (ImageView) dialogView.findViewById(R.id.mImgProfile);
            TextView mBtnGallery = (TextView) dialogView.findViewById(R.id.mBtnGallery);
            TextView mBtnor = (TextView) dialogView.findViewById(R.id.mBtnor);
            mEdtModule = (EditText) dialogView.findViewById(R.id.mEdtModule);
            final EditText mEdtTitle = (EditText) dialogView.findViewById(R.id.mEdtTitle);
            final EditText mEdtDesc = (EditText) dialogView.findViewById(R.id.mEdtDesc);
            if (photoFile != null || i == 1) {
                if (i == 1)
                    mTxtPhoto.setClickable(false);
                else if (i == 2)
                    mTxtVideo.setClickable(false);
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
                    if (mBtnTakePhoto.getText().toString().trim().equalsIgnoreCase("Post")) {
                        if (mPhoto) {
                            if(Utils.validateUserPost(VideoGalleryActivity.this,mEdtModule.getText().toString().trim(),
                                    mEdtTitle.getText().toString().trim(),
                                    mEdtDesc.getText().toString().trim())) {
                                mDialog.dismiss();
                                mPresenter.uploadImageOrVideo(photoFile, mEdtModule.getText().toString().trim(),
                                        mEdtTitle.getText().toString().trim(),
                                        mEdtDesc.getText().toString().trim(), moduleID, categorayID);
                            }else{
                                Utils.makeToast(VideoGalleryActivity.this,"Please enter all the details");
                            }
                        } else {
                            uploadVideo();
                        }
                    } else {
                        if (mPhoto)
                            mPresenter.snapPhotoClick();
                        else
                            chooseVideo();
                    }

                }
            });


            mBtnGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPhoto)
                        mPresenter.pickFromGalleryClick();
                    else
                        chooseVideo();
                }
            });

            mTxtPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPhoto = true;
                    mVideo = false;
                    mBtnTakePhoto.setText("Take a Photo");
                    mTxtPhoto.setTextColor(getResources().getColor(white));
                    mTxtVideo.setTextColor(getResources().getColor(R.color.light_gray_line));

                }
            });

            mTxtVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPhoto = false;
                    mVideo = true;
                    mBtnTakePhoto.setText("Take a Video");
                    mTxtVideo.setTextColor(getResources().getColor(white));
                    mTxtPhoto.setTextColor(getResources().getColor(R.color.light_gray_line));


                }
            });
            mEdtModule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppDialogManager.ModuleChooserDialog(VideoGalleryActivity.this,VideoGalleryActivity.this,mPresenter.getModules());
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
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_VIDEO) {
                System.out.println("SELECT_VIDEO");
                Uri selectedImageUri = data.getData();
                selectedPath = getPath(selectedImageUri);
                showModuleVideoPhoto(null, 1);
            } else
                mPresenter.onActivityResult(requestCode, resultCode, data);

        }

    }

    @Override
    public void setPhoto(File photoFile) {
        showModuleVideoPhoto(photoFile, 2);
    }



    private static final int SELECT_VIDEO = 3;

    private String selectedPath;

    private void chooseVideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select a Video "), SELECT_VIDEO);
    }


    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        cursor.close();

        return path;
    }


    private void uploadVideo() {
        class UploadVideo extends AsyncTask<Void, Void, String> {

            ProgressDialog uploading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                uploading = ProgressDialog.show(VideoGalleryActivity.this, "Uploading File", "Please wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                uploading.dismiss();
            }

            @Override
            protected String doInBackground(Void... params) {
                Upload u = new Upload();
                String url = Constants.DetailPostUserNews + "mid=4&catid=Cat_6395ebd0f&title=&desc=&uid=3939";
                String msg = u.upLoad2Server(selectedPath, url);
                Log.i("msg", "msg" + msg);
                Gson gson;
                gson = new Gson();
                ImageResponse response = gson.fromJson(msg, ImageResponse.class);
                showErrorDialog(response.getStatusInfo());
                return msg;
            }
        }
        UploadVideo uv = new UploadVideo();
        uv.execute();
    }
    @Override
    public void setCatagoryList(CatagoryList catagoryListPojo) {
        AppDialogManager.catagoryChooserDialog(VideoGalleryActivity.this,VideoGalleryActivity.this,catagoryListPojo.getCatagoryList());
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
