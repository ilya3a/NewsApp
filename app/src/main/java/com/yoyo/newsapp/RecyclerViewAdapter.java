package com.yoyo.newsapp;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yoyo.newsapp.news_data_models.Article;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    interface RecyclerCallBack {
        void onItemClicked(Article article);
    }

    RecyclerCallBack recyclerCallBack;

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    ArrayList<Article> articles;
    Context mContext;

    public RecyclerViewAdapter(ArrayList<Article> articles, Context mContext) {
        this.articles = articles;
        this.mContext = mContext;
        recyclerCallBack = (RecyclerCallBack) mContext;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_card_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

        final Article article = articles.get(i);


        Glide.with(mContext).load(article.getUrlToImage()).thumbnail(0.1f).into(viewHolder.imageView);
        viewHolder.descriptionTV.setText(article.getDescription());
        viewHolder.parentLayout.setTag(i);
        viewHolder.headerTV.setText(article.getTitle());
        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerCallBack.onItemClicked(article);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView descriptionTV, headerTV;
        CardView parentLayout;

        public ViewHolder(final View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_card);
            descriptionTV = itemView.findViewById(R.id.card_descrip);
            parentLayout = itemView.findViewById(R.id.parent_card);
            headerTV = itemView.findViewById(R.id.card_header);
        }
    }
}
