package com.ali.memco.evolaptop.dataModel;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ApiService {

    private static final String TAG = "ApiService";

    private String city = "Abadan,IR";
    private String LinkApi = "http://api.openweathermap.org/data/2.5/weather?q=Abadan,IR&appid=8ee23d5ab14ba00451934e0810da178f";
    // public static String postApi="http://192.168.43.99/JsonPhp/getposts.php";
    public static String postApi = "http://insta-pro.ir/app/app/json/getPosts.php";
    public static String signUpUserApi = "http://insta-pro.ir/app/app/json/addUser.php";

    private Context context;

    public ApiService(Context context) {

        this.context = context;
    }


    public void getCurrentWeather(final OnWeatherInfoRecived onWeatherInfoRecived, String city) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, LinkApi,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, "onResponse: " + response.toString());
                onWeatherInfoRecived.onRecived(parseWheaterInfo(response));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: " + error.toString());
                onWeatherInfoRecived.onRecived(null);
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(800, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);

    }

    public void getPosts(final OnPostsRecived onPostsRecived) {

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, postApi, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                List<Post> posts = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    Post post = new Post();
                    try {
                        JSONArray jsonArray = response.getJSONArray(i);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        post.setId(jsonObject.getInt("id"));
                        post.setTitle(jsonObject.getString("post_title"));
                        post.setContent(jsonObject.getString("post_content"));
                        post.setImageUrl(jsonObject.getString("post_img_url"));

                        posts.add(post);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                onPostsRecived.onRecived(posts);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: " + error);
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(8000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);


    }


    private WheaterInfo parseWheaterInfo(JSONObject response) {

        WheaterInfo wheaterInfo = new WheaterInfo();

        try {

            JSONArray weatherJsonArray = response.getJSONArray("weather");
            JSONObject weatherjsonObject = weatherJsonArray.getJSONObject(0);

            wheaterInfo.setWeatherName(weatherjsonObject.getString("main"));
            wheaterInfo.setWeatherDescription(weatherjsonObject.getString("description"));

            JSONObject mainjsonObject = response.getJSONObject("main");
            wheaterInfo.setWeatherTemprature((float) mainjsonObject.getDouble("temp"));
            wheaterInfo.setHumidity(mainjsonObject.getInt("humidity"));
            wheaterInfo.setPressure(mainjsonObject.getInt("pressure"));
            wheaterInfo.setMinTemprature((float) mainjsonObject.getDouble("temp_min"));
            wheaterInfo.setMaxTemprature((float) mainjsonObject.getDouble("temp_max"));

            JSONObject windJsonObject = response.getJSONObject("wind");
            wheaterInfo.setWindSpeed((float) windJsonObject.getDouble("speed"));
            wheaterInfo.setWindDegree((float) windJsonObject.getDouble("deg"));

            return wheaterInfo;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }

    public void SignupUser(JSONObject jsonObject, final onSignupUser onSignupUser){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, signUpUserApi, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean res = response.getBoolean("result");
                    onSignupUser.onSignup(res);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onSignupUser.onSignup(false);
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(8000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }


    public interface OnWeatherInfoRecived {

        void onRecived(WheaterInfo wheaterInfo);
    }

    public interface OnPostsRecived {
        void onRecived(List<Post> posts);
    }

    public interface onSignupUser{
        void onSignup(boolean res);
    }

}
