package com.mal.popularmovies.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mal.popularmovies.R;
import com.mal.popularmovies.models.Review;

import java.util.List;

/**
 * Created by AbdAllah Shaheen on 8/30/2016.
 */
public class ReviewListAdapter extends BaseAdapter {
    private final String LOG_TAG = "Pop Mov App MAL Shaheen";
    private Context context;
    private LayoutInflater myInflater;
    private List<Review> reviewList;

    public ReviewListAdapter(Context context, List<Review> reviewList) {
        this.context = context;
        myInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.reviewList = reviewList;
        Log.i(LOG_TAG, "new ReviewListAdapter");
    }

    public void setData(List<Review> reviewList) {
        this.reviewList = reviewList;
        notifyDataSetChanged();
    }

    public int getCount() {
        return reviewList.size();
    }

    @Override
    public Review getItem(int position) {
        return reviewList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        if (convertView == null) {
            Log.i(LOG_TAG, "new review item view");
            convertView = myInflater.inflate(R.layout.review_list_item, parent, false);
        }

        final Review review = getItem(position);
        Log.i(LOG_TAG,"aaauthor " + review.author);

        //set the name of the author of the review
        TextView author = (TextView) convertView.findViewById(R.id.review_author);
        author.setText(review.author);

        //set the content of the review
        TextView content = (TextView) convertView.findViewById(R.id.review_content);
        content.setText(review.content);

        return convertView;
    }
}
