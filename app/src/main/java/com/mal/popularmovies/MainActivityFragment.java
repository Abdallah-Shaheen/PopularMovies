package com.mal.popularmovies;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.mal.popularmovies.adapters.ImageAdapter;
import com.mal.popularmovies.loaders.DataFetchLoader;
import com.mal.popularmovies.models.Movie;
import com.mal.popularmovies.parsers.MovieParser;
import com.mal.popularmovies.utilities.FavoriteListener;
import com.mal.popularmovies.utilities.MovieListener;
import com.mal.popularmovies.utilities.Utility;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>>, FavoriteListener {

    private final String LOG_TAG = "Pop Mov App MAL Shaheen";
    ImageAdapter imgAdapter;
    GridView gridView;
    ImageView imageView;

    int flag = 0;


    String BASE_URL = "http://api.themoviedb.org/3/movie/popular?";

    private Realm realm;


    public MainActivityFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        realm = Realm.getInstance(getActivity());
        Log.i(LOG_TAG, "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        gridView = (GridView) rootView.findViewById(R.id.gridview);
        imgAdapter = new ImageAdapter(getActivity(), new ArrayList<Movie>());
        gridView.setAdapter(imgAdapter);
        imageView = (ImageView) rootView.findViewById(R.id.error_image);

        if(Utility.isOnline(getContext())) {
            Log.i(LOG_TAG, "internet available");
            getActivity().getSupportLoaderManager().initLoader(1, null, this).forceLoad();
        }else{
            Log.i(LOG_TAG, "no internet");
            imageView.setImageResource(R.drawable.no_internet);
        }

        return rootView;
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {

        String OPEN_Movies_DB_API_KEY = "fbf1054b0fcf2039ebb9d45314387f6a";
        final String Movies_BASE_URL = BASE_URL;
        final String APPID_PARAM = "api_key";
        Uri builtUri = Uri.parse(Movies_BASE_URL).buildUpon().
                appendQueryParameter(APPID_PARAM, OPEN_Movies_DB_API_KEY).build();
        try {
            URL url = new URL(builtUri.toString());
            Log.i(LOG_TAG, url.toString());
            return new DataFetchLoader<Movie>(getActivity(), url, new MovieParser());
        }catch (Exception e){
            Log.e(LOG_TAG, "Error ", e);
            return null;
        }
    }
    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> result) {
        Log.i(LOG_TAG, "onLoadFinished");

        if (result != null) {

            imgAdapter.setData(result);
            Log.i(LOG_TAG, "onLoadFinished : new data");
        } else {
            Log.i(LOG_TAG, "No result");
            Toast.makeText(getContext(), "No Data Received Try Again", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
    }




    @Override
    public void onStart() {
        super.onStart();
        Log.i(LOG_TAG, "onStart" + " flag: " + flag);
        //we have selected Favorite option in main fragment so when we return back update favorites
        if(flag == 1){
            updateFavorites();
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_most_popular) {
            flag = 0;
            Log.i(LOG_TAG, "Most Popular Selected" + " flag: " + flag);
            if(Utility.isOnline(getContext())) {
                Log.i(LOG_TAG, "internet available");
                BASE_URL = "http://api.themoviedb.org/3/movie/popular?";
                getLoaderManager().restartLoader(1, null, this).forceLoad();
            }else{
                Log.i(LOG_TAG, "no internet");
                imgAdapter.setData(new ArrayList<Movie>());
                imageView.setImageResource(R.drawable.no_internet);
            }
            return true;
        }else if (id == R.id.action_high_rated) {
            flag = 0;
            Log.i(LOG_TAG, "Top Rated Selected" + " flag: " + flag);
            if(Utility.isOnline(getContext())) {
                Log.i(LOG_TAG, "internet available");
                BASE_URL = "http://api.themoviedb.org/3/movie/top_rated?";
                getLoaderManager().restartLoader(1, null, this).forceLoad();
            }else{
                Log.i(LOG_TAG, "no internet");
                imgAdapter.setData(new ArrayList<Movie>());
                imageView.setImageResource(R.drawable.no_internet);
            }
            return true;
        } else if (id == R.id.action_favorite) {
            imageView.setImageDrawable(null);
            flag = 1;
            Log.i(LOG_TAG, "Favorite Selected" + " flag: " + flag);
            updateFavorites();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateFavoritesMovies(){
        Log.i(LOG_TAG,"updateFavoritesMovies");
        if (flag == 1){
            updateFavorites();
        }
    };

    private void updateFavorites(){
        RealmResults<Movie> mResult = realm.where(Movie.class).findAll();
        Log.i(LOG_TAG, "nMovie in realm: " + mResult.size() );
        if (!mResult.isEmpty()){
            Log.i(LOG_TAG, "hhhha get movies from realmDB");
            ArrayList<Movie> favoriteMoviesList = new ArrayList<>();
            List<Movie> favoriteMovies = mResult.subList(0,mResult.size());
            favoriteMoviesList.addAll(favoriteMovies);
            imgAdapter.setData(favoriteMoviesList);
        }else {
            Log.i(LOG_TAG,"no realm movies found hhhhhaaahhhh");
            Toast.makeText(getContext(), "No Favorite Movies Found", Toast.LENGTH_LONG).show();
            imgAdapter.setData(new ArrayList<Movie>());
            imageView.setImageResource(R.drawable.unfavorite);
        }
    }
}
