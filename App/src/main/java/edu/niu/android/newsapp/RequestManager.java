package edu.niu.android.newsapp;

import android.content.Context;
import android.widget.Toast;

import edu.niu.android.newsapp.Models.NewsApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {
    Context context;

    // Creating a Retrofit instance with the base URL and Gson converter
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    // Constructor to initialize the context
    public RequestManager(Context context) {
        this.context = context;
    }

    // Method to fetch news headlines based on category and query
    public void getNewdHeadlines(OnFetchDataListener listener, String category, String query) {
        // Creating an instance of the CallNewsApi interface using Retrofit
        CallNewsApi callNewsApi = retrofit.create(CallNewsApi.class);

        // Making an asynchronous network request
        Call<NewsApiResponse> call = callNewsApi.callHeadlines("us", category, query, context.getString(R.string.api_key));
        try {
            call.enqueue(new Callback<NewsApiResponse>() {
                @Override
                public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                    // Check if the response is successful
                    if (!response.isSuccessful()) {
                        // Display a toast message for unsuccessful response
                        Toast.makeText(context, "Errorrrrrrrrr", Toast.LENGTH_SHORT).show();
                    }

                    // Notify the listener with the fetched data and response message
                    listener.onFetchData(response.body().getArticles(), response.message());
                }

                @Override
                public void onFailure(Call<NewsApiResponse> call, Throwable t) {
                    // Notify the listener about the request failure
                    listener.onError("Request Failure");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Interface for defining the News API endpoints
    public interface CallNewsApi {
        @GET("top-headlines")
        Call<NewsApiResponse> callHeadlines(
                @Query("country") String country,
                @Query("category") String category,
                @Query("q") String query,
                @Query("apiKey") String api_key
        );
    }
}
