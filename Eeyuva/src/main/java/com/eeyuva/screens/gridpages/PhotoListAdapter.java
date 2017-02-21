package com.eeyuva.screens.gridpages;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eeyuva.R;
import com.eeyuva.screens.gridpages.model.PhotoList;
import com.eeyuva.screens.home.ResponseList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hari on 17/09/16.
 */
public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.ViewHolder> {
    List<PhotoList> mModuleList = new ArrayList<PhotoList>();
    Context mContext;
    GridContract.AdapterCallBack mAdapterCallBack;

    public PhotoListAdapter(GridContract.AdapterCallBack mAdapterCallBack, Context context, List<PhotoList> mModuleList) {
        Log.i("GridLoadAdapter", "GridLoadAdapter called");
        this.mAdapterCallBack = mAdapterCallBack;
        this.mContext = context;
        this.mModuleList = mModuleList;
        Log.i("GridLoadAdapter", "GridLoadAdapter called" + mModuleList.size());

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_cover, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final PhotoList rl = mModuleList.get(position);
        Picasso.with(mContext)
                .load(rl.getGallerypic())
                .placeholder(mContext.getResources().getDrawable(R.drawable.ic_small_y_logo))
                 .into(holder.mImgItem);
        holder.mImgLabel.setText(rl.getTitle());
        holder.mImgItem.setOnClickListener(new View.OnClickListener() {
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

        public ViewHolder(View v) {
            super(v);
            mImgItem = (ImageView) v.findViewById(R.id.image_cover);
            mImgLabel = (TextView) v.findViewById(R.id.label);
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
