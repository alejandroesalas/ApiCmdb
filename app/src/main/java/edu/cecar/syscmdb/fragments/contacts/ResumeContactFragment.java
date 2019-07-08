package edu.cecar.syscmdb.fragments.contacts;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.cecar.syscmdb.R;
import edu.cecar.syscmdb.data.model.Person;
import edu.cecar.syscmdb.data.model.Team;
import edu.cecar.syscmdb.data.model.VolleYSingleton;

public class ResumeContactFragment extends Fragment {

    private ResumeContactViewModel mViewModel;
    private ImageView groupImageView, contacImageView;
    private TextView txtContacTextView,txtGroupTextView,txtPersonTextView;
    private VolleYSingleton volleYSingleton;
    public static ResumeContactFragment newInstance() {
        return new ResumeContactFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.getActivity().setTitle(R.string.title_fragment_contact);
        View view = inflater.inflate(R.layout.resume_contact_fragment, container, false);
        groupImageView = (ImageView) view.findViewById(R.id.groupImageView);
        contacImageView = (ImageView) view.findViewById(R.id.contactImageView);
        txtGroupTextView = (TextView) view.findViewById(R.id.txtGroupCount);
        //txtContacTextView = (TextView) view.findViewById(R.id.txtContactCount);
        txtPersonTextView = (TextView)view.findViewById(R.id.txtPersonCount) ;
        volleYSingleton = VolleYSingleton.getInstance(getContext());
       // ActionBar actionBar =
       // actionBar.setTitle("Pedido");
       // actionBar.setDisplayHomeAsUpEnabled(true);
        mViewModel = ViewModelProviders.of(this.getActivity()).get(ResumeContactViewModel.class);
        mViewModel.setVolleySingleton(volleYSingleton);
        groupImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment nextFragment = new ContactGroupFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentFrame, nextFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        contacImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment nextFragment = new ContactPersonFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentFrame, nextFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
                return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel.totalContacts();
         mViewModel.getPersons().observe(this, new Observer<List<Person>>() {
             @Override
             public void onChanged(@Nullable List<Person> persons) {
                 //Actualizar gui
                 txtPersonTextView.setText(String.valueOf(persons.size()));
             }
         });
         mViewModel.getTeams().observe(this, new Observer<List<Team>>() {
            @Override
            public void onChanged(@Nullable List<Team> teams) {
                //Actualizar gui
                txtGroupTextView.setText(String.valueOf(teams.size()));
            }
        });

    }

}
