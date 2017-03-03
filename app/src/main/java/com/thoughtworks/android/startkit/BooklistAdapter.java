package com.thoughtworks.android.startkit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thoughtworks.android.startkit.book.Book;

import java.util.ArrayList;
import java.util.List;

public class BooklistAdapter extends RecyclerView.Adapter<BooklistAdapter.ViewHolder> {

    private List<Book> mBooks = new ArrayList<>();

    public void addAll(List<Book> newBooks) {
        mBooks.addAll(newBooks);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Book data = mBooks.get(position);

        holder.title.setText(data.getTitle());
        holder.summary.setText(data.getSummary());
        holder.information.setText(data.getInformation());
        holder.ratingBar.setRating((float) (data.getRating() / 2));
        holder.ratingVal.setText(String.valueOf(data.getRating()));


        Glide
                .with(StartkitApplication.getApplication())
                .load(data.getImage())
                .centerCrop()
                .placeholder(R.drawable.ic_default_cover)
                .crossFade()
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView information;
        public TextView summary;
        public ImageView image;
        public RatingBar ratingBar;
        public TextView ratingVal;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            information = (TextView) v.findViewById(R.id.information);
            summary = (TextView) v.findViewById(R.id.summary);
            image = (ImageView) v.findViewById(R.id.thumbnail);
            ratingBar = (RatingBar) v.findViewById(R.id.rating);
            ratingVal = (TextView) v.findViewById(R.id.ratingValue);
        }
    }
}
