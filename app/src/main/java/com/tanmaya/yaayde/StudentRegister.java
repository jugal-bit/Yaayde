package com.tanmaya.yaayde;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentRegister extends AppCompatActivity {
EditText name,sem,roll,email,password;
Button reg;
String sname,ssem,sroll,semail,spassword;
DatabaseReference mdatabase;
FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);
        name=findViewById(R.id.etname);
        sem=findViewById(R.id.etsem);
        roll=findViewById(R.id.etroll);
        email=findViewById(R.id.etsemail);
        password=findViewById(R.id.etspass);
        reg=findViewById(R.id.regbtn);
        firebaseAuth = FirebaseAuth.getInstance();
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Students");
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             sname=name.getText().toString();
             ssem=sem.getText().toString();
             sroll=roll.getText().toString();
             semail=email.getText().toString();
             spassword=password.getText().toString();
             firebaseAuth.createUserWithEmailAndPassword(semail,spassword).addOnCompleteListener(StudentRegister.this, new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                     FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                     if(!task.isSuccessful())
                     {
                         Toast.makeText(StudentRegister.this,"Register Unsuccessful",Toast.LENGTH_LONG).show();
                     }
                     else if(task.isSuccessful())
                     {
                         String user_id = firebaseAuth.getCurrentUser().getUid();
                         Student st = new Student();
                         st.setId(user_id);
                         st.setName(sname);
                         st.setRoll(sroll);
                         st.setSem(ssem);
                         DatabaseReference current_user_db = mdatabase.child(user_id);
                         current_user_db.setValue(st);
                         Toast.makeText(StudentRegister.this,"Register Successful!",Toast.LENGTH_LONG).show();
                         startActivity(new Intent(StudentRegister.this, StudentLogin.class));
                         finish();
                     }

                 }
             });

            }
        });
    }
}