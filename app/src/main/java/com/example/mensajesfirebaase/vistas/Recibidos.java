package com.example.mensajesfirebaase.vistas;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mensajesfirebaase.Adaptadores.AdaptadorMensajes;
import com.example.mensajesfirebaase.MainActivity;
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
import java.util.List;

public class Recibidos extends Fragment {
    private FirebaseAuth firebaseAuth;
    private ImageView imageProfile;
    MainActivity activityX;
    private DatabaseReference database;
    String nombre, imagen, telefono;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Mensajes> listaMensajes = new ArrayList<Mensajes>();
    private Mensajes mensaje;
    private RecyclerView rv;
    private int id;
    private RecyclerView.Adapter adapter;
    String photoURl;
    private String provider;
    NotificationCompat.Builder mBUilder;
    int mNotificacionId = 1;
    String channelId = "my_channel_01";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        id = root.getId();
        rv = root.findViewById(R.id.rvRecibidos);


        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        try {

            if (getArguments() != null) {
                nombre = (String) getArguments().getSerializable("nombre");
                telefono = (String) getArguments().getSerializable("telefono");
                provider = "firebase";
            } else {
                nombre = firebaseAuth.getCurrentUser().getDisplayName();
                telefono = firebaseAuth.getCurrentUser().getPhoneNumber();
                provider = "Google";
            }
            cargarDatosUsuario();
            cargarRecibidos(root);
        } catch (Exception e) {

        }

        return root;
    }

    public void cargarDatosUsuario() {
        database.child("Usuarios").child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String nombre = snapshot.child("nombre").getValue().toString();
                    photoURl = snapshot.child("photoUrl").getValue().toString();
                    telefono = snapshot.child("telefono").getValue().toString();
                    activityX.changeNavHeaderData(nombre, photoURl, telefono);
                } else {
                    crearUsuario();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layoutManager = new LinearLayoutManager(getActivity());


        rv.setLayoutManager(layoutManager);
    }

    public void crearUsuario() {
        if (nombre == null) {
            nombre = "Sin nombre";
        }

        if (telefono == null) {
            telefono = "Sin telefono";
        }

        if (firebaseAuth.getCurrentUser().getPhotoUrl() == null) {
            imagen = "https://firebasestorage.googleapis.com/v0/b/mensajesfirebase-90e5a.appspot.com/o/avatares%2Fperfil.png?alt=media&token=27eeccf7-8b2e-4399-b464-33cc35583857";
        } else {
            imagen = firebaseAuth.getCurrentUser().getPhotoUrl().toString();
        }


        List<Mensajes> listaEnviados = new ArrayList<Mensajes>();
        List<Mensajes> listaRecibidos = new ArrayList<Mensajes>();
        Mensajes men = new Mensajes();
        listaEnviados.add(men);
        listaRecibidos.add(men);
        Usuario usu = new Usuario(
                firebaseAuth.getCurrentUser().getUid(),
                firebaseAuth.getCurrentUser().getEmail(),
                nombre,
                telefono,
                provider,
                imagen,
                listaEnviados,
                listaRecibidos
        );

        activityX.changeNavHeaderData(nombre, imagen, telefono);
        database.child("Usuarios").child(firebaseAuth.getCurrentUser().getUid()).setValue(usu);

    }

    public void cargarRecibidos(View root) {
        database.child("Usuarios").child(firebaseAuth.getCurrentUser().getUid()).child("listaRecibidos").addValueEventListener(new ValueEventListener() {
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
                notificaciones();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activityX = (MainActivity) context;
    }

    public void notificaciones() {

        NotificationManager mNotificationManager = (NotificationManager) getContext().getSystemService(getContext().getApplicationContext().NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "Email";

            String descripcion = "Comunicacion de email al usuario";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel mChannel = new NotificationChannel(channelId, name, importance);

            mChannel.setDescription(descripcion);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mNotificationManager.createNotificationChannel(mChannel);

            mBUilder = new NotificationCompat.Builder(getContext(), channelId);

        }

        mBUilder.setSmallIcon(R.drawable.gmail).setContentTitle("Email").setContentText("Tienes un nuevo email");
        mNotificationManager.notify(mNotificacionId,mBUilder.build());


    }

}