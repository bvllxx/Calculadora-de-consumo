package com.example.firestorecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        TextInputEditText phoneET = findViewById(R.id.phoneET);
        TextInputEditText bioET = findViewById(R.id.bioET);
        MaterialButton save = findViewById(R.id.save);
        MaterialButton delete = findViewById(R.id.delete);

        phoneET.setText(App.user.getPhone());
        bioET.setText(App.user.getBio());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("users").document(App.user.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditUserActivity.this, "Alumno Eliminado Correctamente", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditUserActivity.this, "Error Al eliminar Alumno", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> user = new HashMap<>();
                user.put("phone", Objects.requireNonNull(phoneET.getText()).toString());
                user.put("bio", Objects.requireNonNull(bioET.getText()).toString());

                db.collection("users").document(App.user.getId()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditUserActivity.this, "Cambios Guardados Correctamente", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditUserActivity.this, "Error Al guardar cambios", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}