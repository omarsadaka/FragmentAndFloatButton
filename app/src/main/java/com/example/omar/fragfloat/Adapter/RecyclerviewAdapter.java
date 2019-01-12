package com.example.omar.fragfloat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omar.fragfloat.Activities.MenuDetailsActivity;
import com.example.omar.fragfloat.Model.Movies;
import com.example.omar.fragfloat.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar on 12/11/2018.
 */

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<Movies> movieList;
    private List<Movies> moviesListFiltered;


    public RecyclerviewAdapter(Context context, List<Movies> movieList) {
        this.context = context;
        this.movieList = movieList;
        this.moviesListFiltered = movieList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Movies movie = moviesListFiltered.get(position);
        String posterLink = movie.getLogo();

        holder.title.setText(movie.getName());
        holder.descr.setText(movie.getSummary());
        if (posterLink.isEmpty()) {
            holder.poster.setImageResource(R.drawable.ic_launcher_background);
        } else {
            Picasso.get().load(posterLink).into(holder.poster);
        }
    }

    @Override
    public int getItemCount() {
        return moviesListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString=constraint.toString();
                if(charString.isEmpty()){
                    moviesListFiltered = movieList;
                }else {
                    List<Movies> filterdList = new ArrayList<>();
                    for (Movies movie:movieList) {
                        if(movie.getName().toLowerCase().contains(charString.toLowerCase())){
                            filterdList.add(movie);
                        }
                    }
                    moviesListFiltered = filterdList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = moviesListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                moviesListFiltered = (ArrayList<Movies>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView poster;
        TextView title;
        TextView descr;


        public ViewHolder(View itemView, final Context context1) {
            super(itemView);
            context = context1;

            poster = (ImageView) itemView.findViewById(R.id.movieImageView);
            title = (TextView) itemView.findViewById(R.id.movieTitle);
            descr = (TextView) itemView.findViewById(R.id.descr);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Movies movies = movieList.get(getAdapterPosition());
                    Toast.makeText(context1, "You Clicked " + movies.getName(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context , MenuDetailsActivity.class);
                    intent.putExtra("data" , movies);
                    context.startActivity(intent);
                }
            });
        }

    }

}
