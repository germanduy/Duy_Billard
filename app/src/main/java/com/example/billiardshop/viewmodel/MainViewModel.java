package com.example.billiardshop.viewmodel;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.billiardshop.R;
import com.example.billiardshop.adapter.MainViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainViewModel {

    public ObservableBoolean isShowToolbar = new ObservableBoolean();
    public ObservableField<String>title = new ObservableField<>();

    @BindingAdapter({"item_selected"})
    public static void setOnNavigationItemSelectedListener(BottomNavigationView bottomNavigationView, ViewPager2 viewPager2){
        viewPager2.setUserInputEnabled(false);
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter((FragmentActivity) viewPager2.getContext());
        viewPager2.setAdapter(mainViewPagerAdapter);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    viewPager2.setCurrentItem(0);
                } else if (id == R.id.nav_cart) {
                    viewPager2.setCurrentItem(1);
                } else if (id == R.id.nav_feedback) {
                    viewPager2.setCurrentItem(2);
                } else if (id == R.id.nav_contact) {
                    viewPager2.setCurrentItem(3);
                } else if (id == R.id.nav_order) {
                    viewPager2.setCurrentItem(4);
                }
                return true;
            }
        });

    }
}
