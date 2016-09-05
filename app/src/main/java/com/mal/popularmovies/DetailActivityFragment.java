package com.mal.popularmovies;

import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mal.popularmovies.adapters.ReviewListAdapter;
import com.mal.popularmovies.adapters.TrailerListAdapter;
import com.mal.popularmovies.loaders.DataFetchLoader;
import com.mal.popularmovies.models.Movie;
import com.mal.popularmovies.models.Review;
import com.mal.popularmovies.models.Trailer;
import com.mal.popularmovies.parsers.ReviewParser;
import com.mal.popularmovies.parsers.TrailerParser;
import com.mal.popularmovies.utilities.FavoriteListener;
import com.mal.popularmovies.utilities.Utility;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Trailer>> {

    private final String LOG_TAG = "Pop Mov App MAL Shaheen";
    String BASE_URL = "http://api.themoviedb.org/3/movie/";
    TrailerListAdapter trailerListAdapter;
    ReviewListAdapter reviewListAdapter;
    ListView trailerListView;
    ListView reviewListView;
    Movie movie;
    static FavoriteListener favoriteListener;
    private Realm realm;

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        realm = Realm.getInstance(getActivity());
        final View view = inflater.inflate(R.layout.fragment_detail, container, false);

        Bundle extras = getArguments();
        final boolean tablet = extras.getBoolean("tabletMode");
        final int flag = extras.getInt("flag");
        movie = (Movie) extras.getSerializable("movie");
        if(movie != null) {
//            Log.i("Shaheen hhhhhhhhhha", "we have movie");
//
//            Log.i("Shaheen hhhhhhhhhha", "selected position  " + movie.posterPath);
//            Log.i("Shaheen hhhhhhhhhha", "selected position  " + movie.overview);
//            Log.i("Shaheen hhhhhhhhhha", "selected position  " + movie.releaseDate);
//            Log.i("Shaheen hhhhhhhhhha", "selected position  " + movie.rating);
//            Log.i("Shaheen hhhhhhhhhha", "selected position  " + movie.originalTitle);
            TextView textView = (TextView) view.findViewById(R.id.movie_title);
            textView.setText(movie.getOriginalTitle());
            textView = (TextView) view.findViewById(R.id.release_date);
            textView.setText(movie.getReleaseDate());
            textView = (TextView) view.findViewById(R.id.movie_rating);
            String rating = Double.toString(movie.getRating());
            textView.setText(rating);
            textView = (TextView) view.findViewById(R.id.overview);
            textView.setText(movie.getOverview());
            ImageView imageView = (ImageView)view.findViewById(R.id.movie_poster);
            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/" + "w185" + movie.getPosterPath())
                    .placeholder(R.drawable.moive)
                    .error(R.drawable.moive)
                    .into(imageView);
        }
        else{
            Log.i("Shaheen", "null movie pointer");
        }

        trailerListAdapter = new TrailerListAdapter(getActivity(), new ArrayList<Trailer>());
        trailerListView = (ListView) view.findViewById(R.id.trailer_list);
        trailerListView.setAdapter(trailerListAdapter);

        reviewListAdapter = new ReviewListAdapter(getActivity(), new ArrayList<Review>());
        reviewListView = (ListView) view.findViewById(R.id.review_list);
        reviewListView.setAdapter(reviewListAdapter);



        if(Utility.isOnline(getContext())) {
            Log.i(LOG_TAG, "internet available");
            getActivity().getSupportLoaderManager().initLoader(2, null, this).forceLoad();
        } else {
            Trailer trailer = new Trailer();
            trailer.key = null;
            trailer.name = "No Internet to get trailers";
            Review review = new Review();
            review.author = "No Internet to get Reviews";
            review.content = "";
            ArrayList<Trailer> tt = new ArrayList<Trailer>();
            tt.add(trailer);
            ArrayList<Review> rr = new ArrayList<Review>();
            rr.add(review);
            trailerListAdapter.setData(tt);
            reviewListAdapter.setData(rr);
            Log.i(LOG_TAG, "no internet");
        }

        final ImageButton imageButton= (ImageButton) view.findViewById(R.id.make_as_favorite_button);
        imageButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageButton.setBackgroundColor(Color.TRANSPARENT);

        RealmQuery<Movie> query = realm.where(Movie.class);
        query.equalTo("id", movie.getId());
        final RealmResults<Movie> rmResult = query.findAll();

        if (!rmResult.isEmpty()){
            imageButton.setImageResource(R.drawable.favorite);
        }

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmQuery<Movie> query = realm.where(Movie.class);
                query.equalTo("id", movie.getId());
                final RealmResults<Movie> mResult = query.findAll();

                Log.i(LOG_TAG, "button make as favorite clicked");
                Log.i(LOG_TAG, "button F :" + mResult.toString() + "mResult size :" + mResult.size());

                if (mResult.isEmpty()) {
                    realm.beginTransaction();
                    Movie rmMovie = realm.createObject(Movie.class);
                    rmMovie.setId(movie.getId());
                    rmMovie.setOriginalTitle(movie.getOriginalTitle());
                    rmMovie.setPosterPath(movie.getPosterPath());
                    rmMovie.setReleaseDate(movie.getReleaseDate());
                    rmMovie.setOverview(movie.getOverview());
                    rmMovie.setRating(movie.getRating());
                    realm.commitTransaction();
                    imageButton.setImageResource(R.drawable.favorite);
                }else {
                    realm.beginTransaction();
                    mResult.clear();
                    realm.commitTransaction();
                    imageButton.setImageResource(R.drawable.unfavorite);
                    if(tablet & (flag == 1)) {
                        view.findViewById(R.id.detail_fragment_view).setVisibility(View.INVISIBLE);
                    }
                }
                if (tablet) {
                    favoriteListener.updateFavoritesMovies();
                }
            }
        });

        return view;
    }



    @Override
    public Loader<ArrayList<Trailer>> onCreateLoader(int id, Bundle args) {

        Log.i(LOG_TAG, "onCreateLoader trailer");

        String OPEN_Movies_DB_API_KEY = "fbf1054b0fcf2039ebb9d45314387f6a";
        final String Movies_BASE_URL = BASE_URL + movie.getId() + "/videos?";
        final String APPID_PARAM = "api_key";
        Uri builtUri = Uri.parse(Movies_BASE_URL).buildUpon().
                appendQueryParameter(APPID_PARAM, OPEN_Movies_DB_API_KEY).build();
        try {
            URL url = new URL(builtUri.toString());
            Log.i(LOG_TAG, url.toString());
            return new DataFetchLoader<Trailer>(getActivity(), url, new TrailerParser());
        }catch (Exception e){
            Log.e(LOG_TAG, "Error ", e);
            return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Trailer>> loader, ArrayList<Trailer> result) {
        Log.i(LOG_TAG, "onLoadFinished trailer");

        if (result != null) {
            trailerListAdapter.setData(result);
            Log.i(LOG_TAG, "onLoadFinished : new data");
        } else {
            Log.i(LOG_TAG, "No Internet");
        }

        getActivity().getSupportLoaderManager().initLoader(3, null, reviewLoaderCallbacks).forceLoad();
    }
//
    @Override
    public void onLoaderReset(Loader<ArrayList<Trailer>> loader) {
    }

    private LoaderManager.LoaderCallbacks<ArrayList<Review>> reviewLoaderCallbacks =
            new LoaderManager.LoaderCallbacks<ArrayList<Review>>() {
                @Override
                public Loader<ArrayList<Review>> onCreateLoader(int id, Bundle args) {

                    Log.i(LOG_TAG, "onCreateLoader review");

                    String OPEN_Movies_DB_API_KEY = "fbf1054b0fcf2039ebb9d45314387f6a";
                    final String Movies_BASE_URL = BASE_URL + movie.getId() + "/reviews?";
                    final String APPID_PARAM = "api_key";
                    Uri builtUri = Uri.parse(Movies_BASE_URL).buildUpon().
                            appendQueryParameter(APPID_PARAM, OPEN_Movies_DB_API_KEY).build();
                    try {
                        URL url = new URL(builtUri.toString());
                        Log.i(LOG_TAG, url.toString());
                        return (new DataFetchLoader<Review>(getActivity(), url, new ReviewParser()));
                    }catch (Exception e){
                        Log.e(LOG_TAG, "Error ", e);
                        return null;
                    }
                }

                @Override
                public void onLoadFinished(Loader<ArrayList<Review>> loader, ArrayList<Review> result) {
                    Log.i(LOG_TAG, "onLoadFinished review");

                    if (result != null) {
                        Log.i(LOG_TAG, "reeeeeeview list");
                        reviewListAdapter.setData(result);
                        Log.i(LOG_TAG, "onLoadFinished : new data");
                    } else {
                        Log.i(LOG_TAG, "No Internet");
                    }
                }

                @Override
                public void onLoaderReset(Loader<ArrayList<Review>> loader) {

                }
            };

}
