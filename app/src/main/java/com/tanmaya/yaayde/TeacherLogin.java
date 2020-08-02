package com.tanmaya.yaayde;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeacherLogin extends AppCompatActivity {
    Button reg,login;
    EditText email,password;
    String semail,spassword,uid,key;
    FirebaseAuth firebaseAuth;
    Integer flag=0;
    private DatabaseReference mdatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        firebaseAuth=FirebaseAuth.getInstance();
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Teacher");
        FirebaseUser user=firebaseAuth.getCurrentUser();
        if(user!=null)
        {   uid=user.getUid();
            mdatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot childDataSnapshot:snapshot.getChildren())
                    {   key=childDataSnapshot.getKey();
                        if(uid.equals(key))
                        {
                            Intent intent =new Intent(TeacherLogin.this,TeacherActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        reg=findViewById(R.id.register);
        login=findViewById(R.id.login);
        email=findViewById(R.id.susername);
        password=findViewById(R.id.spassword);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TeacherLogin.this,TeacherRegister.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                semail=email.getText().toString();
                spassword=password.getText().toString();
                firebaseAuth.signInWithEmailAndPassword(semail,spassword).addOnCompleteListener(TeacherLogin.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    { if(!task.isSuccessful())
                    {
                        Toast.makeText(TeacherLogin.this,"Login Unsuccessful",Toast.LENGTH_LONG).show();
                    }
                    else if(task.isSuccessful())
                    {   Toast.makeText(TeacherLogin.this,"Login Successful",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(TeacherLogin.this,TeacherActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    }
                });

            }
        });

    }
}