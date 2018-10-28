package com.ali.memco.evolaptop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ali.memco.evolaptop.AsyncTask.AddPostTask;
import com.ali.memco.evolaptop.dataModel.Post;

import java.util.ArrayList;
import java.util.List;

public class DbOpenHelper extends SQLiteOpenHelper {



    private static final String TAG = "DbOpenHelper";

    private static final String DATABASE_NAME = "aliRnp";
    private static final int DATABASE_VERSION = 1;
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String IMAGE_URL = "image_url";
    public static final String VISIT = "visit";
    public static final String DATE = "date";

    public static final String TABLE_NAME = "Posts";

    private static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TITLE + " TEXT, "
            + CONTENT + " TEXT, "
            + IMAGE_URL + " TEXT, "
            + VISIT + " INTEGER DEFAULT 0, "
            + DATE + " TEXT);";

    private Context context;

    public DbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addPost(Post post) {

        ContentValues cv = new ContentValues();
        cv.put(ID, post.getId());
        cv.put(TITLE, post.getTitle());
        cv.put(CONTENT, post.getContent());
        cv.put(IMAGE_URL, post.getImageUrl());
        cv.put(VISIT, post.getVisit());
        cv.put(DATE, post.getDate());

        SQLiteDatabase db = this.getWritableDatabase();
        long aded = db.insert(TABLE_NAME, null, cv);

        Log.i(TAG, "addpost: " + aded);

        if (aded != -1) {
            return true;
        } else {
            return false;
        }

    }

    public void addPosts(List<Post> posts) {

        AddPostTask addPostTask = new AddPostTask(context,posts,this);
        addPostTask.execute();
/*
        for (int i = 0; i < posts.size(); i++) {

            if (checkExistPost(posts.get(i))){

                updateTable(posts.get(i));
                Log.i(TAG, "addPosts: update mikone");



            }else {
                addPost(posts.get(i));
                Log.i(TAG, "addPosts: insert mikone");
            }


        }
*/

    }

    public void updateTable(Post post){

        ContentValues cv = new ContentValues();
        cv.put(ID, post.getId());
        cv.put(TITLE, post.getTitle());
        cv.put(CONTENT, post.getContent());
        cv.put(IMAGE_URL, post.getImageUrl());
        cv.put(VISIT, post.getVisit());
        cv.put(DATE, post.getDate());

        SQLiteDatabase db = this.getWritableDatabase();
        long updated = db.update(TABLE_NAME,cv,ID+" = ?",new String[]{String.valueOf(post.getId())});

        Log.i(TAG, "updatePost: " + updated);
    }

    public boolean checkExistPost(Post post){

        int PostId = post.getId();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cu = db.rawQuery("SELECT * FROM "+TABLE_NAME
                +" WHERE "
                +ID
                +" = "
                +PostId,null);
        Log.i(TAG, "checkExistPost: "+cu.toString());
        if (cu.getCount()>0){
            return true;

        }else {
            return false;
        }
    }

    public List<Post> getPosts() {

        List<Post> posts = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {

            while (!cursor.isAfterLast()) {

                Post post = new Post();
                post.setId(cursor.getInt(0));
                post.setTitle(cursor.getString(1));
                post.setContent(cursor.getString(2));
                post.setImageUrl(cursor.getString(3));
                post.setVisit(cursor.getInt(4));
                post.setDate(cursor.getString(5));
                posts.add(post);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return posts;
    }

    public void setVisited(int PostId, int value) {

        ContentValues cv = new ContentValues();
        cv.put(VISIT, value);
        SQLiteDatabase db = this.getWritableDatabase();
        int updateVis = db.update(TABLE_NAME, cv, ID + " = ? ", new String[]{String.valueOf(PostId)});
        Log.i(TAG, "setVisited: " + updateVis);
        db.close();
    }



}
