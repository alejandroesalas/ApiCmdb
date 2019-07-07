package edu.cecar.syscmdb.activity;

import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import edu.cecar.syscmdb.R;
import edu.cecar.syscmdb.adapters.GroupSectionsPagerAdapter;

public class WorkGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_group);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GroupSectionsPagerAdapter groupSectionsPagerAdapter = new GroupSectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewPagerWorkGroup);
        viewPager.setAdapter(groupSectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabsWorkGroup);
        tabs.setupWithViewPager(viewPager);

    }

}
