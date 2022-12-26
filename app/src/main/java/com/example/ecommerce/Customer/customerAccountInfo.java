package com.example.ecommerce.Customer;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerce.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class customerAccountInfo extends AppCompatActivity {
    private TextView getUsername,getPhone,getEmail,getPassword;
    private String id;
    private Button redirect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_acc_info);

        getUsername = findViewById(R.id.get_username);
        getPhone = findViewById(R.id.get_mobile);
        getEmail = findViewById(R.id.get_email);
        getPassword = findViewById(R.id.get_password);
        redirect = findViewById(R.id.redirect_main);
        id = getIntent().getStringExtra("cId");


       DatabaseReference Rootref = FirebaseDatabase.getInstance().getReference("Customers").child("Users").child(id);



        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getUsername.setText(snapshot.child("name").getValue().toString());
                getPhone.setText(snapshot.child("phone").getValue().toString());
                getEmail.setText(snapshot.child("email").getValue().toString());
                getPassword.setText(snapshot.child("password").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(customerAccountInfo.this, customerMain.class);
                startActivity(i);
            }
        });


    }
}
