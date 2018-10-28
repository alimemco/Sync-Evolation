package com.ali.memco.evolaptop.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ali.memco.evolaptop.R;
import com.ali.memco.evolaptop.dataModel.AppFeature;
import com.ali.memco.evolaptop.view.activity.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AppFeatureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<AppFeature> appFeatures;
    private static final int BANNER_VIEW_TYPE = 0;
    private static final int DEFAULT_VIEW_TYPE = 1;


    public AppFeatureAdapter(Context context){
        this.context = context;


    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return BANNER_VIEW_TYPE;
        }else {
            return DEFAULT_VIEW_TYPE;
        }


    }

    public void setupAppFeatureAdapter(List<AppFeature> appFeatures){
        this.appFeatures = appFeatures;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType){

            case BANNER_VIEW_TYPE:
                return new AppFeatureBanner(LayoutInflater.from(context).
                        inflate(R.layout.main_banner,parent,false));

            case DEFAULT_VIEW_TYPE:
                return new AppFeatureViewHolder(LayoutInflater.from(context)
                        .inflate(R.layout.appfeature_item,null,false));

                default:
                    return null;

        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AppFeatureViewHolder){
            AppFeatureViewHolder viewHolder = (AppFeatureViewHolder) holder;
            viewHolder.bindAppFeature(appFeatures.get(position-1));
        }

    }

    @Override
    public int getItemCount() {
        return appFeatures.size()+1;
    }

    public class AppFeatureViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView chapterTitle;
        public AppFeatureViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.feature_image_view);
            chapterTitle = itemView.findViewById(R.id.feature_title);
            chapterTitle.setTypeface(MainActivity.bYkan);


        }


        public void bindAppFeature(final AppFeature appFeature){
            Picasso.get().load(appFeature.getFeatureImage()).into(imageView);
            chapterTitle.setText(appFeature.getTitle());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(itemView.getContext(),appFeature.getDestinationActivity()));
                }
            });

        }
    }

    public class AppFeatureBanner extends RecyclerView.ViewHolder {
        private TextView titleList;
        private TextView titleNews;
        public AppFeatureBanner(View itemView) {
            super(itemView);
            titleList = itemView.findViewById(R.id.main_banner_title_list);
            titleNews = itemView.findViewById(R.id.main_banner_txt_news);
            titleList.setTypeface(MainActivity.bYkan);
            titleNews.setTypeface(MainActivity.iranSans);
        }
    }


}
