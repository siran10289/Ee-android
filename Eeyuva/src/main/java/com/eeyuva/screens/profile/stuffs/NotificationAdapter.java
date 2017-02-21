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
import com.eeyuva.screens.home.loadmore.RoundedTransformation;
import com.eeyuva.screens.profile.model.AlertList;
import com.eeyuva.screens.profile.model.CommentList;
import com.eeyuva.screens.profile.model.NotificationList;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by hari on 08/10/16.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<NotificationList> mAlertList;
    private Context mContext;

    public NotificationAdapter(Context Context, List<NotificationList> alertList) {
        this.mContext = Context;
        this.mAlertList = alertList;
    }

    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_alert_details, parent, false);
        NotificationAdapter.ViewHolder vh = new NotificationAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final NotificationList articles = mAlertList.get(position);
        try {

            holder.txtTitle.setText(articles.getTitle());
            holder.txtTitle.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
            holder.txtType.setText(articles.getModuleName());
            holder.txtSubDesc.setVisibility(View.INVISIBLE);
//            holder.txtDate.setText(getISOTime(articles.()));
            Picasso.with(mContext).load(articles.getThumbimg()).placeholder(mContext.getResources().getDrawable(R.drawable.y_logo)).transform(new RoundedTransformation(8, 0)).resize(80, 80).into(holder.imgArticle);
            holder.imgArticle.setVisibility(View.GONE);
            holder.card_row_list_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =
                            new Intent(mContext, DetailActivity.class);
                    intent.putExtra("article_id", articles.getArticleid());
                    intent.putExtra("module_id", articles.getModuleId());
                    intent.putExtra("type", "notification");
                    mContext.startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SpannableString getApproveExactString(AlertList articles) {
        String admin = "Admin ";
        String approved = "approved ";
        String your = "your ";
        String on = " on ";
        String complete = admin + approved + your + articles.getMsgtype() + on + '"' + articles.getTitle() + '"';
        SpannableString styledString = new SpannableString(complete);
        styledString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, admin.length(), 0);
        styledString.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.colorAccent)), admin.length(), (admin + approved).length(), 0);
        styledString.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.colorAccent)), (admin + approved + your + articles.getMsgtype() + on).length(), complete.length(), 0);
        return styledString;
    }

    private SpannableString getRejectExactString(AlertList articles) {
        String admin = "Admin ";
        String approved = "rejected ";
        String your = "your ";
        String on = " on ";
        String complete = admin + approved + your + articles.getMsgtype() + on + '"' + articles.getTitle() + '"';
        SpannableString styledString = new SpannableString(complete);
        styledString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, admin.length(), 0);
        styledString.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.home_dark_pink)), admin.length(), (admin + approved).length(), 0);
        styledString.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.colorAccent)), (admin + approved + your + articles.getMsgtype() + on).length(), complete.length(), 0);

        return styledString;
    }


    private String getSubString(String summary) {
        if (summary.length() > 290)
            return summary.substring(0, 290) + "...";
        return summary;
    }

    @Override
    public int getItemCount() {
        return mAlertList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public TextView txtDate;
        public TextView txtType;
        public TextView txtSubDesc;
        public ImageView imgArticle;
        public CardView card_row_list_order;

        public ViewHolder(View v) {
            super(v);
            txtTitle = (TextView) v.findViewById(R.id.txtTitle);
            txtSubDesc = (TextView) v.findViewById(R.id.txtSubDesc);
            txtDate = (TextView) v.findViewById(R.id.txtDate);
            txtType = (TextView) v.findViewById(R.id.txtType);
            imgArticle = (ImageView) v.findViewById(R.id.imgArticle);
            card_row_list_order = (CardView) v.findViewById(R.id.card_row_list_order);

        }
    }

    private String getISOTime(String time) {
        try {
            if (!time.equalsIgnoreCase("N/A") && time.length() != 0) {
                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("MMM d, yyyy");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
                simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
                date = simpleDateFormat.parse(time);
                format.setTimeZone(simpleDateFormat.getTimeZone());
                date.getTime();
                return format.format(date);
            } else {
                return "N/A";
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}
