package com.example.sampleapp;

import static com.example.sampleapp.DeliveryActivity.SELECT_ADDRESS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MyAddressesActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView myAddressesRecyclerView;
    private Button deliverHereBtn;
    private static AddressesAdapter addressesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_addresses);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Addresses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myAddressesRecyclerView = findViewById(R.id.addresses_recyclerview);
        deliverHereBtn = findViewById(R.id.deliver_here_btn);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myAddressesRecyclerView.setLayoutManager(layoutManager);

        List<AddressesModel> addressesModelList = new ArrayList<>();
        addressesModelList.add(new AddressesModel("John Doe", "New York  Street, Houston", "190796", true));
        addressesModelList.add(new AddressesModel("Qadir Husain", "MOHAMMAD ZAI, UP", "242001", false));
        addressesModelList.add(new AddressesModel("Qadir Husain", "Lucknow, UP", "123409", false));
        addressesModelList.add(new AddressesModel("John Doe", "New York  Street, Houston", "190796", false));
        addressesModelList.add(new AddressesModel("John Doe", "New York  Street, Houston", "190796",  false));
        addressesModelList.add(new AddressesModel("John Doe", "New York  Street, Houston", "190796", false));
        addressesModelList.add(new AddressesModel("John Doe", "New York  Street, Houston", "190796", false));
        addressesModelList.add(new AddressesModel("John Doe", "New York  Street, Houston", "190796", false));

        int mode = getIntent().getIntExtra("MODE", -1);
        if (mode ==  SELECT_ADDRESS){
            deliverHereBtn.setVisibility(View.VISIBLE);
        } else {
            deliverHereBtn.setVisibility(View.GONE);
        }
        addressesAdapter = new AddressesAdapter(addressesModelList, mode);
        myAddressesRecyclerView.setAdapter(addressesAdapter);
        ((SimpleItemAnimator)myAddressesRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        addressesAdapter.notifyDataSetChanged();
    }

    public static void refreshItem(int deselect, int select){
        addressesAdapter.notifyItemChanged(deselect);
        addressesAdapter.notifyItemChanged(select);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}