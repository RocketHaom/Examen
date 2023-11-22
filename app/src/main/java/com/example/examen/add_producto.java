package com.example.examen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class add_producto extends AppCompatActivity {

    EditText id, nombre, cantidad, imgURL;
    Button btnAdd, btnCancell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_producto);

        id = findViewById(R.id.txtNewIDProd);
        nombre = findViewById(R.id.txtNewNameProd);
        cantidad = findViewById(R.id.txtNewCantidad);
        imgURL = findViewById(R.id.txtNewURL);

        btnAdd = findViewById(R.id.btnCreateProd);
        btnCancell = findViewById(R.id.btnCancelCreateProd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertarDatos();
            }
        });
        btnCancell.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void insertarDatos() {
        Map<String, Object> map = new HashMap<>();
        map.put("ID", id.getText().toString());
        map.put("Nombre", nombre.getText().toString());
        map.put("Cantidad", cantidad.getText().toString());
        map.put("imgURL", imgURL.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("Programación Android").push()
                .setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
            public void onSuccess(Void unused) {
                        Toast.makeText(add_producto.this, "Producto agregado correctamente", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
            public void onFailure(@NonNull Exception e) {
                        Toast.makeText(add_producto.this, "Error al agregar producto", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}