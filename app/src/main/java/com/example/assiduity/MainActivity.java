package com.example.assiduity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText use;
    EditText psswd;
    Button enter;
    ProgressBar progressBar;
    FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        use=findViewById(R.id.user);
        psswd=findViewById(R.id.password);
        enter=findViewById(R.id.sign);
        progressBar=findViewById(R.id.progressBar);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username=use.getText().toString().trim();
                String password=psswd.getText().toString().trim();

                if(TextUtils.isEmpty(username)){
                    use.setError("User_name is required!");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    psswd.setError("Password is required!");
                    return;
                }
                if(password.length()<8){
                    psswd.setError("Password is too short, we need a password of at least 8 characters!");
                    return;
                }
                fauth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Login succesful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Staff.class));
                        }else {
                            Toast.makeText(MainActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                progressBar.setVisibility(View.VISIBLE);





            }
        });





    }

}
