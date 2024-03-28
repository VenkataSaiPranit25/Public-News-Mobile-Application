package edu.niu.android.newsapp;

import java.util.List;

import edu.niu.android.newsapp.Models.NewsHeadlines;

public interface OnFetchDataListener<NewsApiResponse> {
    void onFetchData(List<NewsHeadlines> list, String message);
    void onError(String message);
}
