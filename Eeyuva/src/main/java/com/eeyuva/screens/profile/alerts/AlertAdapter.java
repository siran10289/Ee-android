package com.eeyuva.screens.profile.alerts;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eeyuva.R;
import com.eeyuva.screens.home.loadmore.RoundedTransformation;
import com.eeyuva.screens.profile.ProfileContract;
import com.eeyuva.screens.profile.model.AlertList;
import com.eeyuva.utils.Utils;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by hari on 08/10/16.
 */
public class AlertAdapter extends RecyclerView.Adapter<AlertAdapter.ViewHolder> {

    private List<AlertList> mAlertList;
    private Context mContext;
    private ProfileContract.AdapterCallBack mCallback;

    public AlertAdapter(ProfileContract.AdapterCallBack callback, Context Context, List<AlertList> alertList) {
        this.mCallback = callback;
        this.mContext = Context;
        this.mAlertList = alertList;
    }

    @Override
    public AlertAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_alert_details, parent, false);
        AlertAdapter.ViewHolder vh = new AlertAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final AlertList articles = mAlertList.get(position);
        Log.e("Status:",articles.getStatus());
        try {
           if(articles.getMsgtype().equalsIgnoreCase("Comment")) {
               if (articles.getStatus().equals("inactive")) {
                   holder.txtTitle.setText("Your comment on "+articles.getTitle()+" is under Review");
               } else if (articles.getStatus().equals("active")) {
                   holder.txtTitle.setText("Your comment on "+articles.getTitle()+" has been published");
               }else {
                   holder.txtTitle.setText("Your comment on "+articles.getTitle()+" has been rejected");
               }
           }else if(articles.getMsgtype().equalsIgnoreCase("Usernews")){
               if (articles.getStatus().equals("inactive")) {
                   holder.txtTitle.setText("Your article "+articles.getTitle()+ " is under Review");
               } else if (articles.getStatus().equals("active")) {
                   holder.txtTitle.setText("Your article "+articles.getTitle()+ " has been published");
               }else {
                   holder.txtTitle.setText("Your article "+articles.getTitle()+ " has been rejected");
               }

           }

            holder.txtType.setText(articles.getModuleName());
            holder.txtDate.setText(Utils.getISOTime(articles.getDate()));
            Picasso.with(mContext).load(articles.getThumbimg()).placeholder(mContext.getResources().getDrawable(R.drawable.y_logo)).transform(new RoundedTransformation(8, 0)).resize(80, 80).into(holder.imgArticle);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   /* private SpannableString getApproveExactString(AlertList articles) {
        String admin = "Admin ";
        String approved = "active";
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
        String approved = "inactive";
        String your = "your ";
        String on = " on ";
        String complete = admin + approved + your + articles.getMsgtype() + on + '"' + articles.getTitle() + '"';
        SpannableString styledString = new SpannableString(complete);
        styledString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, admin.length(), 0);
        styledString.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.home_dark_pink)), admin.length(), (admin + approved).length(), 0);
        styledString.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.colorAccent)), (admin + approved + your + articles.getMsgtype() + on).length(), complete.length(), 0);

        return styledString;
    }*/


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
//                DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
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
