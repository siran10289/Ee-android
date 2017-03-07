package com.eeyuva.screens.DetailPage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.eeyuva.ButterAppCompatActivity;
import com.eeyuva.R;
import com.eeyuva.di.component.DaggerDetailComponent;
import com.eeyuva.di.component.DetailComponent;
import com.eeyuva.di.module.DetailModule;
import com.eeyuva.screens.DetailPage.infiniteOtherCoverFlow.InfiniteOtherFragment;
import com.eeyuva.screens.DetailPage.infiniteOtherCoverFlow.InfiniteOtherPagerAdapter;
import com.eeyuva.screens.DetailPage.model.CommentsList;
import com.eeyuva.screens.Upload;
import com.eeyuva.screens.authentication.LoginActivity;
import com.eeyuva.screens.gridpages.GridHomeActivity;
import com.eeyuva.screens.gridpages.PhotoListAdapter;
import com.eeyuva.screens.home.CatagoryList;
import com.eeyuva.screens.home.HomeActivity;
import com.eeyuva.screens.home.ImageResponse;
import com.eeyuva.screens.home.ResponseList;
import com.eeyuva.screens.home.loadmore.ArticlesLoadAdapter;
import com.eeyuva.screens.home.loadmore.RoundedTransformation;
import com.eeyuva.screens.navigation.FragmentDrawer;
import com.eeyuva.screens.profile.userdetails.ProfileActivity;
import com.eeyuva.screens.searchpage.SearchActivity;
import com.eeyuva.utils.AppDialogManager;
import com.eeyuva.utils.Constants;
import com.eeyuva.utils.Utils;
import com.eeyuva.utils.customdialog.DialogListener;
import com.eeyuva.utils.customdialog.DialogUtils;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import me.crosswall.lib.coverflow.core.PagerContainer;

import static com.eeyuva.screens.home.HomeActivity.mModuleList;

/**
 * Created by hari on 14/09/16.
 */
public class DetailActivity extends ButterAppCompatActivity implements DetailContract.View, InfiniteOtherFragment.CommmunicateListener, com.eeyuva.utils.listeners.DialogListener {
    @Inject
    DetailContract.Presenter mPresenter;

    DetailComponent mComponent;

    @Bind(R.id.tool_bar)
    Toolbar mToolbar;

    @Bind(R.id.mTxtModuleName)
    TextView mTxtModuleName;

    @Bind(R.id.mTxtArticleTitle)
    TextView mTxtArticleTitle;

    @Bind(R.id.mTxtTimeInfo)
    TextView mTxtTimeInfo;

    @Bind(R.id.mTxtDetailInfo)
    WebView mTxtDetailInfo;

    @Bind(R.id.mImgModuleImg)
    ImageView mImgModuleImg;

    @Bind(R.id.mImgArticleImg)
    ImageView mImgArticleImg;

    private FragmentDrawer drawerFragment;

    private String mModuleId;
    private String mArticleId;
    private String mModuleName;
    private String mOrderId;
    public static List<ArticleDetail> mHotModuleList = new ArrayList<ArticleDetail>();


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

    @Bind(R.id.mBtnLike)
    TextView mBtnLike;

    @Bind(R.id.mBtnComment)
    TextView mBtnComment;

    @Bind(R.id.mBtnDislike)
    TextView mBtnDislike;

    @Bind(R.id.mBtnViewComment)
    TextView mBtnViewComment;

    @Bind(R.id.mBtnShare)
    TextView mBtnShare;

    @Bind(R.id.layScroll)
    LinearLayout layScroll;

    ArticleDetail mArticleDetail;
    private int type;
    private int mScrolledToPosition;
    public List<ResponseList> mModuleList = new ArrayList<ResponseList>();
    private String mEntityId;

