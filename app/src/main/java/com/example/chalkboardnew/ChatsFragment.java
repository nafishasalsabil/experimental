package com.example.chalkboardnew;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class ChatsFragment extends Fragment {
    Toolbar chats_toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        View view =  inflater.inflate(R.layout.fragment_chats,container,false);
    //chats_toolbar = view.findViewById(R.id.toolbar_chats);
      tabLayout = view.findViewById(R.id.table_layout);
      viewPager = view.findViewById(R.id.my_viewpager);
     //   ((AppCompatActivity)getActivity()).setSupportActionBar(chats_toolbar);
     //  ((Features) getActivity()).getSupportActionBar();
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);












        return view;
    }
    public static ChatsFragment newInstance() {
        ChatsFragment fragment = new ChatsFragment();


        return fragment;
    }
    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new Messages_fragment(),"Messages");
        viewPagerAdapter.addFragment(new Users_fragment(),"Users");
        viewPager.setAdapter(viewPagerAdapter);

    }
}
