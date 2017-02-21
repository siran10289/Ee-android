package com.eeyuva.screens.gridpages;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eeyuva.R;
import com.eeyuva.screens.gridpages.model.PhotoList;
import com.eeyuva.screens.gridpages.model.UserNewsList;
import com.eeyuva.utils.Utils;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.internal.Util;

/**
 * Created by hari on 17/09/16.
 */
public class UserNewsListAdapter extends RecyclerView.Adapter<UserNewsListAdapter.ViewHolder> {
    List<UserNewsList> mModuleList = new ArrayList<UserNewsList>();
    Context mContext;
    GridContract.AdapterCallBack mAdapterCallBack;


    public UserNewsListAdapter(GridContract.AdapterCallBack mAdapterCallBack, Context context, List<UserNewsList> response) {
        this.mAdapterCallBack = mAdapterCallBack;
        this.mContext = context;
        this.mModuleList = response;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_usernews_details, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final UserNewsList rl = mModuleList.get(position);
        Picasso.with(mContext).load(rl.getPicpath()).placeholder(mContext.getResources().getDrawable(R.drawable.user_default)).into(holder.mImgItem);
        holder.mImgItem.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.mImgLabel.setText(rl.getTitle());
        holder.txtSubDesc.setText("Posted By : "+rl.getUsername());
        holder.txtDate.setText(Utils.getISOTime(rl.getDate()));
        holder.Layuserdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterCallBack.setSelectItem(rl);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mModuleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImgItem;
        public TextView mImgLabel;
        public TextView txtSubDesc;
        public TextView txtDate;
        public LinearLayout Layuserdetails;

        public ViewHolder(View v) {
            super(v);
            mImgItem = (ImageView) v.findViewById(R.id.imgArticle);
            mImgLabel = (TextView) v.findViewById(R.id.txtTitle);
            txtSubDesc = (TextView) v.findViewById(R.id.txtSubDesc);
            txtDate = (TextView) v.findViewById(R.id.txtDate);
            Layuserdetails = (LinearLayout) v.findViewById(R.id.Layuserdetails);
        }
    }
    private String getISOTime(String time)  {
        try {
            if(!time.equalsIgnoreCase("N/A")&&time.length()!=0) {
                Date date = new Date();
                DateFormat format = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
                simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
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

    private int[] images = {R.drawable.gimg1,
            R.drawable.gimg2,
            R.drawable.gimg3,
            R.drawable.gimg4,
            R.drawable.img_5,
            R.drawable.gimg6,
            R.drawable.gimg7,
            R.drawable.gimg8,
            R.drawable.gimg9,
            R.drawable.gimg10,
            R.drawable.gimg11,
            R.drawable.gimg12,
            R.drawable.gimg13,
            R.drawable.gimg14,
            R.drawable.gimg15,
            R.drawable.gimg16,
            R.drawable.gimg17,
            R.drawable.gimg18,
    };

    public Integer getItem(int i) {
        return images[i - 1];
    }
}
