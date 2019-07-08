package edu.cecar.syscmdb.fragments.locations;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.cecar.syscmdb.R;
import edu.cecar.syscmdb.adapters.LocationAdapter;
import edu.cecar.syscmdb.data.model.Location;
import edu.cecar.syscmdb.data.model.VolleYSingleton;

/**
 * A simple {@link Fragment} subclass.
 */
public class GeneralLocationView extends Fragment {
    private LocationViewModel mViewModel;
    private VolleYSingleton volleYSingleton;
    private RecyclerView mRecyclerView;
    private LocationAdapter locationAdapter;
    private List<Location> locationList;
    public GeneralLocationView() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        this.getActivity().setTitle(R.string.title_fragment_Location);
        mRecyclerView = view.findViewById(R.id.rcyViewLocation);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        volleYSingleton = VolleYSingleton.getInstance(getContext());
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this.getActivity()).get(LocationViewModel.class);
        mViewModel.setVolleySingleton(volleYSingleton);
        mViewModel.getLocations().observe(this, new Observer<List<Location>>() {
            @Override
            public void onChanged(@Nullable List<Location> locations) {
                //Actualizar gui
                locationAdapter = new LocationAdapter(getContext(), locations, R.layout.layout_location, new LocationAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Location location, int position) {

                    }
                });
                mRecyclerView.setAdapter(locationAdapter);
            }
        });
        //txtContacTextView.setText(String.valueOf(mViewModel.totalContacts()));
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // mListener = null;
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {

        menu.findItem(R.id.create_item).setVisible(true);
        menu.findItem(R.id.create_item).setEnabled(true);
        menu.findItem(R.id.edit).setVisible(true);
        menu.findItem(R.id.edit).setEnabled(true);
        super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.create_item) {
            Log.i("LocationFragment","ItemCreate");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
