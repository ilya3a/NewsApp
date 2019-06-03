package com.yoyo.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.labo.kaji.fragmentanimations.PushPullAnimation;
import com.yoyo.newsapp.news_data_models.Article;

public class ShowNewsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public ShowNewsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ShowNewsFragment newInstance(String jArticle) {
        ShowNewsFragment fragment = new ShowNewsFragment();
        Bundle args = new Bundle();
        args.putString("article", jArticle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_show_news, container, false);

        ImageView articleIV = rootView.findViewById(R.id.articleIv);
        TextView articleTitle = rootView.findViewById(R.id.aricle_titleTV);
        TextView articleContent = rootView.findViewById(R.id.article_description);
        TextView articaleAuthor = rootView.findViewById(R.id.authorTV);
        TextView articaleSource = rootView.findViewById(R.id.sourceTV);
        TextView articalelink = rootView.findViewById(R.id.linkTV);


        final Article article = new Gson().fromJson(getArguments().getString("article"), Article.class);
        Glide.with(this).load(article.getUrlToImage()).into(articleIV);
        articleTitle.setText(article.getTitle());
        articleContent.setText(article.getContent());
        articaleAuthor.setText(article.getAuthor());
        articaleSource.setText(article.getPublishedAt());
        articalelink.setText(article.getUrl());
        articalelink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(article.getUrl()));
                startActivity(intent);
            }
        });



        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            return PushPullAnimation.create(PushPullAnimation.DOWN, enter, 1000);
        } else {
            return PushPullAnimation.create(PushPullAnimation.UP, enter, 1000);

        }
    }
}
