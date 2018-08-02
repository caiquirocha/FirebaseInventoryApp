package com.myapplicationdev.android.demodialog.firebaseinventoryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InventoryDetails extends AppCompatActivity {

    private static final String TAG = "InventoryDetails";

    private EditText etName, etCost;
    private Button btnUpdate, btnDelete;

    private Inventory inventory;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference inventoryListRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_details);
        etName = (EditText)findViewById(R.id.editTextName);
        etCost = (EditText)findViewById(R.id.editTextCost);
        btnUpdate = (Button)findViewById(R.id.buttonUpdate);
        btnDelete = (Button)findViewById(R.id.buttonDelete);

        firebaseDatabase = FirebaseDatabase.getInstance();
        inventoryListRef = firebaseDatabase.getReference("/inventoryList");

        Intent intent = getIntent();
        inventory = (Inventory) intent.getSerializableExtra("Inventory");

        etName.setText(inventory.getBrand());
        etCost.setText(String.valueOf(inventory.getUnit_price()));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String brand = etName.getText().toString();
                double cost = Double.parseDouble(etCost.getText().toString());
                inventoryListRef.child(inventory.getId()).child("brand").setValue(brand);
                inventoryListRef.child(inventory.getId()).child("unit_price").setValue(cost);
                Toast.makeText(getApplicationContext(), "Student record updated successfully", Toast.LENGTH_SHORT).show();

                setResult(RESULT_OK);
                finish();

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Delete Student record based on student id
                inventoryListRef.child(inventory.getId()).removeValue();

                Toast.makeText(getApplicationContext(), " Student record deleted successfully", Toast.LENGTH_SHORT).show();

                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
