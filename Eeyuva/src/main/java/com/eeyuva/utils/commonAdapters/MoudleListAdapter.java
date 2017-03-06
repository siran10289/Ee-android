package com.eeyuva.utils.commonAdapters;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eeyuva.R;
import com.eeyuva.screens.home.ResponseList;
import com.eeyuva.utils.listeners.DialogListener;

import java.util.List;

/**
 * Created by Aximsoft on 12/15/2016.
 */

public class MoudleListAdapter extends RecyclerView.Adapter<MoudleListAdapter.DataObjectHolder> {
    private List<ResponseList> alModules;
    private Context mContext;
    private ResponseList modulePojo;
    private DialogListener dialogListener;
    private Dialog dialogModuleList;


    public MoudleListAdapter(Context context, List<ResponseList> alModules, Dialog dialogModuleList, DialogListener dialogListener) {
        this.mContext=context;
        this.alModules=alModules;
        this.dialogListener=dialogListener;
        this.dialogModuleList=dialogModuleList;

    }

    class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView tv_moduleName;
        LinearLayout ll_modulename;

        DataObjectHolder(View itemView) {
            super(itemView);
            tv_moduleName = (TextView) itemView.findViewById(R.id.tv_moduleName);
            ll_modulename=(LinearLayout)itemView.findViewById(R.id.ll_modulename);
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_module, parent, false);
        return new DataObjectHolder(view);
    }

    //Binding the data
    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        modulePojo = alModules.get(position);
        holder.tv_moduleName.setText(modulePojo.getTitle());
        holder.ll_modulename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogModuleList.dismiss();
                dialogListener.onDialogClosedByOkClick(alModules.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return alModules.size();
    }

}
