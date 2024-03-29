package com.example.assiduity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Employee extends AppCompatActivity {
    EditText name,surname,address,mail,key;
    Button add;
    DatabaseReference employeeDb;
    FirebaseAuth fauthe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        name=findViewById(R.id.name);
        surname=findViewById(R.id.surname);
        address=findViewById(R.id.address);
        mail=findViewById(R.id.mail);
        key=findViewById(R.id.key);
        add=findViewById(R.id.create);

        employeeDb= FirebaseDatabase.getInstance().getReference().child("employee");
        fauthe = FirebaseAuth.getInstance();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEmployeeData();
            }
        });


    }
    private void addEmployeeData(){
        String nom=name.getText().toString();
        String prenom=surname.getText().toString();
        String adresse=address.getText().toString();
        String email=mail.getText().toString();
        String mdp=key.getText().toString();

        if(TextUtils.isEmpty(nom)){
            name.setError("name is required!");
            return;
        }
        if(TextUtils.isEmpty(prenom)){
            surname.setError("surname is required!");
            return;
        }
        if(TextUtils.isEmpty(adresse)){
            address.setError("address is required!");
            return;
        }
        if(TextUtils.isEmpty(email)){
            mail.setError("email is required!");
            return;
        }
        if(TextUtils.isEmpty(mdp)){
            key.setError("Password is required!");
            return;
        }
        if(mdp.length()<8){
            key.setError("Password is too short, we need a password of at least 8 characters!");
            return;
        }

        if(fauthe.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),Staff.class));
            finish();
        }

        fauthe.createUserWithEmailAndPassword(email,mdp).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Employe employe= new Employe(nom,prenom,email,adresse,mdp,FirebaseAuth.getInstance().getUid());
                    employeeDb.child(FirebaseAuth.getInstance().getUid()).setValue(employe);

                    Toast.makeText(Employee.this, "Employee add!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),Staff.class));
                    finish();

                }else{
                    Toast.makeText(Employee.this, "We have an error!!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}