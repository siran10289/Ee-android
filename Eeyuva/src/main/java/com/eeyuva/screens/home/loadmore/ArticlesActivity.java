package com.eeyuva.screens.home.loadmore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eeyuva.ButterAppCompatActivity;
import com.eeyuva.R;
import com.eeyuva.di.component.DaggerHomeComponent;
import com.eeyuva.di.component.HomeComponent;
import com.eeyuva.di.module.HomeModule;
import com.eeyuva.screens.DetailPage.DetailActivity;
import com.eeyuva.screens.Upload;
import com.eeyuva.screens.authentication.LoginActivity;
import com.eeyuva.screens.gridpages.GridHomeActivity;
import com.eeyuva.screens.home.CatagoryList;
import com.eeyuva.screens.home.GetArticleResponse;
import com.eeyuva.screens.home.HomeActivity;
import com.eeyuva.screens.home.HomeContract;
import com.eeyuva.screens.home.ImageResponse;
import com.eeyuva.screens.home.ResponseItem;
import com.eeyuva.screens.home.ResponseList;
import com.eeyuva.screens.navigation.FragmentDrawer;
import com.eeyuva.screens.profile.alerts.AlertActivity;
import com.eeyuva.screens.searchpage.SearchActivity;
import com.eeyuva.screens.searchpage.model.SearchResponse;
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
 * Created by hari on 05/09/16.
 */
public class ArticlesActivity extends ButterAppCompatActivity implements HomeContract.View, HomeContract.AdapterCallBack,DialogListener {

    @Inject
    HomeContract.Presenter mPresenter;

    HomeComponent mComponent;
    private FragmentDrawer drawerFragment;


    @Bind(R.id.tool_bar)
    Toolbar mToolbar;

    private List<ResponseItem> mArticlesList = new ArrayList<ResponseItem>();

    private int mPrevIndex = 0;

    String mModuleId;

    String mModuleName;

    @Bind(R.id.orderlist)
    RecyclerView mRecyclerView;

    @Bind(R.id.txtHotStories)
    TextView mTxtHotStories;

    ArticlesLoadAdapter mArticleAdapter;

    RecyclerView.LayoutManager mLayoutManager;
    private String mOrderId;


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
    @Bind(R.id.search_view)
    LinearLayout ll_searchView;
    @Bind(R.id.btnOk)
    Button btnDoSearch;
    @Bind(R.id.btnSearch)
    EditText etSearch;
    EditText mEdtModule;
    private String moduleID,categorayID;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        initComponent();
        mPresenter.setView(this);

