package com.example.quikr_dummy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class list extends AppCompatActivity {

    Map<String,Object> l;
    String id;
    Integer flag=0;
    List<List<String> > mp=new ArrayList<List<String>>() {};
    Map <String, Pair<String,String>> myMap = new HashMap<String, Pair<String, String>>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    List<Map<String,Object>> m=new ArrayList<Map<String,Object>>() {};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        fetch();
    }

    public void fetch()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("items")
//                .whereEqualTo("state", "CA")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                           // Log.w(TAG, "Listen failed.", e);
                            return;
                        }
                        m.clear();
//                        List<String> cities = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.getData() != null) {
//                                doc.get
                                m.add(doc.getData());
//                                Log.e("data",doc.getData().size());
//                                cities.add(doc.getString("name"));
                            }
                        }
//                        Log.e("TAG", "Current cites in CA: " +m.size());
                        mAdapter = new MyAdapter(list.this,m,id);
                        recyclerView.setAdapter(mAdapter);
                        recyclerView.setLayoutManager(layoutManager);
                    }
                });
//
            }


}
