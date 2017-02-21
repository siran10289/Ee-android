package com.eeyuva.screens.profile.stuffs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eeyuva.R;
import com.eeyuva.screens.profile.model.CommentResponse;
import com.eeyuva.screens.profile.model.NewsResponse;
import com.eeyuva.screens.profile.userdetails.IFragmentToActivity;
import com.eeyuva.utils.customdialog.DialogListener;
import com.eeyuva.utils.customdialog.DialogUtils;

/**
 * Created by hari on 03/10/16.
 */
public class NewsFrgament extends Fragment {
    private IFragmentToActivity mCallback;

    RecyclerView mRecyclerView;

    NewsAdapter mCommentAdapter;

    RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frgment_news, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.orderlist);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (IFragmentToActivity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement IFragmentToActivity");
        }
    }

    @Override
    public void onDetach() {
        mCallback = null;
        super.onDetach();
    }

    public void onRefresh(NewsResponse response) {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity(), 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mCommentAdapter = new NewsAdapter(getActivity(), response.getAlertList());
        mRecyclerView.setAdapter(mCommentAdapter);
        mCommentAdapter.notifyDataSetChanged();
    }


}