    @Bind(R.id.scrollView)
    ScrollView mScrollView;
    public String mType;
    private String[] mArticleImgList;
    PagerContainer container;
    ViewPager pager;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.search_view)
    LinearLayout ll_searchView;
    @Bind(R.id.btnOk)
    Button btnDoSearch;
    @Bind(R.id.btnSearch)
    EditText etSearch;
    private String moduleID,categorayID;
    EditText mEdtModule;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        initComponent();
        mPresenter.setView(this);

        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);


        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);


        try {
            mType = getIntent().getExtras().getString("type");

            if (mType.equals("home") || mType.equals("search")) {
                mModuleId = getIntent().getExtras().getString("module_id");
                mArticleId = getIntent().getExtras().getString("article_id");
                mPresenter.getArticlesDetails(mModuleId, mArticleId);
                mPresenter.getOtherArticlesDetails(mModuleId, mArticleId);
            } else if (mType.equals("usernews")) {
                mModuleId = getIntent().getExtras().getString("module_id");
                mArticleId = getIntent().getExtras().getString("article_id");
                mEntityId = getIntent().getExtras().getString("entity_id");
                mPresenter.getArticlesDetails(mArticleId);
                mPresenter.getOtherArticlesDetails(mModuleId, mArticleId, mEntityId);
            } else if (mType.equals("news")) {
                mModuleId = getIntent().getExtras().getString("module_id");
                mArticleId = getIntent().getExtras().getString("article_id");
                mEntityId = getIntent().getExtras().getString("entity_id");
                mPresenter.getArticlesNewsDetails(mArticleId);
                mPresenter.getOtherArticlesDetails(mModuleId, mArticleId, mEntityId);
            } else if (mType.equals("notification")) {
                mModuleId = getIntent().getExtras().getString("module_id");
                mArticleId = getIntent().getExtras().getString("article_id");
                mPresenter.getArticlesDetails(mModuleId, mArticleId);
                mPresenter.getOtherArticlesDetails(mModuleId, mArticleId);
            }


            mModuleList = mPresenter.getModules();
            if (mModuleList.size() != 0) {
                for (ResponseList rl : mModuleList) {
                    if (rl.getModuleid().equals(mModuleId)) {
                        mOrderId = rl.getOrderid();
                        mModuleName = rl.getTitle();
                    }
                }
            }
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (mType.equals("home") || mType.equals("search")) {
                        mPresenter.getArticlesDetails(mModuleId, mArticleId);
                        mPresenter.getOtherArticlesDetails(mModuleId, mArticleId);
                    } else if (mType.equals("usernews")) {
                        mPresenter.getArticlesDetails(mArticleId);
                        mPresenter.getOtherArticlesDetails(mModuleId, mArticleId, mEntityId);
                    } else if (mType.equals("news")) {
                        mPresenter.getArticlesNewsDetails(mArticleId);
                        mPresenter.getOtherArticlesDetails(mModuleId, mArticleId, mEntityId);
                    } else if (mType.equals("notification")) {
                        mPresenter.getArticlesDetails(mModuleId, mArticleId);
                        mPresenter.getOtherArticlesDetails(mModuleId, mArticleId);
                    }
                }
            });
            mTxtModuleName.setText(mModuleName);
            mImgModuleImg.setImageResource(getItem(Integer.parseInt(mOrderId)));

            container = (PagerContainer) findViewById(R.id.pager_container);
            pager = container.getViewPager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View view = LayoutInflater.from(DetailActivity.this).inflate(R.layout.item_detail_cover, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.image_cover);
//            imageView.setImageDrawable(getResources().getDrawable(mArticleImgList.covers[position]));
            Log.i("mArticleImgList", "mArticleImgList" + mArticleImgList[position]);
            Picasso.with(DetailActivity.this).load(mArticleImgList[position]).placeholder(getResources().getDrawable(R.drawable.ic_big_y_logo)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mArticleImgList.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }
    }


    private void initComponent() {
        mComponent = DaggerDetailComponent.builder()
                .appComponent(getApplicationComponent())
                .detailModule(new DetailModule(this))
                .build();
        mComponent.inject(this);
    }

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

    public InfiniteOtherPagerAdapter infinitehotPagerAdapter;

    public ViewPager hotpager;

    public static int HotPAGES = 0;
    // You can choose a bigger number for LOOPS, but you know, nobody will fling
    // more than 1000 times just in order to test your "infinite" ViewPager :D
    public static int HotLOOPS = 100;
    public static int HotFIRST_PAGE;

    @Override
    public void setArticleDetails(ArticleDetail articleDetail) {
        try {
            mSwipeRefreshLayout.setRefreshing(false);

            mArticleDetail = articleDetail;
            mTxtArticleTitle.setText(articleDetail.getTitle());
            mArticleImgList = articleDetail.getGalleryimg().split(",");
            Log.i("mArticleImgList", "mArticleImgList" + mArticleImgList.length);

            container = (PagerContainer) findViewById(R.id.pager_container);
            pager = container.getViewPager();
            pager.setAdapter(new MyPagerAdapter());
            pager.setClipChildren(false);
            pager.setOffscreenPageLimit(15);
            String posted = "Posted by: ";
            String mOn = " on ";
            String complete = "Posted by: " + articleDetail.getCreatedby() + " on " + Utils.getISOTime(articleDetail.getCreateddate());

            SpannableString styledString = new SpannableString("Posted by: " + articleDetail.getCreatedby() + " on " + Utils.getISOTime(articleDetail.getCreateddate()));
            styledString.setSpan(new ForegroundColorSpan(Color.RED), 0, posted.length(), 0);
            styledString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), posted.length(), posted.length() + articleDetail.getCreatedby().length(), 0);
            styledString.setSpan(new ForegroundColorSpan(Color.RED), posted.length() + articleDetail.getCreatedby().length(), complete.length(), 0);
            mTxtTimeInfo.setText(styledString);
