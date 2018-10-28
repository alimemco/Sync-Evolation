package com.ali.memco.evolaptop;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class GetPicassActivity extends AppCompatActivity {

    private static final String TAG = "GetPicassActivity";

    private Button btn;
    private ImageView img1,img2,img3,img4,img5,img6,img7;
    private List<String> urls;
    private String url;
    private String bigPic="http://kb4images.com/images/desktop-backgrounds/37106620-desktop-backgrounds.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_picass);

        iniyView();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url=bigPic;
                for (int i = 0; i < 5; i++) {
                    url = urls.get(i);
                    Picasso.get().load(urls.get(i)).into(img1);
                    Log.i(TAG, "onClick: "+urls.get(i));


                }


            }
        });


    }

    private void iniyView() {

        btn = findViewById(R.id.getPicasBtn);
        img1 = findViewById(R.id.getPicasImg1);
        img2 = findViewById(R.id.getPicasImg2);
        img3 = findViewById(R.id.getPicasImg3);
        img4 = findViewById(R.id.getPicasImg4);
        img5 = findViewById(R.id.getPicasImg5);
        img6 = findViewById(R.id.getPicasImg6);
        img7 = findViewById(R.id.getPicasImg7);

        urls = new ArrayList<>();
        urls.add("http://insta-pro.ir/app/app/json/uploads/pic1.jpg");
        urls.add("http://insta-pro.ir/app/app/json/uploads/pic2.jpg");
        urls.add("http://insta-pro.ir/app/app/json/uploads/pic3.jpg");
        urls.add("http://insta-pro.ir/app/app/json/uploads/pic4.jpg");
        urls.add("http://insta-pro.ir/app/app/json/uploads/pic5.jpg");
        urls.add("http://insta-pro.ir/app/app/json/uploads/pic6.jpg");
        urls.add("http://kb4images.com/images/desktop-backgrounds/37106620-desktop-backgrounds.jpg");



    }

    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            //img1.setImageBitmap(bitmap);

            File folder = new File(Environment.getExternalStorageDirectory(),"ali9");
            if (!folder.exists()){
                folder.mkdir();
            }
            String filneName = url.substring(url.lastIndexOf("/")+1,url.length());
            File image = new File(folder,filneName);
            try {
                FileOutputStream fos = new FileOutputStream(image);
                boolean fo =bitmap.compress(Bitmap.CompressFormat.JPEG,75,fos);

                fos.close();
                Log.i(TAG, "onBitmapLoaded: "+fo+" "+filneName);
                
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };
}
