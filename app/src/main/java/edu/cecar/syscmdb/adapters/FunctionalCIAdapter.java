package edu.cecar.syscmdb.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.cecar.syscmdb.data.model.FunctionalCI;

public class FunctionalCIAdapter extends RecyclerView.Adapter<FunctionalCIAdapter.ViewHolder> {
    private final Context mCtx;
    private final List<FunctionalCI> functionalCIList;
    private final int layout;
    private final FunctionalCIAdapter.OnItemClickListener listener;

    public FunctionalCIAdapter(Context mCtx, List<FunctionalCI> functionalCIList, int layout, FunctionalCIAdapter.OnItemClickListener listener) {
        this.mCtx = mCtx;
        this.functionalCIList = functionalCIList;
        this.layout = layout;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FunctionalCIAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(viewGroup.getContext()).inflate(layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FunctionalCIAdapter.ViewHolder viewHolder, int position) {
        viewHolder.bind(functionalCIList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return functionalCIList.size();
    }
    //
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView key;
        private TextView name;
        private TextView description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Inicializo los campos
        }

        public void bind(final FunctionalCI functionalCI, final FunctionalCIAdapter.OnItemClickListener listener) {
            key.setText(String.valueOf(functionalCI.getKey()));
            name.setText(functionalCI.getName());
            description.setText(functionalCI.getDesc());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(functionalCI, getAdapterPosition());
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(Object contact, int position);
    }
}
