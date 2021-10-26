package com.example.carebot.homeFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carebot.R;
import com.example.carebot.adapter.healthFragmentAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class healthFragment extends Fragment {
    private String[] titles = new String[]{"Status","heartBeat"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_health, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager2 viewPager2 = view.findViewById(R.id.view_health_below);
        TabLayout tabLayout = view.findViewById(R.id.tab_health);
        healthFragmentAdapter healthFragmentAdapter = new healthFragmentAdapter(getActivity());
        viewPager2.setAdapter(healthFragmentAdapter);
        new TabLayoutMediator(tabLayout,viewPager2,((tab, position) -> tab.setText(titles[position]))).attach();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        topStatusFragment NAME = new topStatusFragment();
        fragmentTransaction.replace(R.id.view_health_above, NAME);
        fragmentTransaction.commit();
        tabLayout.setBackgroundColor(getResources().getColor(R.color.blue));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                switch (tab.getPosition()){
                    case 0:
                        topStatusFragment NAME = new topStatusFragment();
                        fragmentTransaction.replace(R.id.view_health_above, NAME);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.blue));
                        fragmentTransaction.commit();
                        break;
                    case 1:
                        topHeartBeatFragment NAM = new topHeartBeatFragment();
                        fragmentTransaction.replace(R.id.view_health_above, NAM);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.red));
                        fragmentTransaction.commit();
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}