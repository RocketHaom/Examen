package com.example.examen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel, MainAdapter.myViewHolder> {

    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MainModel model) {
        holder.id.setText("ID: " + model.getID());
        holder.nombre.setText("Nombre: " + model.getNombre());
        holder.cantidad.setText("Cantidad: " + model.getCantidad());
        Glide.with(holder.img.getContext())
                .load(model.getImgURL())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.google.firebase.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);

        holder.editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialog = new AlertDialog.Builder(holder.img.getContext()).create();
                View view = LayoutInflater.from(holder.img.getContext()).inflate(R.layout.edit_producto, null);

                EditText id = view.findViewById(R.id.txtEditIDProd);
                EditText nombre = view.findViewById(R.id.txtEditNameProd);
                EditText cantidad = view.findViewById(R.id.txtEditCantidadProd);
                EditText imgUrl = view.findViewById(R.id.actualizarImagen);

                Button actualizar = view.findViewById(R.id.btnEditProd);
                Button cancelar = view.findViewById(R.id.btnCancelEditProd);

                id.setText(model.getID());
                nombre.setText(model.getNombre());
                cantidad.setText(model.getCantidad());
                imgUrl.setText(model.getImgURL());

                cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                actualizar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("ID", id.getText().toString());
                        map.put("Nombre", nombre.getText().toString());
                        map.put("Cantidad", cantidad.getText().toString());
                        map.put("imgURL", imgUrl.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Programación Android")
                                .child(getRef(holder.getAdapterPosition()).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.nombre.getContext(), "Producto actualizado", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.nombre.getContext(), "Error al editar", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });
                    }
                });

                dialog.setView(view);
                dialog.show();
            }
        });

        holder.eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.nombre.getContext());
                builder.setTitle("¿Eliminar producto?");
                builder.setMessage("Seguro que deseas eliminar este producto?");

                builder.setPositiveButton("Eliminar producto", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Programación Android")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.nombre.getContext(), "Cancelar", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new myViewHolder(view);
    }

    static class myViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView id, nombre, cantidad;
        Button editar, eliminar;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img1);
            id = itemView.findViewById(R.id.idText);
            nombre = itemView.findViewById(R.id.nombreText);
            cantidad = itemView.findViewById(R.id.cantidadText);

            editar = itemView.findViewById(R.id.btnEditProd);
            eliminar = itemView.findViewById(R.id.btnDeleteProd);

        }
    }
}
