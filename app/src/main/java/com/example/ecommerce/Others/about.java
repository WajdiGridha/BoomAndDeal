package com.example.ecommerce.Others;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.ecommerce.Customer.customerMain;
import com.example.ecommerce.R;


public class about extends AppCompatActivity {

    private Button redirect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);


        redirect = findViewById(R.id.redirect_main);


        redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(about.this, customerMain.class);
                startActivity(i);
            }
        });


    }

}
