package com.example.mensajesfirebaase.vistas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mensajesfirebaase.Adaptadores.AdaptadorMensajes;
import com.example.mensajesfirebaase.R;
import com.example.mensajesfirebaase.modelos.Mensajes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Enviados extends Fragment {
    private DatabaseReference database;
    private FirebaseAuth firebaseAuth;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Mensajes> listaMensajes = new ArrayList<Mensajes>();
    private Mensajes mensaje;
    private RecyclerView rv;
    private RecyclerView.Adapter adapter;
    private int id;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        cargarEnviados();

        rv = root.findViewById(R.id.rvEnviados);
        //Toast.makeText(getContext(),""+root.getContext(),Toast.LENGTH_SHORT).show();
        //Toast.makeText(getContext(), "" + this.getId(), Toast.LENGTH_SHORT).show();
        id = root.getId();
        //Toast.makeText(getContext(), "" + id, Toast.LENGTH_SHORT).show();
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);

    }

    public void cargarEnviados() {
        database.child("Usuarios").child(firebaseAuth.getCurrentUser().getUid()).child("listaEnviados").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaMensajes.clear();
                for (DataSnapshot xMensajes : snapshot.getChildren()) {
                    mensaje = xMensajes.getValue(Mensajes.class);
                    listaMensajes.add(mensaje);
                }
                adapter = new AdaptadorMensajes(listaMensajes, getActivity(), id);
                rv.setAdapter(adapter);
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}