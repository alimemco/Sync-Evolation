package com.ali.memco.evolaptop.view.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import com.ali.memco.evolaptop.R;
import com.ali.memco.evolaptop.adapter.ViewPageClothesAdapter;

public class Botick_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_botick_activity);


        ViewPager viewPager = findViewById(R.id.Botic_ViewPager);
        TabLayout tabLayout = findViewById(R.id.botick_TabLayout);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.botick_toolbar);

        ViewPageClothesAdapter adapter = new ViewPageClothesAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }
}
