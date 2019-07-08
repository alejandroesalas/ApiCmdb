package edu.cecar.syscmdb.fragments.contacts;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.Person;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.cecar.syscmdb.R;
import edu.cecar.syscmdb.adapters.ContactAdapter;
import edu.cecar.syscmdb.data.model.VolleYSingleton;


public class ContactPersonFragment extends Fragment {
    //recycler
    private RecyclerView mRecyclerView;
    private ContactAdapter contactAdapter;
    private List<Person> personList;

    //viewModel
    private ResumeContactViewModel mViewModel;
    //private OnFragmentInteractionListener mListener;
    private VolleYSingleton volleYSingleton;
    public ContactPersonFragment() {
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
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        this.getActivity().setTitle(R.string.title_fragment_Person);
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
        mViewModel.getPersons().observe(this, new Observer<List<edu.cecar.syscmdb.data.model.Person>>() {
            @Override
            public void onChanged(@Nullable List<edu.cecar.syscmdb.data.model.Person> persons) {
                //Actualizar gui
                contactAdapter = new ContactAdapter(getContext(), persons, R.layout.layout_contact_person, new ContactAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Object contact, int position) {

                    }
                });
                mRecyclerView.setAdapter(contactAdapter);
            }
        });
        //txtContacTextView.setText(String.valueOf(mViewModel.totalContacts()));
    }
    /*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.home, menu);

        super.onCreateOptionsMenu(menu, inflater);

    }
    */@Override
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
          Log.i("PersonFragment","ItemCreate");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
