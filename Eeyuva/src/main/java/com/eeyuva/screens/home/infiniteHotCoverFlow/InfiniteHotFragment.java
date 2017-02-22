package com.eeyuva.screens.home.infiniteHotCoverFlow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eeyuva.R;
import com.eeyuva.screens.DetailPage.DetailActivity;
import com.eeyuva.screens.DetailPage.infiniteOtherCoverFlow.InfiniteOtherFragment;
import com.eeyuva.screens.home.HomeActivity;
import com.eeyuva.screens.home.loadmore.ArticlesActivity;
import com.squareup.picasso.Picasso;

public class InfiniteHotFragment extends Fragment {

    private CommmunicateListener commmunicateListener;

    public static Fragment newInstance(HomeActivity context, int pos, float scale) {
        Bundle b = new Bundle();
        b.putInt("pos", pos);
        b.putFloat("scale", scale);
        return Fragment.instantiate(context, InfiniteHotFragment.class.getName(), b);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        LinearLayout l = (LinearLayout)inflater.inflate(R.layout.hot_mf, container, false);

        final int pos = this.getArguments().getInt("pos");
//        ImageView tv = (ImageView) l.findViewById(R.id.hotimage);
//        Log.i("InfiniteHotFragmentpos","InfiniteHotFragmentpos"+pos);
////        tv.setImageResource(getItem(Integer.parseInt(HomeActivity.mHotModuleList.get(pos).getOrderid())));
//        Picasso.with(getActivity()).load(HomeActivity.mHotModuleList.get((pos % HomeActivity.mHotModuleList.size())).getPicpath()).into(tv);
//        tv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageView imageView = (ImageView) l.findViewById(R.id.hotimage);
        TextView label = (TextView) l.findViewById(R.id.label);
        TextView labeltitle = (TextView) l.findViewById(R.id.labeltitle);
        Picasso.with(getActivity()).load(HomeActivity.mHotModuleList.get((pos % HomeActivity.mHotModuleList.size())).getPicpath()).placeholder(l.getResources().getDrawable(R.drawable.ic_big_y_logo)).into(imageView);
        //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        label.setText(getSubString(HomeActivity.mHotModuleList.get((pos % HomeActivity.mHotModuleList.size())).getTitle()));
        labeltitle.setText(getSubString(HomeActivity.mHotModuleList.get((pos % HomeActivity.mHotModuleList.size())).getModulename()));
        Log.e("Title:",HomeActivity.mHotModuleList.get(pos).getTitle());

        InfiniteHotLinearLayout root = (InfiniteHotLinearLayout) l.findViewById(R.id.hotroot);
        float scale = this.getArguments().getFloat("scale");
        root.setScaleBoth(scale);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                commmunicateListener.showUpdatedDetails(HomeActivity.mHotModuleList.get(pos % HomeActivity.mHotModuleList.size()).getModid(),HomeActivity.mHotModuleList.get(pos % HomeActivity.mHotModuleList.size()).getEntityid());
            }
        });
        return l;
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


    private String getSubString(String summary) {
        if (summary.length() > 100)
            return summary.substring(0, 100) + "...";
        return summary;
    }

    public interface CommmunicateListener {

        void showUpdatedDetails(String articleid, String entityid);


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            commmunicateListener = (InfiniteHotFragment.CommmunicateListener) activity;
        } catch (Exception e) {
        }
    }
}
