package com.example.ecommerce.Customer;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ecommerce.Model.usersCustomers;
import com.example.ecommerce.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;


public class customerFragLogin extends Fragment {


    private EditText inputPhoneCustomer, inputPasswordCustomer;
    private String parentDbName = "Users";



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public customerFragLogin() {

    }


    public static customerFragLogin newInstance(String param1, String param2) {
        customerFragLogin fragment = new customerFragLogin();
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
        return inflater.inflate(R.layout.frag_customer_login, container, false);
    }


    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);


        TextView forgotpass;
        forgotpass = view.findViewById(R.id.forgot_pass_customer);

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), customerVerify.class));
            }
        });


        Button login = (Button) view.findViewById(R.id.login_customer);
        inputPhoneCustomer = (EditText) view.findViewById(R.id.phone_customer);
        inputPasswordCustomer = (EditText) view.findViewById(R.id.password_customer);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }

            private void LoginUser() {

                String phone = inputPhoneCustomer.getText().toString();
                String password = inputPasswordCustomer.getText().toString();

                if (TextUtils.isEmpty(phone))
                {
                    Toast.makeText(getActivity(), "Please write your phone number.", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(password))
                {
                    Toast.makeText(getActivity(), "Please write your password.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    AllowAccessToAccount(phone, password);
                }

            }

            private void AllowAccessToAccount(final String phone, final String password) {

                final DatabaseReference RootRef;
                RootRef = FirebaseDatabase.getInstance().getReference("Customers");

                RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                        if (datasnapshot.child(parentDbName).child(phone).exists())
                        {
                            usersCustomers usersData = datasnapshot.child(parentDbName).child(phone).getValue(usersCustomers.class);

                            assert usersData != null;
                            if (usersData.getPhone().equals(phone))
                            {
                                if (usersData.getPassword().equals(password))
                                {
                                        Toast.makeText(getActivity(), "Logged in Successfully.", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getActivity(), customerMain.class);
                                    intent.putExtra("cId",phone);
                                        startActivity(intent);
                                        onDestroy();
                                }
                                else
                                {
                                    Toast.makeText(getActivity(), "Password is incorrect.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Account with " + phone + " does not exist.", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });


    }



}
