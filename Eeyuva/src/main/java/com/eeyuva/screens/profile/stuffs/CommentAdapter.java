package com.eeyuva.screens.profile.stuffs;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eeyuva.R;
import com.eeyuva.screens.home.loadmore.RoundedTransformation;
import com.eeyuva.screens.profile.ProfileContract;
import com.eeyuva.screens.profile.model.AlertList;
import com.eeyuva.screens.profile.model.CommentList;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by hari on 08/10/16.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<CommentList> mAlertList;
    private Context mContext;
    private ProfileContract.AdapterCallBack mAdapterCallBack;

    public CommentAdapter(ProfileContract.AdapterCallBack AdapterCallBack, Context Context, List<CommentList> alertList) {
        this.mContext = Context;
        this.mAlertList = alertList;
        this.mAdapterCallBack = AdapterCallBack;
    }

    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_alert_details, parent, false);
        CommentAdapter.ViewHolder vh = new CommentAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CommentList articles = mAlertList.get(position);
        try {

            holder.txtTitle.setText(articles.getArtitle());
            holder.txtType.setText(articles.getModulename());
            holder.txtType.setTextColor(mContext.getResources().getColor(R.color.home_dark_pink));
//            if (articles.getStatus().equalsIgnoreCase("inactive")) {
//                holder.txtSubDesc.setText("Inactive");
//                holder.txtSubDesc.setTextColor(mContext.getResources().getColor(R.color.dialog_red));
//            } else {
//                holder.txtSubDesc.setText("Active");
//                holder.txtSubDesc.setTextColor(mContext.getResources().getColor(R.color.dialog_green));
//            }
            holder.txtSubDesc.setVisibility(View.GONE);
            holder.txtDate.setText("View Comment");
            Picasso.with(mContext).load(articles.getArtimg()).placeholder(mContext.getResources().getDrawable(R.drawable.y_logo)).transform(new RoundedTransformation(8, 0)).resize(80, 80).into(holder.imgArticle);
            holder.txtDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAdapterCallBack.getComments(articles.getModuleid(), articles.getArtid());
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

        public ViewHolder(View v) {
            super(v);
            txtTitle = (TextView) v.findViewById(R.id.txtTitle);
            txtSubDesc = (TextView) v.findViewById(R.id.txtSubDesc);
            txtDate = (TextView) v.findViewById(R.id.txtDate);
            txtType = (TextView) v.findViewById(R.id.txtType);
            imgArticle = (ImageView) v.findViewById(R.id.imgArticle);


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
