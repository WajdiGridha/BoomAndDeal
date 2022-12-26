package com.example.ecommerce.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.Adapter.CategoryAdapter;
import com.example.ecommerce.MainActivity;
import com.example.ecommerce.Model.modelCategory;
import com.example.ecommerce.R;
import com.example.ecommerce.Shopkeeper.shopkeeperAccountInfo;

import java.util.ArrayList;
import java.util.List;

public class customerMain extends AppCompatActivity {

    private Toolbar toolbar;
    private CategoryAdapter adapter;
    private List<modelCategory> exampleList;
    private CategoryAdapter.RecyclerViewClickListener listener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_main);

        toolbar = findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);

        fillExampleList();
        setUpRecyclerView();
    }

    private void fillExampleList() {
        exampleList = new ArrayList<>();
        exampleList.add(new modelCategory(R.drawable.voiture, "Voitures"));
        exampleList.add(new modelCategory(R.drawable.location, "Immobilier"));
        exampleList.add(new modelCategory(R.drawable.telephone, "Telephones"));
        exampleList.add(new modelCategory(R.drawable.rechange, "Vehicules & Pieces"));
        exampleList.add(new modelCategory(R.drawable.informatique, "Informatique & Multimedia"));
        exampleList.add(new modelCategory(R.drawable.beaute, "Mode & Beaute"));
        exampleList.add(new modelCategory(R.drawable.jardin, "Maison & Jardin"));
        exampleList.add(new modelCategory(R.drawable.construction, "Matériaux & Equipement"));
    }

    private void setUpRecyclerView() {
        setOnClickListener();
        RecyclerView recyclerView = findViewById(R.id.category_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new CategoryAdapter(exampleList,listener);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListener() {
        listener = new CategoryAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent myIntent = new Intent(getApplicationContext(), categorySpecific.class);
                myIntent.putExtra("cName",exampleList.get(position).getText1());
                startActivity(myIntent);
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_customer, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.account_info){
            Intent newIntent = new Intent(getApplicationContext(), customerAccountInfo.class);
            newIntent.putExtra("cId",getIntent().getStringExtra("cId"));
            startActivity(newIntent);
            return true;
        }
        else if(id == R.id.placed_orders){
            Toast.makeText(getApplicationContext(),"Orders",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(id == R.id.help){
            Toast.makeText(getApplicationContext(),"Contact us",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(id == R.id.search){
            return true;
        }else if(id == R.id.cart){
            Toast.makeText(getApplicationContext(),"Cart",Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(getApplicationContext(),"Succesfully Logout",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
