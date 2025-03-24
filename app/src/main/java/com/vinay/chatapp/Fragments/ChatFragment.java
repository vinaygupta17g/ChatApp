package com.vinay.chatapp.Fragments;

import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vinay.chatapp.Adapters.UsersAdapter;
import com.vinay.chatapp.Models.Users;
import com.vinay.chatapp.databinding.FragmentChatBinding;
import java.util.ArrayList;
public class ChatFragment extends Fragment {
    FragmentChatBinding binding;
    ArrayList<Users> list=new ArrayList<>();
    FirebaseDatabase database;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentChatBinding.inflate(inflater,container,false);
        database=FirebaseDatabase.getInstance();
        UsersAdapter adapter=new UsersAdapter(list,getContext());
        binding.userrecyclerView.setAdapter(adapter);
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        binding.userrecyclerView.setLayoutManager(manager);
        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot datasnapshot: snapshot.getChildren())
                {
                    Users users=datasnapshot.getValue(Users.class);
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