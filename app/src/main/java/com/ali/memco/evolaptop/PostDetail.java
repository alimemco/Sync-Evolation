package com.ali.memco.evolaptop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PostDetail extends AppCompatActivity {

    private ImageView detImg ;
    private TextView detTitile, detContent, detDate;

    private DbOpenHelper dbOpenHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        initViews();

        getDataFromIntent();



    }

    private void getDataFromIntent() {

        Intent intent = getIntent();
        int id = intent.getIntExtra(DbOpenHelper.ID,0);

        dbOpenHelper.setVisited(id,1);

        String title= intent.getStringExtra(DbOpenHelper.TITLE);
        String content= intent.getStringExtra(DbOpenHelper.CONTENT);
        String imageurl= intent.getStringExtra(DbOpenHelper.IMAGE_URL);
        String date= intent.getStringExtra(DbOpenHelper.DATE);

        Picasso.get().load(imageurl).into(detImg);
        detTitile.setText(title);
        detContent.setText(content);
        detDate.setText(date);


    }

    private void initViews() {
        detImg = findViewById(R.id.det_img_image);
        detTitile = findViewById(R.id.det_txt_titr);
        detContent = findViewById(R.id.det_txt_content);
        detDate= findViewById(R.id.det_txt_date);

        dbOpenHelper = new DbOpenHelper(this);




    }
}
