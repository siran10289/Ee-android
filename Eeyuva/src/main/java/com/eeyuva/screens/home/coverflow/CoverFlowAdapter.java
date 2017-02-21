package com.eeyuva.screens.home.coverflow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eeyuva.R;
import com.eeyuva.screens.home.ResponseList;

import java.util.ArrayList;
import java.util.List;

public class CoverFlowAdapter extends BaseAdapter {

    private List<ResponseList> mData = new ArrayList<ResponseList>();
    private Context mContext;

    public CoverFlowAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<ResponseList> data) {
        mData = data;
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
        return images[i-1];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.item_coverflow, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) rowView.findViewById(R.id.label);
            viewHolder.image = (ImageView) rowView
                    .findViewById(R.id.image);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        try {
            holder.image.setImageResource(getItem(Integer.parseInt(mData.get(position).getOrderid())));
//            holder.text.setText(mData.get(position).getTitle());
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
