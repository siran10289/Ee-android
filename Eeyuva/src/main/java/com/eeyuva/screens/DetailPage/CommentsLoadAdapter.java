package com.eeyuva.screens.DetailPage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eeyuva.R;
import com.eeyuva.screens.DetailPage.model.CommentsList;
import com.eeyuva.screens.home.HomeContract;
import com.eeyuva.screens.home.ResponseItem;
import com.eeyuva.screens.home.coverflow.ArticlesAdapter;
import com.eeyuva.screens.home.loadmore.RoundedTransformation;
import com.eeyuva.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hari on 18/09/16.
 */
public class CommentsLoadAdapter extends RecyclerView.Adapter<CommentsLoadAdapter.ViewHolder> {

    private List<CommentsList> mCommentList = new ArrayList<CommentsList>();
    private Context mContext;
    private DetailContract.AdapterCallBack mAdapterCallBack;

    public CommentsLoadAdapter(Context context, List<CommentsList> responseItem) {
        this.mContext = context;
        this.mCommentList = responseItem;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_comment_details, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CommentsList comments = mCommentList.get(position);
        try {
            holder.txtTitle.setText(comments.getCommentby());
            holder.txtSubDesc.setText(comments.getCommentDescription());
            holder.txtDate.setText(Utils.getISOTime(comments.getCommentDate()));
//            holder.txtStatus.setText(comments.getCommentDate());
            if (comments.getStatus().equalsIgnoreCase("delete")) {
                holder.txtStatus.setText("UnPublished");
                holder.txtStatus.setBackground(mContext.getResources().getDrawable(R.drawable.publish_btn));
            } else {
                holder.txtStatus.setText("Published");
                holder.txtStatus.setBackground(mContext.getResources().getDrawable(R.drawable.dialog_green_btn));
            }
//            Picasso.with(mContext).load(articles.getPicpath()).transform(new RoundedTransformation(8, 0)).resize(80, 80).into(holder.imgArticle);
            holder.txtSubDesc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getSubString(String summary) {
        if (summary.length() > 190)
            return summary.substring(0, 190) + "...";
        return summary;
    }

    @Override
    public int getItemCount() {
        return mCommentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public TextView txtSubDesc;
        public TextView txtDate;
        public TextView txtStatus;
        public ImageView imgArticle;

        public ViewHolder(View v) {
            super(v);
            txtTitle = (TextView) v.findViewById(R.id.txtTitle);
            txtSubDesc = (TextView) v.findViewById(R.id.txtSubDesc);
            txtDate = (TextView) v.findViewById(R.id.txtDate);
            imgArticle = (ImageView) v.findViewById(R.id.imgArticle);
            txtStatus = (TextView) v.findViewById(R.id.txtStatus);

        }
    }
}
