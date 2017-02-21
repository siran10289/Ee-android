package com.eeyuva.screens.searchpage;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eeyuva.R;
import com.eeyuva.screens.home.HomeContract;
import com.eeyuva.screens.home.ResponseItem;
import com.eeyuva.screens.home.loadmore.RoundedTransformation;
import com.eeyuva.screens.searchpage.model.Doc;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hari on 09/09/16.
 */
public class SearchLoadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Doc> mDocsList = new ArrayList<Doc>();
    static Context mContext;

    public final int TYPE_ARTICLE = 0;
    public final int TYPE_LOAD = 1;

    //    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;
    HomeContract.AdapterCallBack mAdapterCallBack;


    public SearchLoadAdapter(HomeContract.AdapterCallBack AdapterCallBack, Context context, List<Doc> mDocsList) {
        this.mContext = context;
        this.mDocsList = mDocsList;
        this.mAdapterCallBack = AdapterCallBack;

    }


//    @Override
//    public ArticlesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
////        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_article_details, parent, false);
////        ViewHolder vh = new ViewHolder(v);
//
//        LayoutInflater inflater = LayoutInflater.from(mContext);
//
//        if(viewType==TYPE_MOVIE){
//            return new MovieHolder(inflater.inflate(R.layout.view_article_details,parent,false));
//        }else{
//            return new LoadHolder(inflater.inflate(R.layout.row_load,parent,false));
//        }
//    }

    //    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        final ResponseItem articles = mArticlesList.get(position);
//        try {
//            holder.txtTitle.setText(articles.getTitle());
//            holder.txtSubDesc.setText(articles.getSummary());
//            Picasso.with(mContext).load(articles.getPicpath()).resize(80, 80).into(holder.imgArticle);
//
//        }catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
//        if (viewType == TYPE_ARTICLE) {
        return new MovieHolder(inflater.inflate(R.layout.view_article_details, parent, false));
//        }
    }

    @Override
    public int getItemViewType(int position) {
        if (!mDocsList.get(position).isLoadtype()) {
            return TYPE_ARTICLE;
        } else {
            return TYPE_LOAD;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

//        if (position >= getItemCount() - 1 && isMoreDataAvailable && !isLoading && loadMoreListener != null) {
//            isLoading = true;
//            loadMoreListener.onLoadMore();
//        }

        if (getItemViewType(position) == TYPE_ARTICLE) {
            ((MovieHolder) holder).bindData(mDocsList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mDocsList.size();
    }

//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public TextView txtTitle;
//        public TextView txtSubDesc;
//        public ImageView imgArticle;
//
//        public ViewHolder(View v) {
//            super(v);
//            txtTitle = (TextView) v.findViewById(R.id.txtTitle);
//            txtSubDesc = (TextView) v.findViewById(R.id.txtSubDesc);
//            imgArticle = (ImageView) v.findViewById(R.id.imgArticle);
//        }
//    }

    /* VIEW HOLDERS */

    class MovieHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public TextView txtSubDesc;
        public ImageView imgArticle;
        public LinearLayout mLayArticle;

        public MovieHolder(View v) {
            super(v);
            txtTitle = (TextView) v.findViewById(R.id.txtTitle);
            mLayArticle = (LinearLayout) v.findViewById(R.id.mLayArticle);
            txtSubDesc = (TextView) v.findViewById(R.id.txtSubDesc);
            imgArticle = (ImageView) v.findViewById(R.id.imgArticle);
        }

        void bindData(final Doc articles) {
            txtTitle.setText(articles.getTitle().get(0));
           /* String complete = getISOTime(articles.getDate().trim());
            SpannableString styledString = new SpannableString(complete);
            styledString.setSpan(new ForegroundColorSpan(Color.RED), 0, complete.length(), 0);*/
            txtSubDesc.setText(articles.getDescription());
            Picasso.with(mContext).load("http://pics.eeyuva.com/" + articles.getImgpath()).placeholder(mContext.getResources().getDrawable(R.drawable.ic_big_y_logo)).transform(new RoundedTransformation(8, 0)).resize(80, 80).into(imgArticle);
            mLayArticle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAdapterCallBack.onItemClick(articles.getEntityid(),""+articles.getModid());
                }
            });
        }
    }


    private String getSubString(String summary) {
        if (summary.length() > 190)
            return summary.substring(0, 190) + "...";
        return summary;
    }
    private String getISOTime(String time)  {
        try {
            if(!time.equalsIgnoreCase("N/A")&&time.length()!=0) {
                Date date = new Date();
                DateFormat format = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
                simpleDateFormat.applyPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
                date = simpleDateFormat.parse(time);
                format.setTimeZone(simpleDateFormat.getTimeZone());
                date.getTime();
                return format.format(date);
            }else {
                return "N/A";
            }
        }catch (ParseException e){
            e.printStackTrace();
            return "";
        }
    }
}
