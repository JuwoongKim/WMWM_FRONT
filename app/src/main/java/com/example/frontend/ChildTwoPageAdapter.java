package com.example.frontend;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChildTwoPageAdapter extends FragmentStateAdapter {
    private final List<Fragment> FragmentList = new ArrayList<Fragment>();

    public ChildTwoPageAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    public void addFragment(Fragment fragment){
        FragmentList.add(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return FragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return FragmentList.size();
    }
}
