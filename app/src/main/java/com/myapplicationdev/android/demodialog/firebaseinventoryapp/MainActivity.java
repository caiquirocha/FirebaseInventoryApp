package com.myapplicationdev.android.demodialog.firebaseinventoryapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvInventory;
    private ArrayList<Inventory> alInventory;
    private ArrayAdapter<Inventory> aaInventory;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference studentListRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvInventory = (ListView)findViewById(R.id.listViewInventory);
        alInventory = new ArrayList<Inventory>();
        aaInventory = new ArrayAdapter<Inventory>(this, android.R.layout.simple_list_item_1, alInventory);
        lvInventory.setAdapter(aaInventory);

        firebaseDatabase = FirebaseDatabase.getInstance();
        studentListRef = firebaseDatabase.getReference("inventoryList");

        studentListRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.i("MainActivity", "onChildAdded()");
                Inventory inventory = dataSnapshot.getValue(Inventory.class);
                if (inventory != null) {
                    inventory.setId(dataSnapshot.getKey());

                    alInventory.add(inventory);
                    aaInventory.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
                Log.i("MainActivity", "onChildChanged()");
                String selectedId = dataSnapshot.getKey();
                Inventory inventory = dataSnapshot.getValue(Inventory.class);
                if (inventory != null) {
                    for (int i = 0; i < alInventory.size(); i++) {
                        if (alInventory.get(i).getId().equals(selectedId)) {
                            inventory.setId(selectedId);
                            alInventory.set(i, inventory);
                            break;
                        }
                    }
                    aaInventory.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.i("MainActivity", "onChildRemoved()");
                String selectedId = dataSnapshot.getKey();
                for(int i= 0; i < alInventory.size(); i++) {
                    if (alInventory.get(i).getId().equals(selectedId)) {
                        alInventory.remove(i);
                        break;
                    }
                }
                aaInventory.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.i("MainActivity", "onChildMoved()");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MainActivity", "Database error occurred", databaseError.toException());
            }
        });

        lvInventory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Inventory inventory = alInventory.get(i);  // Get the selected Student
                Intent intent = new Intent(MainActivity.this, InventoryDetails.class);
                intent.putExtra("Inventory", inventory);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.addInventory) {

            Intent intent = new Intent(getApplicationContext(), AddInventory.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
