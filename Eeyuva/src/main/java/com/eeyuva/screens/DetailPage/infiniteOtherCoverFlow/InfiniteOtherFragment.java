package com.eeyuva.screens.DetailPage.infiniteOtherCoverFlow;

import android.app.Activity;
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
import com.eeyuva.screens.home.HomeActivity;
import com.eeyuva.screens.home.infiniteHotCoverFlow.InfiniteHotLinearLayout;
import com.squareup.picasso.Picasso;

public class InfiniteOtherFragment extends Fragment {

    private CommmunicateListener commmunicateListener;

    public static Fragment newInstance(DetailActivity context, int pos, float scale) {
        Bundle b = new Bundle();
        b.putInt("pos", pos);
        b.putFloat("scale", scale);
        return Fragment.instantiate(context, InfiniteOtherFragment.class.getName(), b);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        LinearLayout l = (LinearLayout)
                inflater.inflate(R.layout.other_mf, container, false);

        final int pos = this.getArguments().getInt("pos");
        ImageView imageView = (ImageView) l.findViewById(R.id.hotimage);
        TextView label = (TextView) l.findViewById(R.id.label);
        TextView labeltitle = (TextView) l.findViewById(R.id.labeltitle);
        Picasso.with(getActivity()).load(DetailActivity.mHotModuleList.get((pos % DetailActivity.mHotModuleList.size())).getGalleryimg()).placeholder(l.getResources().getDrawable(R.drawable.ic_big_y_logo)).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        label.setText(getSubString(DetailActivity.mHotModuleList.get((pos % DetailActivity.mHotModuleList.size())).getTitle()));
        labeltitle.setText(getSubString(DetailActivity.mHotModuleList.get((pos % DetailActivity.mHotModuleList.size())).getModulename()));

        InfiniteOtherLinearLayout root = (InfiniteOtherLinearLayout) l.findViewById(R.id.hotroot);
        float scale = this.getArguments().getFloat("scale");
        root.setScaleBoth(scale);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commmunicateListener.showUpdatedDetails(DetailActivity.mHotModuleList.get((pos % DetailActivity.mHotModuleList.size())).getArticleid());
                Log.i("onclick", "onclick" + DetailActivity.mHotModuleList.get((pos % DetailActivity.mHotModuleList.size())).getArticleid());
                Log.i("onclick", "onclick" + DetailActivity.mHotModuleList.get((pos % DetailActivity.mHotModuleList.size())).getModulename());
            }
        });
        return l;
    }

    private String getSubString(String summary) {
        if (summary.length() > 100)
            return summary.substring(0, 100) + "...";
        return summary;
    }


    public interface CommmunicateListener {

        void showUpdatedDetails(String articleid);


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            commmunicateListener = (CommmunicateListener) activity;
        } catch (Exception e) {
        }
    }
}
