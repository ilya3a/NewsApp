package com.yoyo.newsapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.labo.kaji.fragmentanimations.PushPullAnimation;
import com.yoyo.newsapp.news_data_models.Article;
import com.yoyo.newsapp.news_data_models.ModelNews;

import java.util.ArrayList;
import java.util.Objects;

public class NewsListFragment extends Fragment {

    final String NEWS_BASE_URL = "https://newsapi.org/v2/everything?q=";//israel;
    final String API_KEY = "&apiKey=7c46ec14171146958d70df2056a62308";

    com.southernbox.parallaxrecyclerview.ParallaxRecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    ArrayList<Article> jArticles = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    Context context;
    int position = 0;
    final String SHARED_DATA = "data";
    final String LAST_ARTICALS = "last";


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    public static NewsListFragment newInstance(ArrayList<String> subscriptions) {
        NewsListFragment newsListFragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("subscriptions", subscriptions);
        newsListFragment.setArguments(bundle);
        return newsListFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.news_list_fragment, container, false);

        recyclerView = rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewAdapter(jArticles, context);

        swipeRefreshLayout = rootView.findViewById(R.id.refresh);
        swipeRefreshLayout.setColorSchemeColors(Color.GREEN, Color.RED);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateData(getArguments().getStringArrayList("subscriptions"));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2 * 1000);

            }
        });
        swipeRefreshLayout.setRefreshing(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        sharedPreferences = context.getSharedPreferences(SHARED_DATA, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        ArrayList<String> subscriptionList = getArguments().getStringArrayList("subscriptions");
        adapter.setArticles(jArticles);
        updateData(subscriptionList);


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        String json = sharedPreferences.getString(LAST_ARTICALS, null);
        if (json != null) {
            jArticles = new Gson().fromJson(json, new TypeToken<ArrayList<Article>>() {
            }.getType());
            adapter.setArticles(jArticles);
            updateData(getArguments().getStringArrayList("subscriptions"));
        }
    }

    public void updateData(ArrayList<String> subscriptionList) {

        jArticles.removeAll(jArticles);

        final RequestQueue queue = Volley.newRequestQueue(Objects.requireNonNull(context));
        if (!subscriptionList.isEmpty()) {
            for (String requestedNews : subscriptionList) {
                StringRequest request = new StringRequest(NEWS_BASE_URL + requestedNews + API_KEY, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ModelNews news = new Gson().fromJson(response, ModelNews.class);
                        ArrayList<Article> articles = news.getArticles();


                        jArticles.addAll(articles);
                        for (Article article : articles) {
                            if (article.getAuthor() == null | article.getUrlToImage() == null | article.getContent() == null | article.getTitle() == null) {
                                jArticles.remove(article);
                            }
                        }
                        adapter.setArticles(jArticles);
                        editor.putString(LAST_ARTICALS, new Gson().toJson(jArticles)).commit();
                        swipeRefreshLayout.setRefreshing(false);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(context, "No internet connection = No news", Toast.LENGTH_SHORT).show();
                        if (getView() != null) {
                            final Snackbar snackbar = Snackbar.make(getView(), "No internet for getting the News", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }

                    }
                });
                queue.add(request);
            }
//            queue.start();
        } else {
            adapter.setArticles(jArticles);
            editor.putString(LAST_ARTICALS, new Gson().toJson(jArticles)).commit();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        editor.putString(LAST_ARTICALS, new Gson().toJson(jArticles)).commit();

    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            return PushPullAnimation.create(PushPullAnimation.UP, enter, 1000);
        } else {
            return PushPullAnimation.create(PushPullAnimation.DOWN, enter, 1000);

        }
    }

}
