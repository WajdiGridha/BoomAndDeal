package com.example.ecommerce.Customer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
    //private String id;
    private Button buy,cart,plus,minus;
    boolean buyState = false,cartState = false;

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
        //prodName.setText(name);
        //prodPrice.setText(price);

        //plus = (Button) findViewById(R.id.prod_plus);
        //minus = (Button) findViewById(R.id.prod_minus);
        //quantity = (TextView) findViewById(R.id.prod_card_quantity);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),phone.getText().toString(),Toast.LENGTH_SHORT).show();
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

        /*plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qt = Integer.parseInt(quantity.getText().toString());
                if(qt == 9){
                    Toast.makeText(getApplicationContext(),"Sorry! Operation can't be performed",Toast.LENGTH_SHORT).show();
                }
                else{
                    qt = qt+1;
                    quantity.setText(String.valueOf(qt));
                }
            }
        });*/

        /*minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qt = Integer.parseInt(quantity.getText().toString());
                if(qt == 1){
                    Toast.makeText(getApplicationContext(),"Sorry! Operation can't be performed",Toast.LENGTH_SHORT).show();
                }
                else{
                    qt = qt-1;
                    quantity.setText(String.valueOf(qt));
                }
            }
        });*/

    }

    private void SearchFirebase() {

        Rootref.orderByChild("IMG").equalTo(imUrl).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot product : snapshot.getChildren()){
                    //price = product.child("Product Name").getValue().toString();
                    //name = product.child("Product Price").getValue().toString();
                    prodName.setText(Objects.requireNonNull(product.child("Product Name").getValue()).toString());
                    prodPrice.setText(Objects.requireNonNull(product.child("Product Price").getValue()).toString());

                    shopName.setText(Objects.requireNonNull(product.child("Shop Name").getValue()).toString());
                    prodId.setText(Objects.requireNonNull(product.child("Product Id").getValue()).toString());
                    shopId.setText(Objects.requireNonNull(product.child("Product Discount").getValue()).toString());
                    phone.setText(Objects.requireNonNull(product.child("Phone Number").getValue()).toString());
                    //id = product.getKey();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}