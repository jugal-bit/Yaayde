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

public class TeacherRegister extends AppCompatActivity {
EditText name,cid,ctitle,email,password;
Button reg;
String tname,courseid,coursetitle,temail,tpassword;
FirebaseAuth firebaseAuth;
    private DatabaseReference mdatabase;
    private DatabaseReference mclassroom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_register);
        firebaseAuth=FirebaseAuth.getInstance();
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Teacher");
        mclassroom = FirebaseDatabase.getInstance().getReference().child("Classroom");
        name=findViewById(R.id.etname);
        cid=findViewById(R.id.etcid);
        ctitle=findViewById(R.id.etct);
        email=findViewById(R.id.ettemail);
        password=findViewById(R.id.etpass);
        reg=findViewById(R.id.regbtn);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              tname=name.getText().toString();
              courseid=cid.getText().toString();
              coursetitle=ctitle.getText().toString();
              temail=email.getText().toString();
              tpassword=password.getText().toString();
              firebaseAuth.createUserWithEmailAndPassword(temail,tpassword).addOnCompleteListener(TeacherRegister.this, new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                      FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                      if(!task.isSuccessful()){
                          Toast.makeText(TeacherRegister.this,"Register Unsuccessful",Toast.LENGTH_LONG).show();
                      }
                      else
                          if(task.isSuccessful())
                      {
                          String user_id = firebaseAuth.getCurrentUser().getUid();
                          DatabaseReference current_user_db = mdatabase.child(user_id);
                          current_user_db.child("uid").setValue(user_id);
                          current_user_db.child("name").setValue(tname);
                          current_user_db.child("course_no").setValue(courseid);
                          current_user_db.child("course_title").setValue(coursetitle);

                          DatabaseReference clssdb = mclassroom.child(user_id);
                          Classroom classroom = new Classroom();
                          classroom.setCourse_no(courseid);
                          classroom.setCourse_name(coursetitle);
                          classroom.setTeachername(tname);
                          classroom.setTeacherid(firebaseAuth.getCurrentUser().getUid());
                          classroom.setId(user_id);

                          clssdb.setValue(classroom);
                          Toast.makeText(TeacherRegister.this,"Register Successful!",Toast.LENGTH_LONG).show();
                          startActivity(new Intent(TeacherRegister.this, TeacherLogin.class));
                          finish();
                      }

                  }
              });
            }
        });
    }
}