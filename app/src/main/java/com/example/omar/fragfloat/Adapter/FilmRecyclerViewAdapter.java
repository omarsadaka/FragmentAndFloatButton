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

import com.example.omar.fragfloat.Avtivities.FilmDetailsActivity;
import com.example.omar.fragfloat.Model.ResultsBean;
import com.example.omar.fragfloat.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar on 12/18/2018.
 */

public class FilmRecyclerViewAdapter extends RecyclerView.Adapter<FilmRecyclerViewAdapter.viewHolder> implements Filterable{
    private List<ResultsBean> filmsList;
    private List<ResultsBean> filterFilmList;
    private Context context;

    public FilmRecyclerViewAdapter(List<ResultsBean> filmsList,Context context) {
        this.filmsList = filmsList;
        this.context=context;
        this.filterFilmList = filmsList;
    }

    @NonNull
    @Override
    public FilmRecyclerViewAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.films_item_row, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmRecyclerViewAdapter.viewHolder holder, int position) {
        ResultsBean films = filterFilmList.get(position);
        holder.title.setText(films.getTitle());
        holder.auther.setText(films.getType());
        holder.language.setText(films.getImdb_score());
        holder.country.setText(films.getId());

        Picasso.get().load(films.getImdb_poster_url()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return filterFilmList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString=constraint.toString();
                if(charString.isEmpty()){
                    filterFilmList = filmsList;
                }else {
                    List<ResultsBean> filterdList = new ArrayList<>();
                    for (ResultsBean resultsBean:filmsList) {
                        if(resultsBean.getTitle().toLowerCase().contains(charString.toLowerCase())){
                            filterdList.add(resultsBean);
                        }
                    }
                    filterFilmList = filterdList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterFilmList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filterFilmList = (ArrayList<ResultsBean>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    class viewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView auther;
        TextView language;
        TextView country;

        viewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.film_image);
            title = itemView.findViewById(R.id.film_title);
            auther = itemView.findViewById(R.id.film_auther);
            language = itemView.findViewById(R.id.film_language);
            country = itemView.findViewById(R.id.film_country);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ResultsBean resultsBean = filmsList.get(getAdapterPosition());
                    Intent intent = new Intent(context , FilmDetailsActivity.class);
                    intent.putExtra("data" , resultsBean);
                    context.startActivity(intent);
                }
            });
        }
    }

}
