package com.example.prabhjot.firstapp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.List;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{


    private final List<Item> itemsList;
    public boolean isViewAList;
    Context mContext;

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView title, rating, releaseYear;
        public ImageView image;

        public ItemViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            title = (TextView) view.findViewById(R.id.title);
            rating= (TextView) view.findViewById(R.id.rating);
            releaseYear = (TextView) view.findViewById(R.id.release_year);
            image= (ImageView) view.findViewById(R.id.image);
        }

        @Override
        public void onClick(View view) {

            /*Item currentItem = new Item(title.getText().toString(),
                    rating.getText().toString(),
                    releaseYear.getText().toString(), " "); */

            int adapterPosition=getAdapterPosition();
            Intent myIntent = new Intent(mContext, ThirdActivity.class);

            // Passing data as a parecelable object to ThirdActivity
            myIntent.putExtra("item",itemsList.get(adapterPosition));
            mContext.startActivity(myIntent);

        }
    }

    public ItemAdapter(List<Item> itemsList, boolean flag, Context context) {
        this.itemsList = itemsList;
        this.isViewAList= flag;
        this.mContext=context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(isViewAList ? R.layout.list_item : R.layout.grid_item, null);

        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {


        Item item = itemsList.get(position);
        holder.title.setText(item.getTitle());
        holder.rating.setText("Rating: " + item.getRating());
        holder.releaseYear.setText("Release Year: "+item.getReleaseYear());

        holder.image.setImageResource(R.drawable.ic_launcher_background);

        DownloadImageTask downloadImageTask= new DownloadImageTask(holder.image);
        downloadImageTask.execute(item.getImage());

        //Picasso.get().load(item.getImage()).into(holder.image);


    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }
        protected Bitmap doInBackground(String... urls) {
            String urlDisplay = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(urlDisplay).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}