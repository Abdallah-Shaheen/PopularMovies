package com.mal.popularmovies;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Receive the selected movie Bundle from main activity.
        Bundle extras = getIntent().getExtras();
        if (savedInstanceState == null) {
            DetailActivityFragment detailActivityFragment = new DetailActivityFragment();
            //Pass the "extras" Bundle that contains the selected "movie" to the fragment
            detailActivityFragment.setArguments(extras);
            getSupportFragmentManager().beginTransaction().replace(R.id.movie_detail_container, detailActivityFragment).commit();
        }

    }

}
