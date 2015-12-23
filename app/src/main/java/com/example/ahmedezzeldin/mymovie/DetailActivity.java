package com.example.ahmedezzeldin.mymovie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle extras = getIntent().getExtras();
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(extras);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.detail_container, detailFragment).commit();
    }
}
