package com.example.ecommerce.Shopkeeper;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.ecommerce.MainActivity;
import com.example.ecommerce.Others.about;
import com.example.ecommerce.R;

public class shopkeeperMain extends AppCompatActivity {

    private Toolbar toolbar;
    private CardView cardView1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopkeeper_main);

        toolbar = findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);

        cardView1 = findViewById(R.id.card1);
        CardView cardView2 = findViewById(R.id.card2);

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = getIntent().getStringExtra("shopName");
                Intent next =  new Intent(getApplicationContext(), addProduct.class);
                next.putExtra("sName",name);
                next.putExtra("sID",getIntent().getStringExtra("Id"));
                startActivity(next);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_shopkeeper, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.account_info){
            Intent newIntent = new Intent(getApplicationContext(), shopkeeperAccountInfo.class);
            newIntent.putExtra("Id",getIntent().getStringExtra("Id"));
            startActivity(newIntent);
            Toast.makeText(getApplicationContext(),"Account info",Toast.LENGTH_SHORT).show();
            return true;
        }

        else if(id == R.id.help){
            startActivity(new Intent(getApplicationContext(), about.class));
            Toast.makeText(getApplicationContext(),"Contact us",Toast.LENGTH_SHORT).show();
            return true;
        }
        else{
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(getApplicationContext(),"Succesfully Logout",Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
