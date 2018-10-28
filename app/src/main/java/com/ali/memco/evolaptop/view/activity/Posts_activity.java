package com.ali.memco.evolaptop.view.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.ali.memco.evolaptop.DbOpenHelper;
import com.ali.memco.evolaptop.R;
import com.ali.memco.evolaptop.adapter.AppFeatureAdapter;
import com.ali.memco.evolaptop.adapter.PostAdapter;
import com.ali.memco.evolaptop.dataModel.ApiService;
import com.ali.memco.evolaptop.dataModel.DataFakeGenrator;
import com.ali.memco.evolaptop.dataModel.Post;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Posts_activity extends AppCompatActivity{

    private static final String TAG = "Posts_activity";
    private static final String FOLDER_NAME = "aliImages";
    private static List<Post> posts;
    private String url;

    private static final int REQ_PERM_CODE_WRITE_EXTRE_STORAGE = 200;

    private File folder;

    private RecyclerView recyclerView;
    private CoordinatorLayout coordinatorLayout;
    Toolbar toolbar;
    private DbOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_news);

        initViews();

        if (isInternetAvailable()){
            getFromServer();
            Log.i(TAG, "checkCon: Connected :)");
        }else {
            getFromDatabase();
            Log.i(TAG, "checkCon: Not Conected :(");
            Snackbar.make(coordinatorLayout,"حالت بدون اینترنت فعال است",10000).show();

        }




    }

    private void initViews() {
        coordinatorLayout = findViewById(R.id.post_coordinatorLayout);
        recyclerView = findViewById(R.id.news_rcview);
        toolbar = findViewById(R.id.post_toolbar);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);




        recyclerView.setLayoutManager(new LinearLayoutManager(Posts_activity.this,LinearLayoutManager.VERTICAL,false));
        db = new DbOpenHelper(Posts_activity.this);

    }

    private void getFromServer(){

        ApiService apiService = new ApiService(this);
        apiService.getPosts(new ApiService.OnPostsRecived() {
            @Override
            public void onRecived(List<Post> posts) {
                PostAdapter postAdapter = new PostAdapter(Posts_activity.this,posts);

                recyclerView.setAdapter(postAdapter);


               db.addPosts(posts);
               Posts_activity.posts = posts;
               //getPermition();
            }
        });

    }

    private void getFromDatabase(){

        List<Post> posts = db.getPosts();
        PostAdapter postAdapter = new PostAdapter(Posts_activity.this,posts);
        recyclerView.setAdapter(postAdapter);

    }



    public boolean isInternetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        else
        return false;
    }

    private void saveImageToMemory(List<Post> posts){


        folder = new File(Environment.getExternalStorageDirectory(),FOLDER_NAME);
        if(!folder.exists()){
            folder.mkdir();
        }

        for (int i = 0; i < posts.size(); i++) {

            List<String> urls = new ArrayList<>();

            urls.add(posts.get(i).getImageUrl());
            picSave(urls);

        }

    }


    private void picSave (final List<String> urls){


                for (int i = 0; i < urls.size(); i++) {

                    url = urls.get(i);

                    Picasso.get().load(url).into(targetM);
                    Log.i(TAG, "link  "+url);
                }



    }


    private  Target targetM = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            Log.i(TAG, "BitmapRecived: Ok,Done");

            String picName = url.substring(url.lastIndexOf("/")+1,url.length());
            File imageFile = new File(folder,picName);
            try {
                imageFile.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                FileOutputStream fos = new FileOutputStream(imageFile);
                boolean saved = bitmap.compress(Bitmap.CompressFormat.JPEG,75,fos);
                fos.flush();
                fos.close();

                Log.i(TAG, "BitmapRecived: "+saved+" "+url);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            Log.i(TAG, "onBitmapFailed: "+e.toString());

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    private void getPermition(){

        if (ContextCompat.checkSelfPermission(Posts_activity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(Posts_activity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(Posts_activity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQ_PERM_CODE_WRITE_EXTRE_STORAGE);

            }
        } else {
            saveImageToMemory(posts);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQ_PERM_CODE_WRITE_EXTRE_STORAGE: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    saveImageToMemory(posts);
                } else {

                    Toast.makeText(this, "دسترسی لازم به برنامه داده نشد", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }



}
