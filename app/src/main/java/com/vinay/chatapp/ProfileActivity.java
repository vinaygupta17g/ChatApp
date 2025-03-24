package com.vinay.chatapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.vinay.chatapp.Adapters.MyFollowerAdapter;
import com.vinay.chatapp.Models.Follower;
import com.vinay.chatapp.Models.Users;
import com.vinay.chatapp.databinding.ActivityProfileBinding;
import java.util.ArrayList;
public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;
    ArrayList<Follower> list=new ArrayList<>();
    FirebaseStorage storage;
    FirebaseDatabase database;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        storage=FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        binding=ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        MyFollowerAdapter adapter=new MyFollowerAdapter(list,this);
        binding.myfollower.setAdapter(adapter);

        LinearLayoutManager manager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        binding.myfollower.setLayoutManager(manager);

        FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getUid()).child("follower").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren())
                {
                    Follower follower=snapshot1.getValue(Follower.class);
                    list.add(follower);
                }adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.logout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent=new Intent(ProfileActivity.this,SignInActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );
        binding.back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(ProfileActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );
        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren())
                {
                    Users user=snapshot.getValue(Users.class);
                    binding.username.setText(user.getUsername());
                    Picasso.get().load(user.getProfilepic()).placeholder(R.drawable.placeuser).into(binding.profile);
                    Picasso.get().load(user.getCoverphoto()).placeholder(R.drawable.cover).into(binding.cover);
                    binding.mobile.setText(user.getMobile());
                    binding.email.setText(user.getEmail());
                    binding.follower.setText(user.getFollowercount()+"");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent();
                intent.setAction(intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });
        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2)
        {
            if(data.getData()!=null)
            {
                Uri sfile=data.getData();
                binding.profile.setImageURI(sfile);

                final StorageReference reference=storage.getReference().child("profile_pic").child(FirebaseAuth.getInstance().getUid());
                reference.putFile(sfile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("profilepic").setValue(uri.toString());
                            }
                        });
                    }
                });
            }
        }
        else
        {
            if(data.getData()!=null)
            {
                Uri sfile=data.getData();
                binding.cover.setImageURI(sfile);
                StorageReference reference=storage.getReference().child("coverphoto").child(FirebaseAuth.getInstance().getUid());
                reference.putFile(sfile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("coverphoto").setValue(uri.toString());
                            }
                        });
                    }
                });
            }
        }
    }
}