package com.example.mensajesfirebaase.vistas;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mensajesfirebaase.R;
import com.example.mensajesfirebaase.modelos.Mensajes;
import com.example.mensajesfirebaase.modelos.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class AnadirMensaje extends Fragment {
    ArrayList<Usuario> usuarios;
    ArrayList<Mensajes> listaMensajes;
    //ArrayList<Mensajes> listaMensajes2;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference database;
    private Usuario usuario;
    private Mensajes mensaje;
    private EditText de, asunto, contenido;
    private Button bt;
    private Spinner sp;
    int numeros = 0;
    int numero;
    private ValueEventListener eventListener;
    private String photoURl;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        usuarios = new ArrayList<Usuario>();
        listaMensajes = new ArrayList<Mensajes>();

        /*Firebase*/
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        rellenarArray();
        //rellenarArrayRecibidos();
        cargarLista();


        de = root.findViewById(R.id.editTextTextPersonName);
        de.setText(firebaseAuth.getCurrentUser().getEmail());
        asunto = root.findViewById(R.id.editTextTextPersonName3);
        contenido = root.findViewById(R.id.editTextTextMultiLine);
        sp = root.findViewById(R.id.spinnerUsuarios);
        bt = root.findViewById(R.id.buttonEnviar);
        cargarDatosUsuario();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (de.getText().toString().isEmpty() || asunto.getText().toString().isEmpty() || contenido.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Campos vacios", Toast.LENGTH_SHORT).show();
                } else {
                    anadirMensaje();
                }

            }
        });


        return root;
    }

    public void rellenarArray2(Usuario us) {
        database = FirebaseDatabase.getInstance().getReference();
        database.child("Usuarios").child(us.getUid()).child("listaRecibidos").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Mensajes> listaMensajes2 = new ArrayList<Mensajes>();
                listaMensajes2.clear();
                for (DataSnapshot xMensajes : snapshot.getChildren()) {
                    mensaje = xMensajes.getValue(Mensajes.class);
                    listaMensajes2.add(mensaje);
                }
                Date d = new Date();
                CharSequence s = DateFormat.format("dd/MM/yyyy", d.getTime());

                Mensajes men = new Mensajes(de.getText().toString(), us.getEmail(), asunto.getText().toString(), contenido.getText().toString(), s.toString(), photoURl, us.getPhotoUrl());
                //listaMensajes2.add(men);
                //Toast.makeText(getContext(), "" + listaMensajes2.size(), Toast.LENGTH_SHORT).show();

                database.child("Usuarios").child(us.getUid()).child("listaRecibidos").child(String.valueOf(listaMensajes2.size())).setValue(men);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void anadirMensaje() {
        if (de.getText().toString().isEmpty() || asunto.getText().toString().isEmpty() || contenido.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Campos vacios", Toast.LENGTH_SHORT).show();
        } else {
            Usuario us = (Usuario) sp.getSelectedItem();
            rellenarArray2(us);
            Date d = new Date();
            CharSequence s = DateFormat.format("dd/MM/yyyy", d.getTime());
            Mensajes men = new Mensajes(de.getText().toString(), us.getEmail(), asunto.getText().toString(), contenido.getText().toString(), s.toString(), photoURl, us.getPhotoUrl());
            database.child("Usuarios").child(firebaseAuth.getCurrentUser().getUid()).child("listaEnviados").child(String.valueOf(listaMensajes.size())).setValue(men);


        }

    }


    public void cargarDatosUsuario() {
        database.child("Usuarios").child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    photoURl = snapshot.child("photoUrl").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }


    public void rellenarArray() {
        database = FirebaseDatabase.getInstance().getReference();
        database.child("Usuarios").child(firebaseAuth.getCurrentUser().getUid()).child("listaEnviados").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaMensajes.clear();
                for (DataSnapshot xMensajes : snapshot.getChildren()) {
                    mensaje = xMensajes.getValue(Mensajes.class);
                    listaMensajes.add(mensaje);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    public void cargarLista() {
        database = FirebaseDatabase.getInstance().getReference();
        ArrayAdapter<Usuario> adapter = new ArrayAdapter<Usuario>(getActivity(), R.layout.support_simple_spinner_dropdown_item, usuarios);

        database.child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuarios.clear();
                for (DataSnapshot xUsuarios : snapshot.getChildren()) {
                    usuario = xUsuarios.getValue(Usuario.class);
                    if (!firebaseAuth.getCurrentUser().getUid().equals(usuario.getUid())) {
                        usuarios.add(usuario);
                    }
                }
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                if(usuarios.size()==0){
                    Toast.makeText(getContext(),"No hay usuarios a los que enviar email",Toast.LENGTH_LONG).show();
                    Navigation.findNavController(getView()).navigate(R.id.action_nav_slideshow_to_nav_home);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}