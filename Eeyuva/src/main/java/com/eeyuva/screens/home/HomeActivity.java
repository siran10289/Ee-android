package com.eeyuva.screens.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.LinkagePager;
import android.support.v4.view.PagerAdapter;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.eeyuva.ButterAppCompatActivity;
import com.eeyuva.R;
import com.eeyuva.di.component.DaggerHomeComponent;
import com.eeyuva.di.component.HomeComponent;
import com.eeyuva.di.module.HomeModule;
import com.eeyuva.screens.DetailPage.DetailActivity;
import com.eeyuva.screens.Upload;
import com.eeyuva.screens.authentication.LoginActivity;
import com.eeyuva.screens.gridpages.GridHomeActivity;
import com.eeyuva.screens.gridpages.VideoGalleryActivity;
import com.eeyuva.screens.gridpages.model.PhotoGalleryResponse;
import com.eeyuva.screens.home.coverflow.ArticlesAdapter;
import com.eeyuva.screens.home.coverflow.CoverFlowAdapter;
import com.eeyuva.screens.home.hotNewsCoverFlow.HotNewsCoverFlowAdapter;
import com.eeyuva.screens.home.infiniteCoverFlow.InfinitePagerAdapter;
import com.eeyuva.screens.home.infiniteHotCoverFlow.InfiniteHotFragment;
import com.eeyuva.screens.home.infiniteHotCoverFlow.InfiniteHotPagerAdapter;
import com.eeyuva.screens.home.loadmore.ArticlesActivity;
import com.eeyuva.screens.home.loadmore.RoundedTransformation;
import com.eeyuva.screens.navigation.FragmentDrawer;
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
import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;
import me.crosswall.lib.coverflow.core.LinkageCoverTransformer;
import me.crosswall.lib.coverflow.core.LinkagePagerContainer;

/**
 * Created by hari on 05/09/16.
 */
public class HomeActivity extends ButterAppCompatActivity implements HomeContract.View, HomeContract.AdapterCallBack, InfiniteHotFragment.CommmunicateListener,DialogListener {

    @Inject
    HomeContract.Presenter mPresenter;
    HomeComponent mComponent;
    private FragmentDrawer drawerFragment;
    @Bind(R.id.tool_bar)
    Toolbar mToolbar;

    @Bind(R.id.label)
    TextView label;

    @Bind(R.id.imgLoadMore)
    TextView txtLoadMore;

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


    private FeatureCoverFlow mCoverFlow;
    private FeatureCoverFlow mHotNewscoverflow;
    private CoverFlowAdapter mAdapter;
    private HotNewsCoverFlowAdapter mHotNewsAdapter;
    private TextSwitcher mTitle;
    public static List<ResponseList> mModuleList = new ArrayList<ResponseList>();
    public static List<ResponseList> mFinalModuleList = new ArrayList<ResponseList>();
    public static List<ModuleList> mHotModuleList = new ArrayList<ModuleList>();

    @Bind(R.id.orderlist)
    RecyclerView mRecyclerView;

    @Bind(R.id.Layscroll)
    ScrollView Layscroll;

    ArticlesAdapter mArticleAdapter;

    RecyclerView.LayoutManager mLayoutManager;
    public int mScrolledToPosition;

    private LinkagePagerContainer customPagerContainer;
    //    private LinkagePager pager;
    private AppBarLayout appBarLayout;
    private int parallaxHeight;
    private View tab;
    boolean initialflag;


    public static int PAGES = 0;
    // You can choose a bigger number for LOOPS, but you know, nobody will fling
    // more than 1000 times just in order to test your "infinite" ViewPager :D
    public static int LOOPS = 10;
    public static int FIRST_PAGE;

    public InfinitePagerAdapter infinitePagerAdapter;
    public InfiniteHotPagerAdapter infinitehotPagerAdapter;
    public ViewPager pager;
    public ViewPager hotpager;

    public static int HotPAGES = 0;
    // You can choose a bigger number for LOOPS, but you know, nobody will fling
    // more than 1000 times just in order to test your "infinite" ViewPager :D
    public static int HotLOOPS = 10;
    public static int HotFIRST_PAGE;

