package edu.cecar.syscmdb.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.cecar.syscmdb.R;
import edu.cecar.syscmdb.data.model.Location;
import edu.cecar.syscmdb.data.model.Person;
import edu.cecar.syscmdb.data.model.Team;

public final class LocationAdapter  extends RecyclerView.Adapter<LocationAdapter.ViewHolder>{

    private final Context mCtx;
    private final List<Location> locations;
    private final int layout;
    private final LocationAdapter.OnItemClickListener listener;

    public LocationAdapter(Context mCtx, List<Location> locations,int layout, LocationAdapter.OnItemClickListener listener) {
        this.mCtx = mCtx;
        this.locations = locations;
        this.layout = layout;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View view;
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_location,viewGroup,false);
        return new LocationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.ViewHolder viewHolder, int position) {
        viewHolder.bind(locations.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //private ImageView picture;
        private TextView name;
        private TextView status;
        private TextView address;
        private TextView orgName;
        private TextView city;
        private TextView country;
        public ViewHolder(View itemView){
            super(itemView);
            //se inicializan los elementos segun el layout
           name = itemView.findViewById(R.id.txtNameLo);
           status = itemView.findViewById(R.id.txtStatusLo);
           address = itemView.findViewById(R.id.txtAddressLo);
           orgName = itemView.findViewById(R.id.txtOrNameLo);
           city = itemView.findViewById(R.id.txtCityLo);
           country = itemView.findViewById(R.id.txtCountryLo);
        }

        public void bind(final Location location,final LocationAdapter.OnItemClickListener listener) {
            //actualizan los datos en el recycler
                Location theLocation1 =  location;
                name.setText(theLocation1.getName());
                status.setText(theLocation1.getStatus());
                address.setText(theLocation1.getAddress());
                orgName.setText(theLocation1.getOrgName());
                city.setText(theLocation1.getCity());
                country.setText(theLocation1.getCountry());
                /*
                if (!theLocation1.getPicture().getData().isEmpty()){
                    byte[] imageByteArray = Base64.decode(theLocation1.getPicture().getData(), Base64.DEFAULT);
                    Glide.with(mCtx).load(imageByteArray).asBitmap()
                            .placeholder(R.drawable.ic_boss)
                            .into(picture);
                }*/
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(location, getAdapterPosition());
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(Location location, int position);
    }
}
