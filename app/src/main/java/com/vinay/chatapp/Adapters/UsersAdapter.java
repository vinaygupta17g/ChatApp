package com.vinay.chatapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.vinay.chatapp.ChatDetailActivity;
import com.vinay.chatapp.Models.Users;
import com.vinay.chatapp.R;

import java.util.ArrayList;
public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.viewHolder>{
ArrayList<Users> list;
Context context;

    public UsersAdapter(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.chat_sample,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Users users=list.get(position);
        Picasso.get().load(users.getProfilepic()).placeholder(R.drawable.placeuser).into(holder.profile);
        if(users.getUid().equals(FirebaseAuth.getInstance().getUid()))
            holder.username.setText(users.getUsername()+" (You)");
        else
            holder.username.setText(users.getUsername());
        FirebaseDatabase.getInstance().getReference().child("Chats").child(FirebaseAuth.getInstance().getUid()+users.getUid()).orderByChild("timestamp").limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren())
                {
                    for(DataSnapshot dataSnapshot:snapshot.getChildren())
                    {
                        holder.lastmessage.setText(dataSnapshot.child("message").getValue(String.class).toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,ChatDetailActivity.class);
                intent.putExtra("profile",users.getProfilepic());
                intent.putExtra("userid",users.getUid());
                intent.putExtra("username",users.getUsername());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView profile;
        TextView username,lastmessage;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            profile=itemView.findViewById(R.id.profile);
            username=itemView.findViewById(R.id.username);
            lastmessage=itemView.findViewById(R.id.lastchat);
        }
    }
}
