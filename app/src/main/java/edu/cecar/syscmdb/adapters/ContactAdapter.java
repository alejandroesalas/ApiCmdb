package edu.cecar.syscmdb.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import edu.cecar.syscmdb.R;
import edu.cecar.syscmdb.data.model.Person;
import edu.cecar.syscmdb.data.model.Team;

public final class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private final Context mCtx;
    private final List contacts;
    private final int layout;
    private final OnItemClickListener listener;

    public ContactAdapter(Context mCtx, List contacts,int layout, OnItemClickListener listener) {
        this.mCtx = mCtx;
        this.contacts = contacts;
        this.layout = layout;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View view;
        switch (layout){
            case R.layout.layout_contact_group:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_contact_group,viewGroup,false);
                break;
            default:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_contact_person,viewGroup,false);
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.bind(contacts.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView picture;
        private TextView name;
        private TextView status;
        private TextView email;
        private TextView orgName;
        private TextView phone;
        private TextView locationName;
        public ViewHolder(View itemView){
            super(itemView);
             //se inicializan los elementos segun el layout
            switch (layout){
                case R.layout.layout_contact_group:
                        picture = itemView.findViewById(R.id.groupImageView);
                        name = itemView.findViewById(R.id.txtgroupName);
                        status = itemView.findViewById(R.id.txtGroupStatus);
                        email = itemView.findViewById(R.id.txtGroupEmail);
                        orgName = itemView.findViewById(R.id.txtGroupOrgName);
                    break;
                default:
                    picture = itemView.findViewById(R.id.contactImageView);
                    name = itemView.findViewById(R.id.txtPersonName);
                    status = itemView.findViewById(R.id.txtPersonStatus);
                    email = itemView.findViewById(R.id.txtPersonEmail);
                    locationName = itemView.findViewById(R.id.txtPersonLocation);
                    phone = itemView.findViewById(R.id.txtPersonPhone);
                    break;
            }
        }

        public void bind(final Object contact,final OnItemClickListener listener) {
            //actualizan los datos en el recycler
            if (contact instanceof Person){
                Person thePerson = (Person) contact;
                //falta colocar la foto
                name.setText(thePerson.getFriendlyname());
                status.setText(thePerson.getStatus());
                email.setText(thePerson.getEmail());
                locationName.setText(thePerson.getLocation().getName());
                phone.setText(thePerson.getMobilePhone());
                if (!thePerson.getPicture().getData().isEmpty()){
                    byte[] imageByteArray = Base64.decode(thePerson.getPicture().getData(), Base64.DEFAULT);
                    Glide.with(mCtx).load(imageByteArray).asBitmap()
                            .placeholder(R.drawable.ic_boss)
                            .into(picture);
                }
            }else if (contact instanceof Team){
                Team theTeam = (Team) contact;
                //falta colocar la foto
                name.setText(theTeam.getName());
                status.setText(theTeam.getStatus());
                email.setText(theTeam.getEmail());
                orgName.setText(theTeam.getOrgName());
                Glide.with(mCtx).load(R.mipmap.ic_group).asBitmap()
                        .placeholder(R.mipmap.ic_group)
                        .into(picture);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(contact, getAdapterPosition());
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(Object contact, int position);
    }
}
