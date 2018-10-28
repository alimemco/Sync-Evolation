package com.ali.memco.evolaptop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ali.memco.evolaptop.R;
import com.ali.memco.evolaptop.dataModel.Cloth;

import java.util.List;

public class ClothAdapter extends RecyclerView.Adapter<ClothAdapter.ClothViewHolder> {

    private Context context;
    private List<Cloth> clothes;

    public ClothAdapter (Context context, List<Cloth> clothes){

        this.context = context;
        this.clothes = clothes;
    }


    @NonNull
    @Override
    public ClothViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_cloth_item,parent,false);
        return new ClothViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ClothViewHolder holder, int position) {

        Cloth cloth = clothes.get(position);
        holder.clothImg.setImageDrawable(cloth.getClothImg());
        holder.clothTitle.setText(cloth.getClothTitle());
        holder.clothCountView.setText(String.valueOf(cloth.getCountView()));


    }

    @Override
    public int getItemCount() {
        return clothes.size();
    }

    public class ClothViewHolder extends RecyclerView.ViewHolder {

        private ImageView clothImg;
        private TextView clothTitle;
        private TextView clothCountView;

        public ClothViewHolder(View itemView) {
            super(itemView);

            clothImg = itemView.findViewById(R.id.cloth_img_pic);
            clothTitle = itemView.findViewById(R.id.cloth_titleTxt);
            clothCountView = itemView.findViewById(R.id.cloth_countViewTxt);


        }
    }
}