    boolean mPhoto = true;
    boolean mVideo = false;
    SwipeRefreshLayout mSwipeRefreshLayout;
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
        setContentView(R.layout.activity_home);
        initComponent();
        mPresenter.setView(this);
        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        Log.i("Device", "device size" + getResources().getString(R.string.size));
        setSupportActionBar(mToolbar);
//        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mModuleList = mPresenter.getModules();
        mHotModuleList = mPresenter.getHotModules();

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);


        for (int i = 0; i <= 1000; i++) {
            mFinalModuleList.addAll(mModuleList);
        }


        PAGES = mModuleList.size();
        FIRST_PAGE = PAGES * LOOPS / 2;



        HotPAGES = mHotModuleList.size();
        HotFIRST_PAGE = HotPAGES * HotLOOPS / 2;

        pager = (ViewPager) findViewById(R.id.infiniteviewpager);

        infinitePagerAdapter = new InfinitePagerAdapter(this, this.getSupportFragmentManager());
        pager.setAdapter(infinitePagerAdapter);
        pager.setPageTransformer(false, infinitePagerAdapter);

        // Set current item to the middle page so we can fling to both
        // directions left and right

        Log.e("FirstPage:",FIRST_PAGE+"");
        pager.setCurrentItem(FIRST_PAGE);

        // Necessary or the pager will only have one extra page to show
        // make this at least however many pages you can see
        pager.setOffscreenPageLimit(3);

        // Set margin for pages as a negative number, so a part of next and
        // previous pages will be showed
//        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
//        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            // Do something for lollipop and above versions
//            pager.setPageMargin(-700);
//        } else {
////         do something for phones running an SDK before lollipop
//            pager.setPageMargin(-400);
//        }
        if (getResources().getString(R.string.size).equals("mdpi"))
            pager.setPageMargin(-600);
        else if (getResources().getString(R.string.size).equals("hdpi"))
            pager.setPageMargin(-650);
        else if (getResources().getString(R.string.size).equals("xhdpi"))
            pager.setPageMargin(-650);
        else if (getResources().getString(R.string.size).equals("xxhdpi"))
            pager.setPageMargin(-650);
        Log.i("screen size", "screen" + getResources().getString(R.string.dimens));
        try {
            if (getIntent().getExtras().getString("status").equalsIgnoreCase("clear"))
                mPresenter.setClearPrefs();
        } catch (Exception e) {
            e.printStackTrace();
        }


        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i("position", "onPageScrolled" + position);
                if (!initialflag) {
                    label.setText(mModuleList.get((position % mModuleList.size())).getTitle());
                    mPresenter.getArticles(mModuleList.get((position % mModuleList.size())).getModuleid());
                }
            }

            @Override
            public void onPageSelected(int position) {
                Log.i("position", "onPageSelected" + position);
                mScrolledToPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i("position", "onPageScrollStateChanged" + state);
                Log.i("position", "mScrolledToPosition" + mScrolledToPosition);

                if (state == 0) {
                    label.setText(mModuleList.get((mScrolledToPosition % mModuleList.size())).getTitle());
                    mPresenter.getArticles(mModuleList.get((mScrolledToPosition % mModuleList.size())).getModuleid());
                }
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                label.setText(mModuleList.get((mScrolledToPosition % mModuleList.size())).getTitle());
                mPresenter.getArticles(mModuleList.get((mScrolledToPosition % mModuleList.size())).getModuleid());
            }
        });

        hotpager = (ViewPager) findViewById(R.id.infinitehotviewpager);
        infinitehotPagerAdapter = new InfiniteHotPagerAdapter(this, this.getSupportFragmentManager());
        hotpager.setAdapter(infinitehotPagerAdapter);
        hotpager.setPageTransformer(false, infinitehotPagerAdapter);

        // Set current item to the middle page so we can fling to both
        // directions left and right
        hotpager.setCurrentItem(HotFIRST_PAGE);

        // Necessary or the pager will only have one extra page to show
        // make this at least however many pages you can see
        hotpager.setOffscreenPageLimit(3);

        // Set margin for pages as a negative number, so a part of next and
        // previous pages will be showed
        hotpager.setPageMargin(0);


        LinkagePagerContainer mContainer = (LinkagePagerContainer) findViewById(R.id.pager_container);

        final LinkagePager pager = mContainer.getViewPager();

        final PagerAdapter adapter = new MyPagerAdapter();

        pager.setAdapter(adapter);

        pager.setOffscreenPageLimit(3);

        pager.setClipChildren(false);

        pager.setPageMargin(20);

        pager.setPageTransformer(false, new LinkageCoverTransformer(0.3f, 0f, 0f, 0f));
        mScrolledToPosition = mFinalModuleList.size() / 2;
        Log.e("Position:",""+mScrolledToPosition++);
       // pager.setCurrentItem(mScrolledToPosition++);
        pager.setCurrentItem(FIRST_PAGE);
