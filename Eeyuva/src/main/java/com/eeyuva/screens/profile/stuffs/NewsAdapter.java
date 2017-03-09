package com.eeyuva.screens.profile.stuffs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eeyuva.R;
import com.eeyuva.screens.DetailPage.DetailActivity;
import com.eeyuva.screens.home.HomeActivity;
import com.eeyuva.screens.home.loadmore.RoundedTransformation;
import com.eeyuva.screens.profile.model.AlertList;
import com.eeyuva.screens.profile.model.CommentList;
import com.eeyuva.screens.profile.model.NewsList;
import com.eeyuva.screens.profile.model.NewsResponse;
import com.eeyuva.utils.customdialog.DialogListener;
import com.eeyuva.utils.customdialog.DialogUtils;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by hari on 08/10/16.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<NewsList> mAlertList;
    private Context mContext;
    private MyArticleDeleteListener myArticleDeleteListener;

    public NewsAdapter(Context Context, List<NewsList> alertList,MyArticleDeleteListener listener) {
        this.mContext = Context;
        this.mAlertList = alertList;
        this.myArticleDeleteListener=listener;
    }

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_stuff_news_details, parent, false);
        NewsAdapter.ViewHolder vh = new NewsAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final NewsList articles = mAlertList.get(position);
        try {

            holder.txtTitle.setText(articles.getTitle());
            holder.txtType.setText(articles.getModulename());
            holder.txtType.setTextColor(mContext.getResources().getColor(R.color.home_dark_pink));
            if (articles.getStatus().equalsIgnoreCase("0")) {
                holder.txtPublish.setText("UnPublished");
                holder.txtPublish.setBackground(mContext.getResources().getDrawable(R.drawable.publish_btn));
               // holder.txtEdit.setText("Edit");
                //holder.txtEdit.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_corner_home_pink));
                holder.txtPublish.setVisibility(View.VISIBLE);
                //holder.txtEdit.setVisibility(View.VISIBLE);
                holder.imgDelete.setVisibility(View.VISIBLE);
            } else if (articles.getStatus().equalsIgnoreCase("1")) {
                holder.txtPublish.setText("Published");
                holder.txtPublish.setBackground(mContext.getResources().getDrawable(R.drawable.dialog_green_btn));
                holder.txtPublish.setVisibility(View.VISIBLE);
               // holder.txtEdit.setVisibility(View.GONE);
                holder.imgDelete.setVisibility(View.GONE);
            } else if (articles.getStatus().equalsIgnoreCase("2")) {
                holder.txtPublish.setText("Rejected");
                holder.txtPublish.setBackground(mContext.getResources().getDrawable(R.drawable.dialog_red_btn));
                holder.txtPublish.setVisibility(View.VISIBLE);
               // holder.txtEdit.setVisibility(View.GONE);
                holder.imgDelete.setVisibility(View.GONE);
            }
            holder.txtSubDesc.setVisibility(View.INVISIBLE);
            Picasso.with(mContext).load(articles.getPicpath()).placeholder(mContext.getResources().getDrawable(R.drawable.y_logo)).transform(new RoundedTransformation(8, 0)).resize(80, 80).into(holder.imgArticle);
            holder.card_row_list_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    intent.putExtra("article_id", articles.getArticleid());
                    intent.putExtra("module_id", articles.getModuleid());
                    intent.putExtra("entity_id", articles.getEntityid());
                    intent.putExtra("type", "news");
                    mContext.startActivity(intent);
                }
            });
            holder.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAlerDialogForDelete("Are you sure?",articles.getArticleid());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mAlertList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public TextView txtPublish;
        public TextView txtType;

        public TextView txtSubDesc;
        public ImageView imgArticle;
        public ImageView imgDelete;
        public CardView card_row_list_order;
        public ViewHolder(View v) {
            super(v);
            txtTitle = (TextView) v.findViewById(R.id.txtTitle);
            txtSubDesc = (TextView) v.findViewById(R.id.txtSubDesc);
            txtPublish = (TextView) v.findViewById(R.id.txtPublish);

            txtType = (TextView) v.findViewById(R.id.txtType);
            imgArticle = (ImageView) v.findViewById(R.id.imgArticle);
            imgDelete = (ImageView) v.findViewById(R.id.imgDelete);
            card_row_list_order = (CardView) v.findViewById(R.id.card_row_list_order);

        }
    }
    public void showAlerDialogForDelete(String errorMsg, final String articleID) {
        DialogUtils.showDialog(mContext, errorMsg, mContext.getString(R.string.sig__default_dialog_action_confirm), "", new DialogListener() {
            @Override
            public void onConfirm() {
               myArticleDeleteListener.deleteArticle(articleID);
            }

            @Override
            public void onCancel() {
                return;
            }
        });

    }

}
