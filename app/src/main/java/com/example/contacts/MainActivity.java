package com.example.contacts;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements ContactsAdapter.ItemEventListener {
    private ContactsAdapter adapter;
    private int editingItemPosition = -1;
    private EditText fullNameET;
    private ImageView addNewContactBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapter = new ContactsAdapter(this);
        recyclerView.setAdapter(adapter);

        fullNameET = findViewById(R.id.et_main_contactFullName);
        addNewContactBtn = findViewById(R.id.iv_main_addNewContact);
        addNewContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fullNameET.length() > 0) {

                    if (editingItemPosition > -1) {
                        adapter.updateContact(fullNameET.getText().toString(), editingItemPosition );
                        editingItemPosition = -1;
                        addNewContactBtn.setImageResource(R.drawable.ic_add_white_24);
                        ;
                    } else {
                        Toast.makeText(view.getContext(), fullNameET.getText().toString(), Toast.LENGTH_LONG).show();

                        adapter.addNewContact(fullNameET.getText().toString());
                        //         recyclerView.scrollToPosition(0); // سفا اسکرول میشه
                        //       recyclerView.smoothScrollToPosition(0);// به نرمی اسمکرول میشه
                        recyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.smoothScrollToPosition(0);
                            }
                        });
                    }
                    fullNameET.setText("");
                    fullNameET.requestFocus();


                }
            }
        });
    }


    @Override
    public void onItemClick(String fullName, int position) {
        editingItemPosition = position;
        fullNameET.setText((fullName));
        addNewContactBtn.setImageResource(R.drawable.ic_done_white_24);

    }
}