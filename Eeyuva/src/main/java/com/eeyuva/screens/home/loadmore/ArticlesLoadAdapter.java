package com.eeyuva.screens.home.loadmore;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eeyuva.R;
import com.eeyuva.screens.home.HomeContract;
import com.eeyuva.screens.home.ResponseItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hari on 09/09/16.
 */
public class ArticlesLoadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ResponseItem> mArticlesList = new ArrayList<ResponseItem>();
    static Context mContext;

    public final int TYPE_ARTICLE = 0;
    public final int TYPE_LOAD = 1;

    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;
    HomeContract.AdapterCallBack mAdapterCallBack;


    public ArticlesLoadAdapter(HomeContract.AdapterCallBack AdapterCallBack, Context context, List<ResponseItem> responseItem) {
        this.mContext = context;
        this.mArticlesList = responseItem;
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
        if (viewType == TYPE_ARTICLE) {
            return new MovieHolder(inflater.inflate(R.layout.view_article_details, parent, false));
        } else {
            return new LoadHolder(inflater.inflate(R.layout.row_load, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (!mArticlesList.get(position).getLoadtype()) {
            return TYPE_ARTICLE;
        } else {
            return TYPE_LOAD;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (position >= getItemCount() - 1 && isMoreDataAvailable && !isLoading && loadMoreListener != null) {
            isLoading = true;
            loadMoreListener.onLoadMore();
        }

        if (getItemViewType(position) == TYPE_ARTICLE) {
            ((MovieHolder) holder).bindData(mArticlesList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mArticlesList.size();
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
            txtSubDesc = (TextView) v.findViewById(R.id.txtSubDesc);
            imgArticle = (ImageView) v.findViewById(R.id.imgArticle);
            mLayArticle = (LinearLayout) v.findViewById(R.id.mLayArticle);
        }

        void bindData(final ResponseItem articles) {
            txtTitle.setText(articles.getTitle());
            txtSubDesc.setText(Html.fromHtml(getSubString(articles.getSummary())));
            Picasso.with(mContext).load(articles.getThumbimg()).placeholder(mContext.getResources().getDrawable(R.drawable.ic_big_y_logo)).transform(new RoundedTransformation(8, 0)).resize(80, 80).into(imgArticle);
            mLayArticle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAdapterCallBack.onItemClick(articles.getArticleid(), "");
                }
            });
        }
    }

    class LoadHolder extends RecyclerView.ViewHolder {
        public LoadHolder(View itemView) {
            super(itemView);
        }
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    /* notifyDataSetChanged is final method so we can't override it
         call adapter.notifyDataChanged(); after update the list
         */
    public void notifyDataChanged() {
        notifyDataSetChanged();
        isLoading = false;
    }


    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    private String getSubString(String summary) {
        if (summary.length() > 190)
            return summary.substring(0, 190) + "...";
        return summary;
    }
}
