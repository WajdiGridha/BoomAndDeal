package com.example.ecommerce.Shopkeeper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerce.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class shopkeeperAccountInfo extends AppCompatActivity {

    private TextView getUsername,getPhone,getShopName,getShopNo;
    private String id;
    private Button redirect;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopkeeper_acc_info);

        getUsername = findViewById(R.id.get_username);
        getPhone = findViewById(R.id.get_mobile);
        getShopName = findViewById(R.id.get_shopName);
        getShopNo = findViewById(R.id.get_shopID);
        redirect = findViewById(R.id.redirect_main);
        id = getIntent().getStringExtra("Id");

        DatabaseReference Rootref = FirebaseDatabase.getInstance().getReference("Shopkeepers").child("Users").child(id);



        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getUsername.setText(snapshot.child("Username").getValue().toString());
                getPhone.setText(snapshot.child("phone").getValue().toString());
                getShopName.setText(snapshot.child("Shopname").getValue().toString());
                getShopNo.setText(snapshot.child("shopID").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(shopkeeperAccountInfo.this, shopkeeperMain.class);
                startActivity(i);
            }
        });


    }

}
