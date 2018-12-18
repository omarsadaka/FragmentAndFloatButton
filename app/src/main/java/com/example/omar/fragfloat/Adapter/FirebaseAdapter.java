package com.example.omar.fragfloat.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omar.fragfloat.Model.ListItem;
import com.example.omar.fragfloat.Model.Movies;
import com.example.omar.fragfloat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar on 12/11/2018.
 */

public class FirebaseAdapter extends RecyclerView.Adapter<FirebaseAdapter.viewHolder> implements Filterable{

    private Context context;
    private List<ListItem> listItems;
    private List<ListItem> listItemFilter;
    private AlertDialog.Builder alertDialog;
    private Dialog dialog;
    public FirebaseAdapter(Context context, List<ListItem> listItems) {
        this.context = context;
        this.listItems = listItems;
        this.listItemFilter = listItems;
    }

    @NonNull
    @Override
    public FirebaseAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.firebase_list_row, parent , false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FirebaseAdapter.viewHolder holder, int position) {
        ListItem list = listItemFilter.get(position);

        holder.name.setText(  list.getName());
        holder.job.setText(  list.getJob());
        holder.phone.setText(  list.getPhone());

    }

    @Override
    public int getItemCount() {
        return listItemFilter.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString=constraint.toString();
                if(charString.isEmpty()){
                    listItemFilter = listItems;
                }else {
                    List<ListItem> filterdList = new ArrayList<>();
                    for (ListItem listItem:listItems) {
                        if(listItem.getJob().toLowerCase().contains(charString.toLowerCase())){
                            filterdList.add(listItem);
                        }
                    }
                    listItemFilter = filterdList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listItemFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listItemFilter = (ArrayList<ListItem>) results.values;
                notifyDataSetChanged();
            }
        };

    }

    public class viewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView phone;
        private TextView job;
        private ImageView delete;
        private ImageView edit;

        public viewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            phone = (TextView)itemView.findViewById(R.id.phone);
            job = (TextView)itemView.findViewById(R.id.job);
            delete = itemView.findViewById(R.id.delete);
            edit = itemView.findViewById(R.id.edit);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   ListItem listItem = listItems.get(getAdapterPosition());
                   String userId = listItem.getUserId();
                   Log.e("id" , userId);
                   deleteUser(userId , getAdapterPosition());


                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ListItem listItem = listItems.get(getAdapterPosition());
                    String Id = listItem.getUserId();
                    editDate(Id);
                    notifyDataSetChanged();

                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ListItem item = listItems.get(getAdapterPosition());
                    Toast.makeText(context, "You Clicked " + item.getName(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void deleteUser(final String id , final int position) {

        alertDialog = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.delete_dialog, null);
        Button noButton = (Button) view.findViewById(R.id.noBtn);
        Button yesButton = (Button) view.findViewById(R.id.yesBtn);
        alertDialog.setView(view);
        dialog = alertDialog.create();
        dialog.show();
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase.getInstance().getReference()
                        .child("Users").child(id).removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(context, "User Deleted Successfully", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(context, "Failed To Delete User", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                notifyItemRemoved(position);

                dialog.dismiss();

            }
        });

    }
    public void editDate(final String id){

        alertDialog = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.edit_popup, null);
        Button submit = (Button) view.findViewById(R.id.submit);
        final EditText name = view.findViewById(R.id.edit_name);
        final EditText job = view.findViewById(R.id.edit_job);
        final EditText phone = view.findViewById(R.id.edit_phone);
        alertDialog.setView(view);
        dialog = alertDialog.create();
        dialog.show();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference()
                        .child("Users").child(id.toString()).child("UserName").setValue(name.getText().toString());
                FirebaseDatabase.getInstance().getReference()
                        .child("Users").child(id.toString()).child("Job").setValue(job.getText().toString());
                FirebaseDatabase.getInstance().getReference()
                        .child("Users").child(id.toString()).child("Phone").setValue(phone.getText().toString());

                       notifyDataSetChanged();
                dialog.dismiss();

            }
        });

    }
}
