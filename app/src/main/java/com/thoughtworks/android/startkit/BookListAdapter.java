package com.thoughtworks.android.startkit;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder> {

    private List<BookItem> mBooks = new ArrayList<>();

    public void addAll(List<BookItem> newBooks) {
        mBooks.addAll(newBooks);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final BookItem data = mBooks.get(position);

        holder.title.setText(data.getItemTitle());
        holder.summary.setText(data.getItemSummary());
        holder.information.setText(data.getInformation());
        holder.ratingBar.setRating((float) (data.getItemRating() / 2));
        holder.ratingVal.setText(String.valueOf(data.getItemRating()));


        Glide
                .with(StartkitApplication.getApplication())
                .load(data.getItemImage())
                .centerCrop()
                .placeholder(R.drawable.ic_default_cover)
                .crossFade()
                .into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, BookDetailActivity.class);
                intent.putExtra("BOOK", mBooks.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    public void clearAll() {
        mBooks.clear();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.information)
        TextView information;
        @BindView(R.id.summary)
        TextView summary;
        @BindView(R.id.thumbnail)
        ImageView image;
        @BindView(R.id.rating)
        RatingBar ratingBar;
        @BindView(R.id.ratingValue)
        TextView ratingVal;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }
}
