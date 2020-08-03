package com.tanmaya.yaayde;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class ApproveStudent extends AppCompatActivity {
    ListView listView;
    DatabaseReference databaseReference;
    private TextView tname;
    private TextView name;
    private TextView tterm;
    private TextView term;
    private TextView troll;
    private TextView roll;
    private DatabaseReference mdatabase;

    FirebaseAuth firebaseAuth;
    String id;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_student);
        listView=(ListView)findViewById(R.id.list);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Request");
        firebaseAuth=FirebaseAuth.getInstance();


        tname = (TextView) findViewById(R.id.tname);
        name = (TextView) findViewById(R.id.name);
        tterm = (TextView) findViewById(R.id.tterm);
        term = (TextView) findViewById(R.id.term);
        troll = (TextView) findViewById(R.id.troll);
        roll = (TextView) findViewById(R.id.roll);
        Bundle bundle = getIntent().getExtras();
        id=bundle.getString("id");
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Students");
        button=(Button)findViewById(R.id.apply);

        DatabaseReference current_user_db = mdatabase.child(id);

        current_user_db.child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value1 =(String) dataSnapshot.getValue();
                name.setText(value1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        current_user_db.child("term").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value4 =(String) dataSnapshot.getValue();
                term.setText(value4);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        current_user_db.child("roll").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value5 =(String) dataSnapshot.getValue();
                roll.setText(value5);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                        String clsrm=String.valueOf(newPost.get("classroomid"));
                        String st=String.valueOf(newPost.get("studentid"));

                        if(clsrm.equalsIgnoreCase(firebaseAuth.getCurrentUser().getUid()) && st.equalsIgnoreCase(id))
                        {
                            databaseReference.child(dataSnapshot.getKey()).child("accepted").setValue("1");
                        }



                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Intent intent=new Intent(ApproveStudent.this,AddStudent.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

    }
}