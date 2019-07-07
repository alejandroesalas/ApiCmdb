package edu.cecar.syscmdb.fragments.personalinformation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.cecar.syscmdb.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PropertiesFragment extends Fragment {


    public PropertiesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_properties, container, false);
    }

}
