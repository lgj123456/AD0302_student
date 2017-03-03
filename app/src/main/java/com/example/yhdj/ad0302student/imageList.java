package com.example.yhdj.ad0302student;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class imageList extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);

        initViews();

    }

    private void initViews() {
        Intent intent = getIntent();
        int StuId = intent.getIntExtra("id",1);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);

    }
}
