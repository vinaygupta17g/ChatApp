package com.vinay.chatapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vinay.chatapp.Adapters.FollowAdapter;
import com.vinay.chatapp.Models.Users;
import com.vinay.chatapp.R;
import com.vinay.chatapp.databinding.FragmentFollowBinding;

import java.util.ArrayList;

public class FollowFragment extends Fragment {
    FragmentFollowBinding binding;
    ArrayList<Users> list=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentFollowBinding.inflate(inflater,container,false);
        FollowAdapter adapter=new FollowAdapter(list,getContext());
        binding.follorecyclerview.setAdapter(adapter);

        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        binding.follorecyclerview.setLayoutManager(manager);
        FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
               for (DataSnapshot snapshot1:snapshot.getChildren())
               {
                   Users users=snapshot1.getValue(Users.class);
                   list.add(users);
               }
               adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return binding.getRoot();
    }
}