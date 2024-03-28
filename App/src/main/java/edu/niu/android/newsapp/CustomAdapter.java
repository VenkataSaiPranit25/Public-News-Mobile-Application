package edu.niu.android.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.niu.android.newsapp.Models.NewsHeadlines;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private Context context;
    private List<NewsHeadlines> headlinesList;
    private SelectListener listener;

    // Constructor to initialize the adapter with data and listener
    public CustomAdapter(Context context, List<NewsHeadlines> headlinesList, SelectListener listener) {
        this.context = context;
        this.headlinesList = headlinesList;
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for a single item in the RecyclerView
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.headline_list_items, parent, false));
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        // Set the data for a single item in the RecyclerView

        // Set the title of the news article
        holder.text_title.setText(headlinesList.get(position).getTitle());

        // Set the source name of the news article
        holder.text_source.setText(headlinesList.get(position).getSource().getName());

        // Load and display the image for the news article using Picasso library
        if (headlinesList.get(position).getUrlToImage() != null) {
            Picasso.get().load(headlinesList.get(position).getUrlToImage()).into(holder.img_headline);
        }

        // Set a click listener to handle when a news article is clicked
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Notify the listener that a news article is clicked
                listener.OnNewsClicked(headlinesList.get(position));
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        // Return the number of items in the dataset
        return headlinesList.size();
    }
}
