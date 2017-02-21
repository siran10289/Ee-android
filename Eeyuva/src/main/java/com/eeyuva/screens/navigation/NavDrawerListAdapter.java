package com.eeyuva.screens.navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.eeyuva.R;

import java.util.List;

/**
 */
public class NavDrawerListAdapter extends ArrayAdapter {

    private final String LOG_TAG = this.getClass().getSimpleName();

    private Context mContext;
    private List<AppSetting> navDrawerItems;

    public NavDrawerListAdapter(Context context, List<AppSetting> navDrawerItems) {
        super(context, R.layout.navigation_drawer_list_item);
        this.mContext = context;
        this.navDrawerItems = navDrawerItems;
    }

    @Override
    public int getCount() {
        return navDrawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return navDrawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.navigation_drawer_list_item, null);

        }
        TextView txtTitle = (TextView) convertView.findViewById(R.id.titleTextView);
        TextView htxtTitle = (TextView) convertView.findViewById(R.id.headtitleTextView);
        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.iconImageView);
        ImageView himgIcon = (ImageView) convertView.findViewById(R.id.headiconImageView);
        LinearLayout itemlay = (LinearLayout) convertView.findViewById(R.id.itemLay);
        RelativeLayout headLay = (RelativeLayout) convertView.findViewById(R.id.headLay);

        if (!navDrawerItems.get(position).isHeader()) {
            itemlay.setVisibility(View.VISIBLE);
            headLay.setVisibility(View.GONE);
            if (navDrawerItems.get(position).icon == 0) {
                imgIcon.setVisibility(View.GONE);
            } else {
                imgIcon.setVisibility(View.VISIBLE);
                imgIcon.setBackgroundResource(navDrawerItems.get(position).icon);
            }
            txtTitle.setText(navDrawerItems.get(position).title);
            txtTitle.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            if (navDrawerItems.get(position).icon == 0) {
                himgIcon.setVisibility(View.GONE);
            } else {
                himgIcon.setVisibility(View.VISIBLE);
                himgIcon.setBackgroundResource(navDrawerItems.get(position).icon);
            }
            htxtTitle.setText(navDrawerItems.get(position).title);
            htxtTitle.setTextColor(mContext.getResources().getColor(R.color.red));
            itemlay.setVisibility(View.GONE);
            headLay.setVisibility(View.VISIBLE);
        }
        return convertView;
    }
}