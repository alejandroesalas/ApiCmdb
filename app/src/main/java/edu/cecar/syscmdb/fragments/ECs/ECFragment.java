package edu.cecar.syscmdb.fragments.ECs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private final String[] functionalCItypes ={"Aplicación Web","Dispositivos fisicos","Dispositivos Virtuales",
            "Base de datos(Esquema)","Instalacion Middleware","Instalacion Software",
            "Proceso de Negocio","Solucion Aplicativa"};
    public ECFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.home, menu);

        super.onCreateOptionsMenu(menu, inflater);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Log.i("id",id+"");
        if (id == R.id.create_item) {
            Log.i("PersonFragment","ItemCreate");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_ec, container, false);
        spiFunctionalCIType = view.findViewById(R.id.spFunctionalType);
        spiFunctionalCIType.setAdapter(new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,functionalCItypes));
        recyclerViewCI = view.findViewById(R.id.recyclerEc);
        recyclerViewCI.setHasFixedSize(true);
        recyclerViewCI.setLayoutManager(new LinearLayoutManager(this.getContext()));
        volleYSingleton = VolleYSingleton.getInstance(getContext());
       /* spiFunctionalCIType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        });¨*/
        spiFunctionalCIType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = String.valueOf(spiFunctionalCIType.getItemAtPosition(position));
                Log.i("SPinner clicleado",selectedItem);
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

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
