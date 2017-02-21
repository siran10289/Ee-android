package com.eeyuva.screens.profile.userdetails;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eeyuva.R;
import com.eeyuva.screens.profile.model.ProfileResponse;

/**
 * Created by hari on 03/10/16.
 */
public class TabFragment1 extends Fragment {
    private IFragmentToActivity mCallback;

    TextView mTxtName;
    TextView mTxtLastName;
    TextView mTxtMobile;
    TextView mTxtGender;
    TextView mTxtDOB;
    TextView mTxtAboutMe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frgment_tab_one, container, false);
        mTxtName = (TextView) view.findViewById(R.id.mTxtName);
        mTxtLastName = (TextView) view.findViewById(R.id.mTxtLastName);
        mTxtMobile = (TextView) view.findViewById(R.id.mTxtMobile);
        mTxtGender = (TextView) view.findViewById(R.id.mTxtGender);
        mTxtDOB = (TextView) view.findViewById(R.id.mTxtDOB);
        mTxtAboutMe = (TextView) view.findViewById(R.id.mTxtAboutMe);
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

    public void onRefresh(ProfileResponse response) {
        mTxtName.setText(response.getmProfileList().get(0).getFname());
        mTxtLastName.setText(response.getmProfileList().get(0).getLname());

        mTxtMobile.setText(response.getmProfileList().get(0).getMobile());
        if(response.getmProfileList().get(0).getGender().equalsIgnoreCase("1")){
            mTxtGender.setText("Male");
        }else if(response.getmProfileList().get(0).getGender().equalsIgnoreCase("0")){
            mTxtGender.setText("Female");
        }

        mTxtDOB.setText(response.getmProfileList().get(0).getDob());
        mTxtAboutMe.setText(response.getmProfileList().get(0).getAboutMe());
    }

}
