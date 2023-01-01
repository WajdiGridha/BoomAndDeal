package com.example.ecommerce.Customer;


import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;
import com.example.ecommerce.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

public class productCard extends AppCompatActivity {
    private String imUrl,cat;
    private String[] id;
    private ImageView prodImg;
    private TextView prodPrice,prodName,shopName,prodId,shopId,quantity,phone;
    private DatabaseReference Rootref;
    private Button buy,cart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_card);

        cat = getIntent().getStringExtra("catg");
        imUrl = getIntent().getStringExtra("pimg");
        HashMap<String,String> hashMap = (HashMap<String, String>)getIntent().getSerializableExtra("Id");


        assert hashMap != null;
        Rootref = FirebaseDatabase.getInstance().getReference(cat).child(Objects.requireNonNull(hashMap.get("shopId")));
        prodImg = (ImageView) findViewById(R.id.prod_card_img);

        prodName = (TextView) findViewById(R.id.prod_card_name);
        prodPrice = (TextView) findViewById(R.id.prod_card_price);

        shopName = (TextView) findViewById(R.id.prod_shop_name);
        prodId = (TextView) findViewById(R.id.prod_id);
        shopId = (TextView) findViewById(R.id.shop_id);
        phone = (TextView) findViewById(R.id.phoneN);

        buy = (Button) findViewById(R.id.prod_card_buy);
        cart = (Button) findViewById(R.id.prod_card_cart);

        SearchFirebase();

        Glide.with(getApplicationContext()).load(imUrl).into(prodImg);


        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String number = phone.getText().toString().trim();
                Intent callIntent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+Uri.encode(number)));
                startActivity(callIntent);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(getApplicationContext(), customerMain.class);
                    startActivity(intent);

            }
        });



    }

    private void SearchFirebase() {

        Rootref.orderByChild("IMG").equalTo(imUrl).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot product : snapshot.getChildren()){

                    prodName.setText(Objects.requireNonNull(product.child("Product Name").getValue()).toString());
                    prodPrice.setText(Objects.requireNonNull(product.child("Product Price").getValue()).toString());

                    shopName.setText(Objects.requireNonNull(product.child("Shop Name").getValue()).toString());
                    prodId.setText(Objects.requireNonNull(product.child("Product Id").getValue()).toString());
                    shopId.setText(Objects.requireNonNull(product.child("Product Discount").getValue()).toString());
                    phone.setText(Objects.requireNonNull(product.child("Phone Number").getValue()).toString());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}