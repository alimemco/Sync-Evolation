package com.ali.memco.evolaptop.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ali.memco.evolaptop.view.fragment.Botick_Fragment;

public class ViewPageClothesAdapter extends FragmentPagerAdapter{
    public ViewPageClothesAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return Botick_Fragment.newInstance();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){

            case 0:
                return "برترین ها";

            case 1:
                return "جدیدترین ها";

            case 2:
                return "پرفروش ترین ها";

                default:
                    return "";

        }

    }
}
