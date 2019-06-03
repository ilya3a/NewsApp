package com.yoyo.newsapp.news_data_models;

import java.util.ArrayList;

public class ModelNews {
        private String status;
        private float totalResults;
        ArrayList< Article > articles = new ArrayList <> ();

        // Getter Methods

        public String getStatus() {
            return status;
        }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public float getTotalResults() {
            return totalResults;
        }

        // Setter Methods

        public void setStatus(String status) {
            this.status = status;
        }

        public void setTotalResults(float totalResults) {
            this.totalResults = totalResults;
        }
    }