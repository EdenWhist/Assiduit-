package com.example.assiduity;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;

public class Staff extends AppCompatActivity {
    Button scan,addU,historic;
    ImageView QR;
    Bitmap bitmap;
    //DatabaseReference employeeDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);
        scan=findViewById(R.id.scan);
        addU=findViewById(R.id.add);
        historic=findViewById(R.id.history);
        QR=findViewById(R.id.idIVQrcode);

        Query employeeDb = FirebaseDatabase.getInstance().getReference("employee").orderByChild("id").equalTo(FirebaseAuth.getInstance().getUid());

        employeeDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String uid = snapshot.getKey();

                    if (!uid.equals(FirebaseAuth.getInstance().getUid())){
                        String name = snapshot.child(FirebaseAuth.getInstance().getUid()).child("nom").getValue(String.class);
                        /*String surname = snapshot.child(FirebaseAuth.getInstance().getUid()).child("prenom").getValue(String.class);
                        String adress = snapshot.child(FirebaseAuth.getInstance().getUid()).child("adresse").getValue(String.class);
                        String mail = snapshot.child(FirebaseAuth.getInstance().getUid()).child("email").getValue(String.class);*/

                        MultiFormatWriter writer = new MultiFormatWriter();
                        try {
                            BitMatrix matrix = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                matrix = writer.encode("Nom: "+name, BarcodeFormat.QR_CODE,450,450);
                            }

                            BarcodeEncoder encoder = new BarcodeEncoder();
                            bitmap = encoder.createBitmap(matrix);
                            QR.setImageBitmap(bitmap);
                        }catch (WriterException e){
                            e.printStackTrace();
                        }
                    }else {
                        Toast.makeText(Staff.this, "Id doesn't exist", Toast.LENGTH_SHORT).show();
                    }

                }else {
                        Toast.makeText(Staff.this,"Data doesn't exist",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        
        employeeDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String C="CHONTCHA ", M="MBIA", G="MANGIA";
                String name=snapshot.child(FirebaseAuth.getInstance().getUid()).child("nom").getValue(String.class);
                if(Objects.equals(name, C) || Objects.equals(name, M)){
                    scan.setVisibility(View.VISIBLE);
                    addU.setVisibility(View.VISIBLE);
                    historic.setVisibility(View.VISIBLE);
                }
                if(Objects.equals(name, G)){
                    scan.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



       /* WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int dimen = width<height ? width:height;
        dimen = dimen * 3/4;

        qrgEncoder= new Q*/


        addU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Employee.class));
            }
        });
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Scanner.class));
            }
        });

        historic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),history.class));
                finish();
            }
        });
    }


    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
}