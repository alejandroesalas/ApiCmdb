package edu.cecar.syscmdb.fragments.ECs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

import edu.cecar.syscmdb.R;
import edu.cecar.syscmdb.adapters.FunctionalCIAdapter;
import edu.cecar.syscmdb.data.model.FunctionalCI;
import edu.cecar.syscmdb.data.model.VolleYSingleton;

public class ECFragment extends Fragment {
    private Spinner spiFunctionalCIType;
    private RecyclerView recyclerViewCI;
    private VolleYSingleton volleYSingleton;
    private ECViewModel mViewModel;
    private FunctionalCIAdapter adapter;
    private final String[] functionalCItypes ={"Aplicación Web","Dispositivos fisicos","Dispositivod Virtuales",
            "Base de datos(Esquema)","Instalacion Middleware","Instalacion Software",
            "Proceso de Negocio","Solucion Aplicativa"};
    public ECFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_create_ec, container, false);
        spiFunctionalCIType = view.findViewById(R.id.spFunctionalType);
        spiFunctionalCIType.setAdapter(new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,functionalCItypes));
        recyclerViewCI = view.findViewById(R.id.recyclerEc);
        recyclerViewCI.setHasFixedSize(true);
        recyclerViewCI.setLayoutManager(new LinearLayoutManager(this.getContext()));
        volleYSingleton = VolleYSingleton.getInstance(getContext());
        spiFunctionalCIType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("SPinner clicleado",functionalCItypes[position]);
                mViewModel.getFuncTionalCI(functionalCItypes[position]).observe(getActivity(), new Observer<List<FunctionalCI>>() {
                    @Override
                    public void onChanged(@Nullable List<FunctionalCI> functionalCIS) {
                        adapter = new FunctionalCIAdapter(getContext(),functionalCIS,R.layout.layout_functionalci,new FunctionalCIAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(Object contact, int position) {

                            }
                        });
                        recyclerViewCI.setAdapter(adapter);
                    }
                });
            }
        });
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this.getActivity()).get(ECViewModel.class);
        mViewModel.setVolleySingleton(volleYSingleton);
    }
}
