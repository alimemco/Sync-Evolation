package com.ali.memco.evolaptop.dataModel;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;

import com.ali.memco.evolaptop.R;
import com.ali.memco.evolaptop.view.activity.AnimationActivity;
import com.ali.memco.evolaptop.view.activity.Botick_activity;
import com.ali.memco.evolaptop.view.activity.MainActivity;
import com.ali.memco.evolaptop.view.activity.Posts_activity;
import com.ali.memco.evolaptop.view.activity.Profile_Activity;

import java.util.ArrayList;
import java.util.List;

public class DataFakeGenrator {

    public static List<Cloth> getClothes(Context context){

        List<Cloth> clothes = new ArrayList<>();

        for (int i = 1; i <= 8; i++) {

            Cloth cloth = new Cloth();

            cloth.setId(i);
            cloth.setClothTitle("عنوان هر محصول");

            switch (i){

                case 1:
                    cloth.setCountView(700);
                    cloth.setClothImg(ResourcesCompat.getDrawable(context.getResources(),R.drawable.pic1_clothes,null));
                break;

                case 2:
                    cloth.setCountView(241);
                    cloth.setClothImg(ResourcesCompat.getDrawable(context.getResources(),R.drawable.pic2__clothes,null));
                    break;


                case 3:
                    cloth.setCountView(342);
                    cloth.setClothImg(ResourcesCompat.getDrawable(context.getResources(),R.drawable.pic3_clothes,null));
                    break;


                case 4:
                    cloth.setCountView(112);
                    cloth.setClothImg(ResourcesCompat.getDrawable(context.getResources(),R.drawable.pic4_clothes,null));
                    break;


                case 5:
                    cloth.setCountView(22);
                    cloth.setClothImg(ResourcesCompat.getDrawable(context.getResources(),R.drawable.pic5_clothes,null));
                    break;


                case 6:
                    cloth.setCountView(21);
                    cloth.setClothImg(ResourcesCompat.getDrawable(context.getResources(),R.drawable.pic6_clothes,null));
                    break;


                case 7:
                    cloth.setCountView(85);
                    cloth.setClothImg(ResourcesCompat.getDrawable(context.getResources(),R.drawable.pic7_clothes,null));
                    break;


                case 8:
                    cloth.setCountView(24);
                    cloth.setClothImg(ResourcesCompat.getDrawable(context.getResources(),R.drawable.pic8_clothes,null));
                    break;




            }
            clothes.add(cloth);
        }

        return clothes;
    }


    public static List<AppFeature> getAppFeature(Context context){

        List<AppFeature> appFeatures = new ArrayList<>();


        for (int i = 1; i <= 8; i++) {
            AppFeature appFeature = new AppFeature();

            switch (i){

                case 1:
                    appFeature.setId(AppFeature.ID_POSTS_ACTIVITY);
                    appFeature.setTitle(context.getString(R.string.title_POSTS_ACTIVITY));
                    appFeature.setFeatureImage(R.drawable.posts);
                    appFeature.setDestinationActivity(Posts_activity.class);
                    break;


                case 2:
                    appFeature.setId(AppFeature.ID_USER_PROFILE);
                    appFeature.setTitle(context.getString(R.string.title_USER_PROFILE));
                    appFeature.setFeatureImage(R.drawable.user_profile);
                    appFeature.setDestinationActivity(Profile_Activity.class);
                    break;


                case 3:
                    appFeature.setId(AppFeature.ID_FASHION);
                    appFeature.setTitle(context.getString(R.string.title_FASHION));
                    appFeature.setFeatureImage(R.drawable.jacket);
                    appFeature.setDestinationActivity(Botick_activity.class);
                    break;


                case 4:
                    appFeature.setId(AppFeature.ID_MUSIC);
                    appFeature.setTitle(context.getString(R.string.title_MUSIC));
                    appFeature.setFeatureImage(R.drawable.music_player);
                    appFeature.setDestinationActivity(MainActivity.class);
                    break;


                case 5:
                    appFeature.setId(AppFeature.ID_VIDEO);
                    appFeature.setTitle(context.getString(R.string.title_VIDEO));
                    appFeature.setFeatureImage(R.drawable.video_player);
                    appFeature.setDestinationActivity(MainActivity.class);
                    break;


                case 6:
                    appFeature.setId(AppFeature.ID_LOGIN);
                    appFeature.setTitle(context.getString(R.string.title_LOGIN));
                    appFeature.setFeatureImage(R.drawable.login);
                    appFeature.setDestinationActivity(MainActivity.class);
                    break;


                case 7:
                    appFeature.setId(AppFeature.ID_ANIMATION);
                    appFeature.setTitle(context.getString(R.string.title_ANIMATION));
                    appFeature.setFeatureImage(R.drawable.animations_in_android);
                    appFeature.setDestinationActivity(AnimationActivity.class);
                    break;

                case 8:
                    appFeature.setId(AppFeature.ID_MAP);
                    appFeature.setTitle(context.getString(R.string.title_MAP));
                    appFeature.setFeatureImage(R.drawable.google_map);
                    appFeature.setDestinationActivity(AnimationActivity.class);
                    break;


            }
            appFeatures.add(appFeature);

        }

        return appFeatures;
    }
}
