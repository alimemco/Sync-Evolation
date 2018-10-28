package com.ali.memco.evolaptop.AsyncTask;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.ali.memco.evolaptop.DbOpenHelper;
import com.ali.memco.evolaptop.dataModel.Post;
import java.util.List;

import static com.ali.memco.evolaptop.DbOpenHelper.TABLE_NAME;

public class AddPostTask extends AsyncTask<Void,Integer,Void> {

    private static final String TAG = "AddPostTask";

    private Context cotext;
    private List<Post> posts;
    private DbOpenHelper dbOpenHelper;

    public AddPostTask(Context context, List<Post> posts,DbOpenHelper dbOpenHelper){
        this.cotext = context;
        this.posts = posts;
        this.dbOpenHelper = dbOpenHelper;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(Void... voids) {

        for (int i = 0; i < posts.size(); i++) {

            if (checkExistPost(posts.get(i))){

                updateTable(posts.get(i));

            }else {
                addPost(posts.get(i));
            }


        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

    }


    public boolean addPost(Post post) {

        ContentValues cv = new ContentValues();
        cv.put(DbOpenHelper.ID, post.getId());
        cv.put(DbOpenHelper.TITLE, post.getTitle());
        cv.put(DbOpenHelper.CONTENT, post.getContent());
        cv.put(DbOpenHelper.IMAGE_URL, post.getImageUrl());
        cv.put(DbOpenHelper.VISIT, post.getVisit());
        cv.put(DbOpenHelper.DATE, post.getDate());

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        long aded = db.insert(TABLE_NAME, null, cv);

        Log.i(TAG, "addpost: " + post.getId());

        if (aded != -1) {
            return true;
        } else {
            return false;
        }

    }


    public void updateTable(Post post){

        ContentValues cv = new ContentValues();
        cv.put(DbOpenHelper.ID, post.getId());
        cv.put(DbOpenHelper.TITLE, post.getTitle());
        cv.put(DbOpenHelper.CONTENT, post.getContent());
        cv.put(DbOpenHelper.IMAGE_URL, post.getImageUrl());
        cv.put(DbOpenHelper.VISIT, post.getVisit());
        cv.put(DbOpenHelper.DATE, post.getDate());

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        long updated = db.update(TABLE_NAME,cv,DbOpenHelper.ID+" = ?",new String[]{String.valueOf(post.getId())});

        Log.i(TAG, "updatePost: " + post.getId());
    }

    public boolean checkExistPost(Post post){

        int PostId = post.getId();

        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();

        Cursor cu = db.rawQuery("SELECT * FROM "+TABLE_NAME
                +" WHERE "
                +DbOpenHelper.ID
                +" = "
                +PostId,null);
        if (cu.getCount()>0){
            return true;

        }else {
            return false;
        }
    }
}
