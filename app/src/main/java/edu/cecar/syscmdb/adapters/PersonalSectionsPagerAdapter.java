package edu.cecar.syscmdb.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import edu.cecar.syscmdb.R;
import edu.cecar.syscmdb.fragments.ECs.ECsFragment;
import edu.cecar.syscmdb.fragments.personalinformation.GroupsFragment;
import edu.cecar.syscmdb.fragments.personalinformation.PropertiesFragment;


public class PersonalSectionsPagerAdapter extends FragmentPagerAdapter {
    private Fragment currenFragment;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;

    public PersonalSectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: currenFragment = new PropertiesFragment();
                break;
            case 1: currenFragment = new GroupsFragment();
                break;
            case 2: currenFragment = new ECsFragment();
                break;
            default: currenFragment = new PropertiesFragment();
                break;
        }
        return currenFragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }
}