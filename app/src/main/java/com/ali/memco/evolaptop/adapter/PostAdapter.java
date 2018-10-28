package com.ali.memco.evolaptop.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.memco.evolaptop.DbOpenHelper;
import com.ali.memco.evolaptop.PostDetail;
import com.ali.memco.evolaptop.dataModel.Post;
import com.ali.memco.evolaptop.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.NewsHolder>{

    private static final String TAG = "PostAdapter";
    private Context context;
    private List<Post> posts;
    private String url;

    private DbOpenHelper db;
    public PostAdapter(Context context, List<Post> posts){

        this.context = context;
        this.posts = posts;

    }


    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.news_style,parent,false);

        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsHolder holder, final int position) {

        final Post post = posts.get(position);
        url = post.getImageUrl();
        Picasso.get().load(post.getImageUrl().replace("localhost","192.168.43.99")).into(holder.news_img);
        //Picasso.get().load(url).into(targetM);
        holder.news_title.setText(post.getTitle());
        holder.news_content.setText(post.getContent());
        holder.news_date.setText(post.getDate());
        if (post.getVisit()==1){
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context,android.R.color.holo_blue_light));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostDetail.class);
                intent.putExtra(db.ID,post.getId());
                intent.putExtra(db.TITLE,post.getTitle());
                intent.putExtra(db.CONTENT,post.getContent());
                intent.putExtra(db.IMAGE_URL,post.getImageUrl());
                intent.putExtra(db.VISIT,post.getVisit());
                intent.putExtra(db.DATE,post.getDate());
              context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder {
        private ImageView news_img;
        private TextView news_title, news_content, news_date;
        public NewsHolder(View itemView) {
            super(itemView);

            news_img = itemView.findViewById(R.id.news_img_image);
            news_title = itemView.findViewById(R.id.news_txt_titr);
            news_content = itemView.findViewById(R.id.news_txt_content);
            news_date = itemView.findViewById(R.id.news_txt_date);
            db= new DbOpenHelper(context);
        }
    }

    private Target targetM = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

            File folder = new File(Environment.getExternalStorageDirectory(),"aliBit");
            if(!folder.exists()){
                folder.mkdir();
            }

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

}
