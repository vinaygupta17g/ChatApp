package com.vinay.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.vinay.chatapp.Adapters.MessageAdapter;
import com.vinay.chatapp.Models.MessageModel;
import com.vinay.chatapp.databinding.ActivityChatDetailBinding;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ChatDetailActivity extends AppCompatActivity {
    ActivityChatDetailBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    Intent intent;
    ArrayList<MessageModel> message=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatDetailBinding.inflate(getLayoutInflater());
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        getSupportActionBar().hide();
        setContentView(binding.getRoot());
        //getting data from mainactivity
        intent=getIntent();
        final String receiverid=intent.getStringExtra("userid");
        final String senderid=auth.getUid();
        final String senderroom=senderid+receiverid;
        final String receiverroom=receiverid+senderid;
        String username=intent.getStringExtra("username");
        String profilepic=intent.getStringExtra("profile");
        Picasso.get().load(profilepic).placeholder(R.drawable.placeuser).into(binding.profile);
        if(senderid.equals(receiverid))
            binding.username.setText(username+" (You)");
        else
            binding.username.setText(username);
        //back button
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(ChatDetailActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        MessageAdapter adapter=new MessageAdapter(message,this);
        binding.msgrecyclerView.setAdapter(adapter);

        LinearLayoutManager manager=new LinearLayoutManager(this);
        binding.msgrecyclerView.setLayoutManager(manager);

        database.getReference().child("Chats").child(senderroom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                message.clear();
                for(DataSnapshot datasnapshot:snapshot.getChildren())
                {
                    MessageModel model=datasnapshot.getValue(MessageModel.class);
                    model.setMsgid(datasnapshot.getKey());
                    message.add(model);
                    binding.msgrecyclerView.scrollToPosition(message.size()-1);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //message send to database
        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.message.getText().toString().isEmpty())
                {
                    binding.message.setError("Enter some message");
                }
                else
                {
                    String message=binding.message.getText().toString();
                    binding.message.setText("");
                    DateTimeFormatter clock=DateTimeFormatter.ofPattern("hh:mm:ss");
                    LocalDateTime now=LocalDateTime.now();
                    MessageModel messageModel=new MessageModel(senderid,message, clock.format(now));
                    database.getReference().child("Chats").child(senderroom).push().setValue(messageModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            if (senderid.equals(receiverid))
                            {

                            }
                            else
                            {
                                database.getReference().child("Chats").child(receiverroom).push().setValue(messageModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

    }
}