package com.eeyuva.screens.home.hotNewsCoverFlow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eeyuva.R;
import com.eeyuva.screens.home.ModuleList;
import com.eeyuva.screens.home.ResponseList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HotNewsCoverFlowAdapter extends BaseAdapter {

    private List<ModuleList> mData = new ArrayList<ModuleList>();
    private Context mContext;

    public HotNewsCoverFlowAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<ModuleList> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public Integer getItem(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.item_hotcoverflow, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) rowView.findViewById(R.id.label);
            viewHolder.image = (ImageView) rowView
                    .findViewById(R.id.image);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        try {
            Picasso.with(mContext).load(mData.get(position).getPicpath()).placeholder(mContext.getResources().getDrawable(R.drawable.ic_big_y_logo)).into(holder.image);
            holder.text.setText(mData.get(position).getModulename());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowView;
    }


    static class ViewHolder {
        public TextView text;
        public ImageView image;
    }

}
