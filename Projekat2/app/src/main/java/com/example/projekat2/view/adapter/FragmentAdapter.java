package com.example.projekat2.view.adapter;

import android.content.Context;

import com.example.projekat2.R;
import com.example.projekat2.view.fragment.ChatFragment;
import com.example.projekat2.view.fragment.RasporedFragment;
import com.example.projekat2.view.fragment.WallFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {

    private static int FRAGMENT_COUNT = 3;

    private static int FRAGMENT_RASPORED = 0;
    private static int FRAGMENT_CHAT = 1;
    private static int FRAGMENT_WALL = 2;

    private Context context;

    public FragmentAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==FRAGMENT_RASPORED) {
            return context.getString(R.string.fragment_raspored_title);
        } else if(position==FRAGMENT_CHAT) {
            return context.getString(R.string.fragment_chat_title);
        } else if(position==FRAGMENT_WALL) {
            return context.getString(R.string.fragment_wall_title);
        }
        return super.getPageTitle(position);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==FRAGMENT_RASPORED) {
            return RasporedFragment.newInstance();
        }else if(position==FRAGMENT_CHAT) {
            return ChatFragment.newInstance();
        }else if(position==FRAGMENT_WALL){
            return WallFragment.newInstance();
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }
}
