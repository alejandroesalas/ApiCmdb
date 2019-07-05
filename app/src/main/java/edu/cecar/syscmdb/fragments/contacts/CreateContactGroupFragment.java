package edu.cecar.syscmdb.fragments.contacts;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateContactGroupFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateContactGroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateContactGroupFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateContactGroupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateContactGroupFragment newInstance(String param1, String param2) {
        CreateContactGroupFragment fragment = new CreateContactGroupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_contact_person, container, false);
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


  /*  // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
