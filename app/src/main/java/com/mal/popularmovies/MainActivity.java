package com.mal.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.mal.popularmovies.adapters.ImageAdapter;
import com.mal.popularmovies.models.Movie;
import com.mal.popularmovies.utilities.MovieListener;

public class MainActivity extends AppCompatActivity implements MovieListener {

    boolean tabletMode = false;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(findViewById(R.id.movie_detail_container) != null) {
            tabletMode = true;
        }

        ImageAdapter.setMovieListener(this);

        if(savedInstanceState == null) {
            if(tabletMode) {
                MainActivityFragment mainActivityFragment = new MainActivityFragment();
                DetailActivityFragment.favoriteListener = mainActivityFragment;
                getSupportFragmentManager().beginTransaction().add(R.id.movies_main_list_posters, mainActivityFragment).commit();
            }
        }
    }

    @Override
    public void setSelectedMovie(Movie movie) {
        if (tabletMode) {
            //Case Tablet UI
            if(movie != null) {
                DetailActivityFragment detailActivityFragment = new DetailActivityFragment();
                Bundle extras = new Bundle();
                extras.putSerializable("movie", movie);
                extras.putBoolean("tabletMode",tabletMode);
                extras.putInt("flag",flag);
                detailActivityFragment.setArguments(extras);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container, detailActivityFragment).commit();
            } else{
                Toast.makeText(this, "This movie is removed no details found", Toast.LENGTH_LONG).show();
            }
        } else {
            //Case Single Pane UI
            Intent i = new Intent(this, DetailActivity.class);
            i.putExtra("movie", movie);
            startActivity(i);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (tabletMode) {
            int id = item.getItemId();
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.movie_detail_container);
            if (fragment != null) {
                if (id == R.id.action_most_popular) {
                    flag = 0;
                    Log.i("Main Activity", "action_most_popular");
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                } else if (id == R.id.action_high_rated) {
                    flag = 0;
                    Log.i("Main Activity", "action_high_rated");
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                } else if (id == R.id.action_favorite) {
                    flag = 1;
                    Log.i("Main Activity", "action_favorite");
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
