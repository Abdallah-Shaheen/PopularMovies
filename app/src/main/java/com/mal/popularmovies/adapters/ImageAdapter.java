package com.mal.popularmovies.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.mal.popularmovies.models.Movie;
import com.mal.popularmovies.R;
import com.mal.popularmovies.utilities.MovieListener;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by AbdAllah Shaheen on 8/14/2016.
 */
public class ImageAdapter extends BaseAdapter{
    private final String LOG_TAG = "Pop Mov App MAL Shaheen";
    private Context context;
    private List<Movie> moveList;
    static MovieListener movieListener;

    public ImageAdapter(Context context, List<Movie> moveList) {
        this.context = context;
        this.moveList = moveList;
        Log.i(LOG_TAG,"new imgAdapter");
    }

    public void setData(List<Movie> moveList) {
        this.moveList = moveList;
        notifyDataSetChanged();
    }

    public int getCount() {
        return moveList.size();
    }

    @Override
    public Object getItem(int position) {
        Movie m = moveList.get(position);
        return m.getPosterPath();
    }

    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        final ImageView imageView;
        Log.i(LOG_TAG,"new img view");

        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setMinimumHeight(700);
        }else {
            imageView = (ImageView) convertView;
        }

        //imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Log.i(LOG_TAG, "position  " + position);
        String url = (String) getItem(position);
        Picasso.with(context).load("http://image.tmdb.org/t/p/" + "w185" + url)
                .placeholder(R.drawable.moive)
                .error(R.drawable.moive).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Movie selectedMovie = new Movie();

                selectedMovie.setId( moveList.get(position).getId());
                selectedMovie.setOriginalTitle(moveList.get(position).getOriginalTitle());
                selectedMovie.setPosterPath(moveList.get(position).getPosterPath());
                selectedMovie.setReleaseDate(moveList.get(position).getReleaseDate());
                selectedMovie.setOverview(moveList.get(position).getOverview());
                selectedMovie.setRating(moveList.get(position).getRating());

//                Intent intent = new Intent(context, DetailActivity.class);
//                intent.putExtra("movie", selectedMovie);

                Log.i(LOG_TAG, "selected position  " + position);
                Log.i(LOG_TAG, "selected position  " + selectedMovie.getId());
                Log.i(LOG_TAG, "selected position  " + selectedMovie.getOriginalTitle());
                Log.i(LOG_TAG, "selected position  " + selectedMovie.getPosterPath());
                Log.i(LOG_TAG, "selected position  " + selectedMovie.getReleaseDate());
                Log.i(LOG_TAG, "selected position  " + selectedMovie.getRating());
                Log.i(LOG_TAG, "selected position  " + selectedMovie.getOverview());



                movieListener.setSelectedMovie(selectedMovie);
//                context.startActivity(intent);
            }
        });


        return imageView;
    }

    public static void setMovieListener(MovieListener mListener) {
        movieListener=mListener;
    }


}
