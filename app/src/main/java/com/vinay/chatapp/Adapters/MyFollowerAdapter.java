package com.vinay.chatapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.vinay.chatapp.Models.Follower;
import com.vinay.chatapp.Models.Users;
import com.vinay.chatapp.R;
import com.vinay.chatapp.databinding.MyFollowerBinding;
import java.util.ArrayList;

public class MyFollowerAdapter extends RecyclerView.Adapter<MyFollowerAdapter.viewHolder>{
    public MyFollowerAdapter(ArrayList<Follower> list, Context context) {
        this.list = list;
        this.context = context;
    }

    ArrayList<Follower> list;
        Context context;
        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(context).inflate(R.layout.my_follower,parent,false);
            return new viewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull viewHolder holder, int position) {
            Follower follower =list.get(position);
            FirebaseDatabase.getInstance().getReference().child("Users").child(follower.getFollowedby()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Toast.makeText(context, follower.getFollowedby()+"", Toast.LENGTH_SHORT).show();
                    Users user=snapshot.getValue(Users.class);
                    Picasso.get().load(user.getProfilepic()).placeholder(R.drawable.placeuser).into(holder.binding.profile);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
        public class viewHolder extends RecyclerView.ViewHolder {
            MyFollowerBinding binding;
            public viewHolder(@NonNull View itemView) {
                super(itemView);
                binding= MyFollowerBinding.bind(itemView);

            }
        }
}
