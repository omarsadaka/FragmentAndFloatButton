package com.example.omar.fragfloat.Avtivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.omar.fragfloat.Model.Movies;
import com.example.omar.fragfloat.R;
import com.squareup.picasso.Picasso;

public class MenuDetailsActivity extends AppCompatActivity {
    private ImageView poster;
    private TextView title;
    private TextView content;
    private ImageView back;
    private Movies movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_details);
        poster = findViewById(R.id.image);
        title = findViewById(R.id.title);
        content = findViewById(R.id.description);
        back = findViewById(R.id.back);
        movies = (Movies) getIntent().getSerializableExtra("data");

        if (movies.getLogo().isEmpty()){
            Picasso.get().load(R.drawable.ic_launcher_background).into(poster);
        }else {
            Picasso.get().load(movies.getLogo()).into(poster);
        }

        title.setText(movies.getName());
        content.setText(movies.getSummary());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                MenuDetailsActivity.this.finish();
            }
        });
    }
}