//            Picasso.with(this).load(articleDetail.getGalleryimg()).placeholder(getResources().getDrawable(R.drawable.ic_big_y_logo)).into(mImgArticleImg);
            mTxtDetailInfo.getSettings().setJavaScriptEnabled(true);
            mTxtDetailInfo.loadDataWithBaseURL("", articleDetail.getSummary(), "text/html", "UTF-8", "");
            if (!articleDetail.getLikecount().equalsIgnoreCase("0") && !articleDetail.getLikecount().equalsIgnoreCase(null) && articleDetail.getLikecount().length() != 0)
                mBtnLike.setText("Like(" + articleDetail.getLikecount() + ")");
            else
                mBtnLike.setText("Like");
            if (!articleDetail.getDislikecount().equalsIgnoreCase("0") && !articleDetail.getDislikecount().equalsIgnoreCase(null) && articleDetail.getDislikecount().length() != 0)
                mBtnDislike.setText("Dislike(" + articleDetail.getDislikecount() + ")");
            else
                mBtnDislike.setText("Dislike");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setOtherArticleDetails(List<ArticleDetail> response) {
        try {
            mSwipeRefreshLayout.setRefreshing(false);

            mHotModuleList = response;
            HotPAGES = mHotModuleList.size();
            HotFIRST_PAGE = HotPAGES * HotLOOPS / 2;
            hotpager = (ViewPager) findViewById(R.id.infinitehotviewpager);
            infinitehotPagerAdapter = new InfiniteOtherPagerAdapter(this, this.getSupportFragmentManager());
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
            hotpager.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });

            hotpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    Log.i("position", "onPageSelected" + position);
                    mScrolledToPosition = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.layScroll)
    public void LayOnclick() {

        Log.i("onclick", "onclick" + mHotModuleList.get(hotpager.getCurrentItem()).getArticleid());
        Log.i("onclick", "onclick" + mHotModuleList.get(hotpager.getCurrentItem()).getModulename());
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

    @OnClick(R.id.imgHome)
    public void onHomeClick() {
        Intent intent =
                new Intent(DetailActivity.this, HomeActivity.class);
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
                new Intent(DetailActivity.this, GridHomeActivity.class);
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
               // showDialog();
                mScrollView.scrollTo(0,mScrollView.getTop());
                Utils.getSearchQuery(DetailActivity.this,etSearch,btnDoSearch,ll_searchView);
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
                new Intent(DetailActivity.this, LoginActivity.class);
        intent.putExtra("from", Constants.DETAIL);
        startActivity(intent);
    }

    AlertDialog mDialog;

    public void showDialog() {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
                //return;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.search_dialog));
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
                                new Intent(DetailActivity.this, SearchActivity.class);
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

    @OnClick(R.id.mBtnShare)
    public void onShareClick() {
        if (mPresenter.getUserDetails() == null)
            goToLogin();
        else
            showRecommendShareDialog();
    }

    @OnClick(R.id.mBtnLike)
    public void onLikeClick() {
        if (mPresenter.getUserDetails() == null)
            goToLogin();
        else
            showLikeAndDislikeDialog(1);
    }

    @OnClick(R.id.mBtnDislike)
    public void onDislikeClick() {
        if (mPresenter.getUserDetails() == null)
            goToLogin();
        else
            showLikeAndDislikeDialog(2);
    }

    @OnClick(R.id.mBtnComment)
    public void onDialogCommentClick() {
        if (mPresenter.getUserDetails() == null)
            goToLogin();
        else
            showCommentDialog();
    }

    public void showRecommendShareDialog() {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_recommend_share, null);
            builder.setView(dialogView);

            TextView mTxtRecommend = (TextView) dialogView.findViewById(R.id.mTxtRecommend);
            TextView mTxtShare = (TextView) dialogView.findViewById(R.id.mTxtShare);
            TextView mTxtCancel = (TextView) dialogView.findViewById(R.id.mTxtCancel);

            mTxtRecommend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    showShareDialog();

                }
            });
            mTxtShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, mArticleDetail.getTitle() + "\n" + mArticleDetail.getShareLink());
                    startActivity(Intent.createChooser(sharingIntent, "Share via"));


                }
            });
            mTxtCancel.setOnClickListener(new View.OnClickListener() {
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

    public void showShareDialog() {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_share, null);
            builder.setView(dialogView);

            RelativeLayout LayRating = (RelativeLayout) dialogView.findViewById(R.id.LayRating);
            Button mBtnok = (Button) dialogView.findViewById(R.id.btnOk);
            Button mBtnCancel = (Button) dialogView.findViewById(R.id.btnCancel);
            TextView txtRate = (TextView) dialogView.findViewById(R.id.mTxtRate);
            final EditText mail = (EditText) dialogView.findViewById(R.id.mEdtMailid);
            String mRate = "Recommend";
            String start = "Do you want to ";
            String end = " this article";
            String complete = start + mRate + end;
            SpannableString styledString = new SpannableString(complete);
            styledString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), start.length(), start.length() + mRate.length(), 0);
            txtRate.setText(styledString);
            mBtnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    mPresenter.postShareDetail(mModuleId, mEntityId, mail.getText().toString().trim());

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


    @OnClick(R.id.mBtnViewComment)
    public void onDialogViewCommentClick() {
        if (mPresenter.getUserDetails() == null)
            goToLogin();
        else
            mPresenter.getViewComments(mModuleId, mArticleDetail.getArticleid());

    }

    public void showRatingDialog() {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_rating, null);
            builder.setView(dialogView);

            RelativeLayout LayRating = (RelativeLayout) dialogView.findViewById(R.id.LayRating);
            Button mBtnok = (Button) dialogView.findViewById(R.id.btnOk);
            Button mBtnCancel = (Button) dialogView.findViewById(R.id.btnCancel);
            TextView txtRate = (TextView) dialogView.findViewById(R.id.mTxtRate);
            ImageView imgRate = (ImageView) dialogView.findViewById(R.id.imgRate);
            String mRate = "Rate";
            String start = "Do you want to ";
            String end = " this article";
            String complete = start + mRate + end;
            SpannableString styledString = new SpannableString(complete);
            styledString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), start.length(), start.length() + mRate.length(), 0);
            txtRate.setText(styledString);
            mBtnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

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

    public void showLikeAndDislikeDialog(int x) {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_like, null);
            builder.setView(dialogView);

            RelativeLayout LayRating = (RelativeLayout) dialogView.findViewById(R.id.LayRating);
            Button mBtnok = (Button) dialogView.findViewById(R.id.btnOk);
            Button mBtnCancel = (Button) dialogView.findViewById(R.id.btnCancel);
            TextView txtRate = (TextView) dialogView.findViewById(R.id.mTxtRate);
            ImageView imgRate = (ImageView) dialogView.findViewById(R.id.imgRate);
            String mRate = "Rate";

            if (x == 1) {
                type = 1;
                mRate = "Like";
                imgRate.setImageResource(R.drawable.like);
                mBtnok.setText("Like");
            } else {
                type = 0;
                mRate = "Dislike";
                mBtnok.setText("Dislike");
                imgRate.setImageResource(R.drawable.dislike);
            }

            String start = "Do you want to ";
            String end = " this article";
            String complete = start + mRate + end;
            SpannableString styledString = new SpannableString(complete);
            styledString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), start.length(), start.length() + mRate.length(), 0);
            txtRate.setText(styledString);
            mBtnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    mPresenter.setLikeOrDislike(mArticleDetail.getArticleid(), "" + type, mModuleId, mArticleDetail.getCategoryid());
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


    public void showCommentDialog() {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_comment, null);
            builder.setView(dialogView);

            RelativeLayout LayRating = (RelativeLayout) dialogView.findViewById(R.id.LayRating);
            Button mBtnok = (Button) dialogView.findViewById(R.id.btnOk);
            Button mBtnCancel = (Button) dialogView.findViewById(R.id.btnCancel);
            TextView txtRate = (TextView) dialogView.findViewById(R.id.mTxtRate);
            final EditText comments = (EditText) dialogView.findViewById(R.id.mEdtMailid);

            mBtnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    mPresenter.setPostComments(comments.getText().toString().trim(), mModuleId, mArticleDetail.getArticleid());
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

    public void showViewCommentDialog() {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_view_comment, null);
            builder.setView(dialogView);

            RelativeLayout LayRating = (RelativeLayout) dialogView.findViewById(R.id.LayRating);
            topListLay = (RelativeLayout) dialogView.findViewById(R.id.topListLay);
            Button mBtnok = (Button) dialogView.findViewById(R.id.btnOk);
            Button mBtnCancel = (Button) dialogView.findViewById(R.id.btnCancel);
            txtRate = (TextView) dialogView.findViewById(R.id.mTxtRate);
            mNoComment = (TextView) dialogView.findViewById(R.id.mNoComment);
            final EditText comments = (EditText) dialogView.findViewById(R.id.mEdtMailid);

            mlistview = (RecyclerView) dialogView.findViewById(R.id.orderlist);

            mBtnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    mPresenter.setPostComments(comments.getText().toString().trim(), mModuleId, mArticleDetail.getArticleid());

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
    TextView mNoComment;
    RelativeLayout topListLay;
    TextView txtRate;
    RecyclerView.LayoutManager mLayoutManager;
    CommentsLoadAdapter mCommentsAdapter;

    @Override
    public void setCommentsListToAdapter(List<CommentsList> response) {
        if (response != null) {
            showViewCommentDialog();
            mlistview.setHasFixedSize(true);
            mlistview.setVisibility(View.VISIBLE);
            topListLay.setVisibility(View.VISIBLE);
            txtRate.setVisibility(View.VISIBLE);
            mLayoutManager = new GridLayoutManager(this, 1);
            mlistview.setLayoutManager(mLayoutManager);
            mCommentsAdapter = new CommentsLoadAdapter(this, response);
            mlistview.setAdapter(mCommentsAdapter);
            mCommentsAdapter.notifyDataSetChanged();
        } else {
            mNoComment.setVisibility(View.VISIBLE);
            mlistview.setVisibility(View.GONE);
        }
    }

    @Override
    public void setAdpaterNotComments() {
//        topListLay.setVisibility(View.GONE);
//        txtRate.setVisibility(View.GONE);
    }

    @Override
    public void showListenerDialog(String errorMsg) {
        DialogUtils.showDialog(this, errorMsg, getString(R.string.sig__default_dialog_action_confirm), "", new DialogListener() {
            @Override
            public void onConfirm() {
                showCommentDialog();
            }

            @Override
            public void onCancel() {
                return;
            }
        });

    }

    public void gotoHome(View v) {
        Intent intent =
                new Intent(DetailActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void showUpdatedDetails(String mArticleId) {
        this.mArticleId = mArticleId;
        mPresenter.getArticlesDetails(mModuleId, mArticleId);
        mPresenter.getOtherArticlesDetails(mModuleId, mArticleId);
        mScrollView.post(new Runnable() {
            public void run() {
                mScrollView.fullScroll(mScrollView.FOCUS_UP);
            }
        });
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
                    AppDialogManager.ModuleChooserDialog(DetailActivity.this,DetailActivity.this,mPresenter.getModules());
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

    @Override
    public void setLikeCount(Integer countLike) {
        mBtnLike.setText("Like(" + countLike + ")");
    }

    @Override
    public void setDisLikeCount(Integer countLike) {
        mBtnDislike.setText("Dislike(" + countLike + ")");
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
                uploading = ProgressDialog.show(DetailActivity.this, "Uploading File", "Please wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                uploading.dismiss();
            }

            @Override
            protected String doInBackground(Void... params) {
                Upload u = new Upload();
                String url = "http://mobile.eeyuva.com/postusernews.php?mid=4&catid=Cat_6395ebd0f&title=&desc=&uid=3939";
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
        AppDialogManager.catagoryChooserDialog(DetailActivity.this,DetailActivity.this,catagoryListPojo.getCatagoryList());
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
