package com.example.ecommerce.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.ecommerce.Customer.customerFragLogin;
import com.example.ecommerce.Shopkeeper.shopkeeperFragLogin;


public class pagerAdapterLogin extends FragmentPagerAdapter {

    private int numOfTabs;

    public pagerAdapterLogin(FragmentManager fm, int numOfTabs){

        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.numOfTabs = numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new customerFragLogin();
            case 1:
                return new shopkeeperFragLogin();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
