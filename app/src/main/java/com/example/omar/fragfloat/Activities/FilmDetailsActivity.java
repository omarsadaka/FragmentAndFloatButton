package com.example.omar.fragfloat.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.omar.fragfloat.Model.ResultsBean;
import com.example.omar.fragfloat.R;
import com.squareup.picasso.Picasso;

public class FilmDetailsActivity extends AppCompatActivity {
    private ImageView poster;
    private TextView title;
    private TextView description;
    private ResultsBean resultsBean;
    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_details);
        poster = findViewById(R.id.image);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        back = findViewById(R.id.back);

        resultsBean = (ResultsBean) getIntent().getSerializableExtra("data");
        Picasso.get().load(resultsBean.getImdb_poster_url()).into(poster);

//        if (resultsBean.getImdb_poster_url().isEmpty()){
//            Picasso.get().load(R.drawable.ic_launcher_background).into(poster);
//        }else {
//            Picasso.get().load(resultsBean.getImdb_poster_url()).into(poster);
//        }
        title.setText(resultsBean.getTitle());
        description.setText(resultsBean.getDescription());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                FilmDetailsActivity.this.finish();
            }
        });
    }
}
