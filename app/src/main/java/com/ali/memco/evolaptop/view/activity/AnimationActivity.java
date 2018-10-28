package com.ali.memco.evolaptop.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ali.memco.evolaptop.R;

public class AnimationActivity extends AppCompatActivity implements View.OnClickListener{

    private Button button_alpha;
    private Button button_Translate;
    private Button button_Scale;
    private Button button_Rotate;
    private Button button_ValueAnim;
    private Button button_set;
    private Button button_YoYo;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        initViews();
        setupToolbar();
    }

    private void setupToolbar() {

        toolbar = findViewById(R.id.anim_main_toobar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbar.setTitle(R.string.title_ANIMATION);



    }

    private void initViews() {

        button_alpha = findViewById(R.id.button_alpha_animation);
        button_Translate = findViewById(R.id.button_Translate_animation);
        button_Scale = findViewById(R.id.button_Scale_animation);
        button_Rotate = findViewById(R.id.button_Rotate_animation);
        button_ValueAnim = findViewById(R.id.button_value_animator);
        button_set = findViewById(R.id.button_set_animation);
        button_YoYo = findViewById(R.id.button_YoYo_animation);

        button_alpha.setOnClickListener(this);
        button_Translate.setOnClickListener(this);
        button_Scale.setOnClickListener(this);
        button_Rotate.setOnClickListener(this);
        button_ValueAnim.setOnClickListener(this);
        button_set.setOnClickListener(this);
        button_YoYo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(this,AnimationShow.class);
        intent.putExtra(AnimationShow.KEY_EXTRA_ANIM,Integer.parseInt((String)v.getTag()));
        startActivity(intent);

    }
}
