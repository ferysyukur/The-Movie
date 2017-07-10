package com.ferysyukur.daggerretrofit.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ferysyukur.daggerretrofit.BuildConfig;
import com.ferysyukur.daggerretrofit.R;
import com.ferysyukur.daggerretrofit.data.model.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ferysyukur on 5/29/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private ItemClickListener itemClickListener;
    private List<Movie> movies;
    private int rowLayout;
    private Context context;

    public MovieAdapter(List<Movie> movies, int rowLayout, Context context){
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie mov = movies.get(position);
        holder.title.setText(mov.getTitle());
        holder.subtitle.setText(mov.getReleaseDate());
        holder.desc.setText(mov.getOverview());
        holder.rating.setText(mov.getVoteAverage().toString());
        Glide.with(context).load(BuildConfig.BASE_URL_IMG+mov.getPosterPath())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.posterImg);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.movies_layout)
        LinearLayout moviesLayout;

        @BindView(R.id.img)
        ImageView posterImg;

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.subtitle)
        TextView subtitle;

        @BindView(R.id.description)
        TextView desc;

        @BindView(R.id.rating)
        TextView rating;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) itemClickListener.OnItemClick(v, getAdapterPosition());
        }
    }

    public Movie getItem(int id){
        return movies.get(id);
    }

    public interface ItemClickListener{
        void OnItemClick(View view, int position);
    }

    public void setClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
}
