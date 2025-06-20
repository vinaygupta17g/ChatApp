package com.vinay.chatapp.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.vinay.chatapp.Models.Follower;
import com.vinay.chatapp.Models.Users;
import com.vinay.chatapp.R;
import com.vinay.chatapp.databinding.FollowsAmpleBinding;
import java.util.ArrayList;
import java.util.Date;
public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.viewHolder> {
    ArrayList<Users> list;
    Context context;
    public FollowAdapter(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.follows_ample,parent,false);
        return new viewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Users user=list.get(position);
        Picasso.get().load(user.getProfilepic()).placeholder(R.drawable.placeuser).into(holder.binding.profile);
        holder.binding.username.setText(user.getUsername());
        FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("follower").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    holder.binding.follow.setText("following");
                    holder.binding.follow.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.following_bg));
                    holder.binding.follow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            holder.binding.follow.setText("follow");
                            holder.binding.follow.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.button_bg));
                            FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("follower").child(FirebaseAuth.getInstance().getUid()).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("followercount").setValue(user.getFollowercount()-1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(context, "You unfollowed followed "+user.getUsername(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
                else
                {
                    holder.binding.follow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Follower follower=new Follower();
                            follower.setFollowedby(FirebaseAuth.getInstance().getUid());
                            follower.setFollowedat(new Date().getTime());
                            FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("follower").child(FirebaseAuth.getInstance().getUid()).setValue(follower).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("followercount").setValue(user.getFollowercount()+1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(context, "You followed "+user.getUsername(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
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
        FollowsAmpleBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=FollowsAmpleBinding.bind(itemView);
        }
    }
}
