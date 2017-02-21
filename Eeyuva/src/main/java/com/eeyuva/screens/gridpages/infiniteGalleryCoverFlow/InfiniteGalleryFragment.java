package com.eeyuva.screens.gridpages.infiniteGalleryCoverFlow;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eeyuva.R;
import com.eeyuva.screens.gridpages.PhotoGalleryActivity;
import com.eeyuva.screens.gridpages.ScaleImageView;
import com.eeyuva.screens.home.HomeActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;
import uk.co.senab.photoview.log.LogManager;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;

public class InfiniteGalleryFragment extends Fragment {

    public static Fragment newInstance(PhotoGalleryActivity context, int pos, float scale) {
        Bundle b = new Bundle();
        b.putInt("pos", pos);
        b.putFloat("scale", scale);
        return Fragment.instantiate(context, InfiniteGalleryFragment.class.getName(), b);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        LinearLayout l = (LinearLayout)
                inflater.inflate(R.layout.gallery_mf, container, false);

        int pos = this.getArguments().getInt("pos");


        PhotoView imageView = (PhotoView) l.findViewById(R.id.hotimage);
        TextView label = (TextView) l.findViewById(R.id.label);
        final TextView labeltitle = (TextView) l.findViewById(R.id.labeltitle);
//        imageView.setImageBitmap(getBitmapFromURL(PhotoGalleryActivity.mHotModuleList.get((pos % PhotoGalleryActivity.mHotModuleList.size())).getPicpath()));
//        Picasso.with(getActivity()).load(PhotoGalleryActivity.mHotModuleList.get((pos % PhotoGalleryActivity.mHotModuleList.size())).getPicpath()).placeholder(getActivity().getResources().getDrawable(R.drawable.logo_big)).into(imageView);
//        imageView.setScaleType(ScaleImageView.ScaleType.FIT_XY);
        try {
            final PhotoViewAttacher attacher = new PhotoViewAttacher(imageView);

            Picasso.with(getActivity())
                    .load(PhotoGalleryActivity.mHotModuleList.get((pos % PhotoGalleryActivity.mHotModuleList.size())).getPicpath())
                    .placeholder(getActivity().getResources().getDrawable(R.drawable.ic_big_y_logo))
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.i("callback","callbacksuccess");
                            attacher.update();
                            labeltitle.setVisibility(View.INVISIBLE);
                        }


                        @Override
                        public void onError() {
                            Log.i("callback","callbackerror");
                            labeltitle.setVisibility(View.VISIBLE);


                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            labeltitle.setVisibility(View.VISIBLE);
        }

        label.setText(getSubString(PhotoGalleryActivity.mHotModuleList.get((pos % PhotoGalleryActivity.mHotModuleList.size())).getTitle()));

        InfiniteGalleryLinearLayout root = (InfiniteGalleryLinearLayout) l.findViewById(R.id.hotroot);
        float scale = this.getArguments().getFloat("scale");
        root.setScaleBoth(scale);

        return l;
    }

    private String getSubString(String summary) {
        if (summary.length() > 100)
            return summary.substring(0, 100) + "...";
        return summary;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    public interface CommmunicateListener {

        void showUpdatedDetails(String articleid, String entityid);


    }
}