        setSupportActionBar(mToolbar);
//        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getArticles(mModuleId);
            }
        });

        mPrevIndex = getIntent().getExtras().getInt("index");
        mModuleId = getIntent().getExtras().getString("module_id");
        mOrderId = getIntent().getExtras().getString("order_id");
        mModuleName = getIntent().getExtras().getString("module_name");
        mTxtHotStories.setText(mModuleName);
        mPresenter.getArticles(mModuleId);
    }

    private void initAdapter(final List<ResponseItem> responseItem) {
        this.mArticlesList = responseItem;
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mArticleAdapter = new ArticlesLoadAdapter(this, this, mArticlesList);
        mArticleAdapter.setLoadMoreListener(new ArticlesLoadAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                mRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        int index = mArticlesList.size();
                        loadMore(index);
                    }


                });
                //Calling loadMore function in Runnable to fix the
                // java.lang.IllegalStateException: Cannot call this method while RecyclerView is computing a layout or scrolling error
            }
        });
        mRecyclerView.setAdapter(mArticleAdapter);
        mArticleAdapter.notifyDataSetChanged();
        if (mPrevIndex != 0)
            loadMore(mArticlesList.size());

    }

    @Override
    public void setLoadMoredata(GetArticleResponse responseBody) {
        //remove loading view

        mArticlesList.remove(mArticlesList.size() - 1);
        Log.i("index", "mArticlesList after rem" + mArticlesList.size());

        List<ResponseItem> result = responseBody.getResponseItem();
        Log.i("index", "mArticlesList result" + result.size());

        if (result.size() > 0) {
            //add loaded data
            mArticlesList.addAll(result);
        } else {//result size 0 means there is no more data available at server
            mArticleAdapter.setMoreDataAvailable(false);
            //telling adapter to stop calling load more as no more server data available
            Toast.makeText(this, "No More Data Available", Toast.LENGTH_LONG).show();
        }
        mArticleAdapter.notifyDataChanged();
        Log.i("index", "mArticlesList after load" + mArticlesList.size());

        try {
            mLayoutManager.scrollToPosition(mArticlesList.size() - 7);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setLoadMoredata(SearchResponse responseBody) {

    }

    private void loadMore(int index) {
        Log.i("index", "mArticlesList before" + mArticlesList.size());
        Log.i("index", "index" + index);
        mArticlesList.add(new ResponseItem(true));
        mArticleAdapter.notifyItemInserted(mArticlesList.size() - 1);
        mPresenter.getArticles(mModuleId, index + 1, index);

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
        drawerFragment.setList(mPresenter.getModules());
        drawerFragment.setImage(mPresenter.getUserdetails());
//        mArticleAdapter.notifyDataChanged();
    }

    private void initComponent() {
        mComponent = DaggerHomeComponent.builder()
                .appComponent(getApplicationComponent())
                .homeModule(new HomeModule(this))
                .build();
        mComponent.inject(this);
    }



    @Override
    public void setDataToAdapter(List<ResponseList> response) {

    }

    @Override
    public void setArticleAdapterNotify(List<ResponseItem> responseItem) {
        mSwipeRefreshLayout.setRefreshing(false);
        initAdapter(responseItem);
    }


    @Override
    public void onItemClick(String articleid, String modid) {
        Intent intent = new Intent(ArticlesActivity.this, DetailActivity.class);
        intent.putExtra("module_id", mModuleId);
        intent.putExtra("article_id", articleid);
//        intent.putExtra("order_id", mOrderId);
//        intent.putExtra("module_name", mModuleName);
        intent.putExtra("type", "home");
        startActivity(intent);
    }

    @OnClick(R.id.imgHome)
    public void onHomeClick() {
        Intent intent =
                new Intent(ArticlesActivity.this, HomeActivity.class);
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
                new Intent(ArticlesActivity.this, GridHomeActivity.class);
        intent.putExtra("index", i);
        startActivity(intent);
    }
    @Override
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
                Utils.getSearchQuery(ArticlesActivity.this,etSearch,btnDoSearch,ll_searchView);
                break;
            case R.id.action_add:
                if (mPresenter.getUserDetails() == null)
                    goToLogin();
                else
                showModuleVideoPhoto(null,2);
                break;

        }

        return super.onOptionsItemSelected(item);
    }
    private void goToLogin() {
        Intent intent =
                new Intent(ArticlesActivity.this, LoginActivity.class);
        intent.putExtra("from", Constants.ARTICLES);
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
                                new Intent(ArticlesActivity.this, SearchActivity.class);
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
            wlp.horizontalMargin=-.055f;
            wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(wlp);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void gotoHome(View v)
    {
        Intent intent =
                new Intent(ArticlesActivity.this, HomeActivity.class);
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
                        mDialog.dismiss();
                        if (mPhoto) {
                            mPresenter.uploadImageOrVideo(photoFile, mEdtModule.getText().toString().trim(),
                                    mEdtTitle.getText().toString().trim(),
                                    mEdtDesc.getText().toString().trim(),moduleID,categorayID);
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
                    mTxtPhoto.setTextColor(getResources().getColor(R.color.white));
                    mTxtVideo.setTextColor(getResources().getColor(R.color.light_gray_line));

                }
            });

            mTxtVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPhoto = false;
                    mVideo = true;
                    mBtnTakePhoto.setText("Take a Video");
                    mTxtVideo.setTextColor(getResources().getColor(R.color.white));
                    mTxtPhoto.setTextColor(getResources().getColor(R.color.light_gray_line));


                }
            });
            mEdtModule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppDialogManager.ModuleChooserDialog(ArticlesActivity.this,ArticlesActivity.this,mPresenter.getModules());
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
                uploading = ProgressDialog.show(ArticlesActivity.this, "Uploading File", "Please wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                uploading.dismiss();
            }

            @Override
            protected String doInBackground(Void... params) {
                Upload u = new Upload();
                String url = Constants.DetailPostUserNews+"mid=4&catid=Cat_6395ebd0f&title=&desc=&uid=3939";
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
        AppDialogManager.catagoryChooserDialog(ArticlesActivity.this,ArticlesActivity.this,catagoryListPojo.getCatagoryList());
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
