package edu.cecar.syscmdb.fragments.contacts;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.Person;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.cecar.syscmdb.R;
import edu.cecar.syscmdb.adapters.ContactAdapter;
import edu.cecar.syscmdb.data.model.Team;
import edu.cecar.syscmdb.data.model.VolleYSingleton;

public class CreateContactGroupFragment extends Fragment {
    //recycler
    private RecyclerView mRecyclerView;
    private ContactAdapter contactAdapter;
    private List<Person> personList;

    //viewModel
    private ResumeContactViewModel mViewModel;
    //private OnFragmentInteractionListener mListener;
    private VolleYSingleton volleYSingleton;
    //private OnFragmentInteractionListener mListener;

    public CreateContactGroupFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_contact, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerViewPersons);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        volleYSingleton = VolleYSingleton.getInstance(getContext());
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this.getActivity()).get(ResumeContactViewModel.class);
        mViewModel.setVolleySingleton(volleYSingleton);
        mViewModel.getTeams().observe(this, new Observer<List<Team>>() {
            @Override
            public void onChanged(@Nullable List<Team> persons) {
                //Actualizar gui
                contactAdapter = new ContactAdapter(getContext(), persons, R.layout.layout_contact_group, new ContactAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Object contact, int position) {

                    }
                });
                mRecyclerView.setAdapter(contactAdapter);
            }
        });
        //txtContacTextView.setText(String.valueOf(mViewModel.totalContacts()));
    }

}
