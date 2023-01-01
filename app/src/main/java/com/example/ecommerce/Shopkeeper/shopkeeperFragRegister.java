package com.example.ecommerce.Shopkeeper;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class shopkeeperFragRegister extends Fragment{

    private EditText shopkeeperName, shopkeeperPhone, shopkeeperShopName, shopkeeperShopId, shopkeeperPassword;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public shopkeeperFragRegister() {

    }


    public static shopkeeperFragRegister newInstance(String param1, String param2) {
        shopkeeperFragRegister fragment = new shopkeeperFragRegister();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.frag_shopkeeper_register, container, false);
    }


    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        Button createAccount = view.findViewById(R.id.register_shopkeeper);

        shopkeeperName = view.findViewById(R.id.set_name_shopkeeper);
        shopkeeperPhone = view.findViewById(R.id.set_phone_shopkeeper);
        shopkeeperShopName = view.findViewById(R.id.set_shop_name);
        shopkeeperShopId = view.findViewById(R.id.set_shopId_shopkeeper);
        shopkeeperPassword = view.findViewById(R.id.set_password_shopkeeper);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });

    }

    private void CreateAccount() {
        String name = shopkeeperName.getText().toString();
        String phone = shopkeeperPhone.getText().toString();
        String shopNo = shopkeeperShopId.getText().toString();
        String pass = shopkeeperPassword.getText().toString();
        String shopName = shopkeeperShopName.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(getActivity(), "Please write a name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phone)){
            Toast.makeText(getActivity(), "Please enter a phone number", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(shopName)){
            Toast.makeText(getActivity(), "Please give a shop name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(shopNo)){
            Toast.makeText(getActivity(), "Please provide shop's registration number", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(pass)){
            Toast.makeText(getActivity(), "Please give a password", Toast.LENGTH_SHORT).show();
        }


        else
        {
            Toast.makeText(getActivity(), "Please hang in there for a while.", Toast.LENGTH_LONG).show();
            ValidatephoneNumber(name, shopNo, phone, pass, shopName);
        }

    }

    private void ValidatephoneNumber(final String name, final String shopNo, final String phone, final String pass, final String shopName) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference("Shopkeepers");

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (!(dataSnapshot.child("Users").child(shopNo).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("shopID", shopNo);
                    userdataMap.put("Shopname", shopName);
                    userdataMap.put("phone", phone);
                    userdataMap.put("password", pass);
                    userdataMap.put("Username", name);

                    RootRef.child("Users").child(shopNo).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Intent intent = new Intent(getActivity(),shopkeeperMain.class);
                                        intent.putExtra("shopName",shopName);
                                        Intent valAtLogin = new Intent(getActivity(),shopkeeperFragLogin.class);
                                        valAtLogin.putExtra("shopName",shopName);
                                        intent.putExtra("Id",shopNo);
                                        Toast.makeText(getActivity(), "Your account has been created successfully !", Toast.LENGTH_SHORT).show();
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        Toast.makeText(getActivity(), "Something went wrong, please try again later.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(getActivity(), "Shop number "+ shopNo + " already exists.", Toast.LENGTH_LONG).show();
                    Toast.makeText(getActivity(), "Please enter valid shop number.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



}
