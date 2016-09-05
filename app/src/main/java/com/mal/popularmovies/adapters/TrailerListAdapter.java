package com.mal.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mal.popularmovies.R;
import com.mal.popularmovies.models.Trailer;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by AbdAllah Shaheen on 8/30/2016.
 */
public class TrailerListAdapter extends BaseAdapter {

    private final String LOG_TAG = "Pop Mov App MAL Shaheen";
    private Context context;
    private LayoutInflater myInflater;
    private List<Trailer> trailerList;

    public TrailerListAdapter(Context context, List<Trailer> trailerList) {
        this.context = context;
        myInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.trailerList = trailerList;
        Log.i(LOG_TAG,"new TrailerListAdapter");
    }

    public void setData(List<Trailer> trailerList) {
        this.trailerList = trailerList;
        notifyDataSetChanged();
    }

    public int getCount() {
        return trailerList.size();
    }

    @Override
    public Trailer getItem(int position) {
        return trailerList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        if (convertView == null) {
            Log.i(LOG_TAG, "new trailer item view");
            convertView = myInflater.inflate(R.layout.trailer_list_item, parent, false);
        }

        final Trailer trailer = getItem(position);

        //get the first frame of the video "trailer" and set the image trailer with that frame
        ImageView image = (ImageView) convertView.findViewById(R.id.trailer_play_image);
        Picasso.with(context).load("http://img.youtube.com/vi/" + trailer.key + "/0.jpg").into(image);

        //set the name of the trailer
        TextView name = (TextView) convertView.findViewById(R.id.trailer_name);

        name.setText(trailer.name);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trailer iTrailer = getItem(position);
                if (iTrailer.key != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://www.youtube.com/watch?v=" + iTrailer.key));
                    context.startActivity(intent);
                } else {
                    Log.i(LOG_TAG, "trailer key equal null");
                }
            }
        });


        return convertView;
    }

}
