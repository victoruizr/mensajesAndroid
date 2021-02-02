package com.example.mensajesfirebaase.vistas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mensajesfirebaase.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CerrarSesion extends Fragment {


    private FirebaseAuth firebaseAuth;
    private NavController navController;
    private DatabaseReference database;
    private String provider;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v = inflater.inflate(R.layout.fragment_cerrar_sesion, container, false);


        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        firebaseAuth.signOut();
        navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.action_cerrarSesion_to_inicio);
        return v;

    }

    public void cerrarSesion() {

    }


}