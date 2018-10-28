package com.ali.memco.evolaptop.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.memco.evolaptop.R;
import com.ali.memco.evolaptop.adapter.ClothAdapter;
import com.ali.memco.evolaptop.dataModel.DataFakeGenrator;

public class Botick_Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_clothes,container,false);

        RecyclerView recyclerView = view.findViewById(R.id.botik_rcView);
        ClothAdapter adapter = new ClothAdapter(getActivity(), DataFakeGenrator.getClothes(getActivity()));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        return view;
    }

    public static Botick_Fragment newInstance() {

        Bundle args = new Bundle();

        Botick_Fragment fragment = new Botick_Fragment();
        fragment.setArguments(args);
        return fragment;
    }
}