//        label.setText(mFinalModuleList.get((mScrolledToPosition % mFinalModuleList.size())).getTitle());
//        mPresenter.getArticles(mFinalModuleList.get((mScrolledToPosition % mFinalModuleList.size())).getModuleid());
        pager.addOnPageChangeListener(new LinkagePager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i("position", "onPageScrolled" + position);
                if (!initialflag) {
                    label.setText(mFinalModuleList.get((position + 1 % mFinalModuleList.size())).getTitle());
                    mPresenter.getArticles(mFinalModuleList.get((position + 1 % mFinalModuleList.size())).getModuleid());
                    mScrolledToPosition = position + 1;
                }
            }

            @Override
            public void onPageSelected(int position) {
                Log.i("position", "onPageSelected" + position);
                mScrolledToPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i("position", "onPageScrollStateChanged" + state);
                Log.i("position", "mScrolledToPosition" + mScrolledToPosition);

                if (state == 0) {
                    label.setText(mFinalModuleList.get((mScrolledToPosition % mFinalModuleList.size())).getTitle());
                    mPresenter.getArticles(mFinalModuleList.get((mScrolledToPosition % mFinalModuleList.size())).getModuleid());
                }
            }
        });
        try {
            Log.i("Noti", "Noti" + getIntent().getExtras().getString(Constants.TAG_Notification));
            Log.i("Noti", "Noti" + getIntent().getExtras().getString(Constants.TAG_Article_ID));
            Log.i("Noti", "Noti" + getIntent().getExtras().getString(Constants.TAG_Module_ID));
            if (!getIntent().getExtras().getString(Constants.TAG_Module_ID).equalsIgnoreCase(null) && getIntent().getExtras().getString(Constants.TAG_Module_ID).length() != 0) {
                Intent intent = new Intent(this, DetailActivity.class);
                intent.putExtra("article_id", getIntent().getExtras().getString(Constants.TAG_Article_ID));
                intent.putExtra("module_id", getIntent().getExtras().getString(Constants.TAG_Module_ID));
                intent.putExtra("type", "home");
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.imgLoadMore)
    public void onLoadMoreClick() {
        Intent intent =
                new Intent(HomeActivity.this, ArticlesActivity.class);
        intent.putExtra("index", mFinalModuleList.size());
        intent.putExtra("module_id", mFinalModuleList.get(mScrolledToPosition % mFinalModuleList.size()).getModuleid());
        intent.putExtra("order_id", mFinalModuleList.get(mScrolledToPosition % mFinalModuleList.size()).getOrderid());
        intent.putExtra("module_name", mFinalModuleList.get(mScrolledToPosition % mFinalModuleList.size()).getTitle());
        startActivity(intent);
    }

    @OnClick(R.id.imgHome)
    public void onHomeClick() {
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
                new Intent(HomeActivity.this, GridHomeActivity.class);
        intent.putExtra("index", i);
        startActivity(intent);
    }

    private void initAdapter(List<ResponseItem> responseItem) {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mArticleAdapter = new ArticlesAdapter(this, this, responseItem);
        mRecyclerView.setAdapter(mArticleAdapter);
        mArticleAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
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

    private void initComponent() {
        mComponent = DaggerHomeComponent.builder()
                .appComponent(getApplicationComponent())
                .homeModule(new HomeModule(this))
                .build();
        mComponent.inject(this);
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
                //showDialog();
                Layscroll.scrollTo(0,Layscroll.getTop());
                Utils.getSearchQuery(HomeActivity.this,etSearch,btnDoSearch,ll_searchView);
                break;
            case R.id.action_add:
                if (mPresenter.getUserDetails() == null)
                    goToLogin();
                else
                    showModuleVideoPhoto(null, 0);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void goToLogin() {
        Intent intent =
                new Intent(HomeActivity.this, LoginActivity.class);
        intent.putExtra("from", Constants.HOME);
        startActivity(intent);
    }

    @Override
    public void setDataToAdapter(List<ResponseList> response) {
        this.mModuleList = response;
        mAdapter.setData(mModuleList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setArticleAdapterNotify(List<ResponseItem> responseItem) {
        initialflag = true;
        Layscroll.setVisibility(View.VISIBLE);

        initAdapter(responseItem);
    }

    @Override
    public void setLoadMoredata(GetArticleResponse responseBody) {

    }

    @Override
    public void setLoadMoredata(SearchResponse responseBody) {

    }

    @Override
    public void onItemClick(String articleid, String modid) {
        Intent intent = new Intent(HomeActivity.this, DetailActivity.class);
        intent.putExtra("article_id", articleid);
        intent.putExtra("module_id", mFinalModuleList.get(mScrolledToPosition % mFinalModuleList.size()).getModuleid());
//        intent.putExtra("order_id", mFinalModuleList.get(mScrolledToPosition % mFinalModuleList.size()).getOrderid());
        intent.putExtra("type", "home");
        startActivity(intent);
    }

    @Override
    public void showUpdatedDetails(String module_id, String entityid) {
//        for (ModuleList ml : mHotModuleList) {
//            if (ml.getModid().equals(module_id)) {
        Intent intent = new Intent(HomeActivity.this, DetailActivity.class);
        intent.putExtra("article_id", entityid);
        intent.putExtra("module_id", module_id);
        intent.putExtra("type", "home");
        startActivity(intent);
        finish();
//            }
//        }

    }




    class MyListPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mModuleList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            DataDemoView view = new DataDemoView(HomeActivity.this);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private class MyPagerAdapter extends PagerAdapter {
        private int[] images = {R.drawable.img_1,
                R.drawable.img_2,
                R.drawable.img_3,
                R.drawable.img_4,
                R.drawable.img_5,
                R.drawable.img_6,
                R.drawable.img_7,
                R.drawable.img_8,
                R.drawable.img_9,
                R.drawable.img_10,
                R.drawable.img_11,
                R.drawable.img_12,
                R.drawable.img_13,
                R.drawable.img_14,
                R.drawable.img_15,
                R.drawable.img_16,
                R.drawable.img_17,
                R.drawable.img_18,
        };

        public Integer getItem(int i) {
            return images[i - 1];
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = new ImageView(HomeActivity.this);
            Log.i("position", "position" + position);
            view.setImageResource(getItem(Integer.parseInt(mFinalModuleList.get(position).getOrderid())));
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mFinalModuleList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }
    }


    private class BottomPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.item_cover, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.image_cover);
            TextView label = (TextView) view.findViewById(R.id.label);
            Picasso.with(HomeActivity.this).load(mHotModuleList.get(position).getPicpath()).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            label.setText(mHotModuleList.get(position).getTitle());
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mHotModuleList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }
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
                        Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
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
            wlp.width = WindowManager.LayoutParams.FILL_PARENT;
            wlp.verticalMargin = .070f;
            wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(wlp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gotoHome(View v) {

    }


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
                    AppDialogManager.ModuleChooserDialog(HomeActivity.this,HomeActivity.this,mPresenter.getModules());
                }
            });

            mDialog = builder.create();
            mDialog.setCancelable(true);
            mDialog.show();
           /* mEdtModule.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    AppDialogManager.ModuleChooserDialog(HomeActivity.this,HomeActivity.this,mPresenter.getModules());
                    return true;
                }
            });*/
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
                uploading = ProgressDialog.show(HomeActivity.this, "Uploading File", "Please wait...", false, false);
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
       AppDialogManager.catagoryChooserDialog(HomeActivity.this,HomeActivity.this,catagoryListPojo.getCatagoryList());
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FIRST_PAGE=0;
    }
}
