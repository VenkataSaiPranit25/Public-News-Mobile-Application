package edu.niu.android.newsapp;
/**********************************************************************************************
 *                                                                                            *
 * CSCI 522                    Final Project                        Fall semester             *
 *                                                                                            *
 * App Name: News Application                                                                 *
 *                                                                                            *
 * Class Name: MainActivity.java                                                              *
 *                                                                                            *
 * Developer(s): Vamsi krishna Kasula(Z 1972818)                                              *
 *               Venkata SAi pranit Thotakura(Z 1970083)                                      *
 * Due Date: 12/01/23                                                                         *
 *                                                                                            *
 * Purpose: The Android news app seamlessly integrates with the News API to provide users     *
 *          with a user-friendly platform for exploring and reading news articles. Its main   *
 *          activity utilizes a RecyclerView to display a curated list of articles, allowing  *
 *          users to search for specific content and filter by categories through intuitive   *
 *          UI elements. The custom adapter efficiently manages data presentation, while the  *
 *          Request Manager handles network requests using Retrofit, fetching articles based  *
 *          on user queries and categories. A Details Activity enriches the experience by     *
 *          providing in-depth information about selected articles. The app's well-designed   *
 *          interface, featuring buttons, SearchView, and RecyclerView, is enhanced by the    *
 *          Picasso library for image loading. Informative toast messages offer feedback      *
 *          on network requests and data availability, ensuring a smooth and engaging         *
 *          user experience.                                                                  *
 **********************************************************************************************/
/*
References used in this application:
https://www.geeksforgeeks.org/how-to-create-a-news-app-in-android/
https://github.com/topics/newsapp?l=java
https://codecanyon.net/item/android-news-app/10771397
https://newsapi.org/
 */
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import edu.niu.android.newsapp.Models.NewsApiResponse;
import edu.niu.android.newsapp.Models.NewsHeadlines;

public class  MainActivity extends AppCompatActivity implements SelectListener, View.OnClickListener{

    RecyclerView recyclerView;
    CustomAdapter adapter;
    ProgressDialog dialog;
    Button b1,b2,b3,b4,b5,b6,b7;
    SearchView searchView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing UI components
        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // Making a request for news articles based on the query
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty()) {
                    RequestManager manager = new RequestManager(MainActivity.this);
                    manager.getNewdHeadlines(listener, "general", query);
                    dialog.setTitle("Fetching news articles of query " + query);
                    dialog.show();

                    // Clear the SearchView
                    searchView.setQuery("", false);
                }
                return true;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // Initializing progress dialog for fetching news articles
        dialog = new ProgressDialog(this);
        dialog.setTitle("Fetching news articles...");
        dialog.show();

        // Initializing category buttons and setting click listeners
        b1 = findViewById(R.id.btn_1);
        b1.setOnClickListener(this);
        b2 = findViewById(R.id.btn_2);
        b2.setOnClickListener(this);
        b3 = findViewById(R.id.btn_3);
        b3.setOnClickListener(this);
        b4 = findViewById(R.id.btn_4);
        b4.setOnClickListener(this);
        b5 = findViewById(R.id.btn_5);
        b5.setOnClickListener(this);
        b6 = findViewById(R.id.btn_6);
        b6.setOnClickListener(this);
        b7 = findViewById(R.id.btn_7);
        b7.setOnClickListener(this);

        // Fetching general news articles on app startup
        RequestManager manager = new RequestManager(this);
        manager.getNewdHeadlines(listener, "general", null);
    }

    // Callback for handling the response from the news API
    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
            if(list.isEmpty())
            {
                // No data found, display a toast message
                Toast.makeText(MainActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
            else {
                // Data found, display the news articles
                showNews(list);
                dialog.dismiss();
            }

        }

        @Override
        public void onError(String message) {
            Toast.makeText(MainActivity.this, "An Error Occurred", Toast.LENGTH_SHORT).show();

        }
    };

    // Displaying news articles in the RecyclerView
    private void showNews(List<NewsHeadlines> list) {
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        adapter = new CustomAdapter(this,list, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void OnNewsClicked(NewsHeadlines headlines) {
        startActivity(new Intent(MainActivity.this,DetailsActivity.class).putExtra("data",headlines));

    }

    // Handling click events for news category buttons
    @Override
    public void onClick(View v) {
        Button button=(Button) v;
        String category = button.getText().toString();
        dialog.setTitle("Fetching news articles of" + category);
        dialog.show();
        RequestManager manager = new RequestManager(this);
        manager.getNewdHeadlines(listener,category,null);
    }
}