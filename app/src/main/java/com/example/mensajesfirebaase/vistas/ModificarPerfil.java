package com.example.mensajesfirebaase.vistas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.mensajesfirebaase.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;

public class ModificarPerfil extends Fragment {

    private static final int GALLERY_INTENT = 1;
    private StorageReference referencia;
    private EditText name, phone;
    private ImageView image;
    private Button boton, botonActualizar;
    private DatabaseReference database;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        referencia = FirebaseStorage.getInstance().getReference();


    }

    private void modificarImagen() {
        Intent cameraIntent = new Intent(Intent.ACTION_PICK);
        cameraIntent.setType("image/*");
        startActivityForResult(cameraIntent, GALLERY_INTENT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            StorageReference ruta = referencia.child("avatares").child(uri.getLastPathSegment());
            ruta.putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return ruta.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        String nuevaFoto = task.getResult().toString();
                        database.child("Usuarios").child(firebaseAuth.getCurrentUser().getUid()).child("photoUrl").setValue(nuevaFoto);
                    }
                }
            });
        }
    }

    public void cargarDatosUsuario(View root) {
        database.child("Usuarios").child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String nombre = snapshot.child("nombre").getValue().toString();
                    name.setText(nombre);
                    String telefono = snapshot.child("telefono").getValue().toString();
                    phone.setText(telefono);
                    String photoURl = snapshot.child("photoUrl").getValue().toString();
                    image.setImageURI(Uri.parse(photoURl));
                    Glide.with(root.getContext())
                            .load(photoURl)
                            .into(image);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void modificarDatosUsuario() {
        if (phone.getText().toString() != "") {
            database.child("Usuarios").child(firebaseAuth.getCurrentUser().getUid()).child("telefono").setValue(phone.getText().toString());
        }

        if (name.getText().toString() != "") {
            database.child("Usuarios").child(firebaseAuth.getCurrentUser().getUid()).child("nombre").setValue(name.getText().toString());        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_modificar_perfil, container, false);

        name = root.findViewById(R.id.nombreModificar);
        phone = root.findViewById(R.id.telefonoModificar);
        image = root.findViewById(R.id.imagenDeQuienEs);
        boton = root.findViewById(R.id.buttonModificar);
        botonActualizar = root.findViewById(R.id.btn_Actualizar);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarImagen();
                cargarDatosUsuario(root);
            }
        });

        botonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarDatosUsuario();
            }
        });


        cargarDatosUsuario(root);

        return root;
    }
}