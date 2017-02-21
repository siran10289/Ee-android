package com.eeyuva.screens.profile.notification;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.eeyuva.R;
import com.eeyuva.screens.home.ResponseList;
import com.eeyuva.screens.home.loadmore.RoundedTransformation;
import com.eeyuva.screens.profile.ProfileContract;
import com.eeyuva.screens.profile.model.AlertList;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by hari on 08/10/16.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {


    public List<ResponseList> mAlertList;
    private Context mContext;
    private ProfileContract.AdapterCallBack mCallback;

    public NotificationAdapter(ProfileContract.AdapterCallBack callback, Context Context, List<ResponseList> alertList) {
        this.mCallback = callback;
        this.mContext = Context;
        this.mAlertList = alertList;
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

    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_notification_details, parent, false);
        NotificationAdapter.ViewHolder vh = new NotificationAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ResponseList articles = mAlertList.get(position);
        try {
            holder.txtTitle.setText(articles.getTitle());
            holder.imgArticle.setImageResource(getItem(Integer.parseInt(articles.getOrderid())));
            holder.mCheckBox.setChecked(mAlertList.get(position).isSelected());
            holder.mCheckBox.setTag(mAlertList.get(position));
            holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    ResponseList rs = (ResponseList) cb.getTag();
                    rs.setSelected(cb.isChecked());
                    mAlertList.get(position).setSelected(cb.isChecked());
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

    public void setCheckedItem(String notificationModules) {
        String[] modules = notificationModules.split(",");
        for (String id : modules) {
            getResult(getPosition(id)).setSelected(true);
            notifyDataSetChanged();
        }
    }

    public ResponseList getResult(int position) {
        return mAlertList.
                get(position);
    }

    private int getPosition(String id) {
        int k = -1;
        for (int i = 0; i < mAlertList.size(); i++) {
            if (mAlertList.get(i).getModuleid().equals(id))
                k = i;
        }
        return k;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public ImageView imgArticle;
        public CheckBox mCheckBox;

        public ViewHolder(View v) {
            super(v);
            txtTitle = (TextView) v.findViewById(R.id.txtTitle);
            imgArticle = (ImageView) v.findViewById(R.id.imgArticle);
            mCheckBox = (CheckBox) v.findViewById(R.id.mCheckBox);

        }
    }

    public List<ResponseList> getAlertList() {
        return mAlertList;
    }


}
