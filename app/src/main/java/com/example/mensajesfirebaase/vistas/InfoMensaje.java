package com.example.mensajesfirebaase.vistas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.mensajesfirebaase.R;
import com.example.mensajesfirebaase.modelos.Mensajes;

public class InfoMensaje extends Fragment {

    TextView tDe, Asunto, contenido;
    ImageView imagen;
    Mensajes mensa;
    int id = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mensa = (Mensajes) getArguments().getSerializable("Mensaje");
            id = getArguments().getInt("id");
        }


    }

    private void mostrarDatos(int id) {
        if (id == 0) {
            Glide.with(getContext())
                    .load(mensa.getImagenUsuarioEnviado())
                    .into(imagen);
            tDe.setText(mensa.getPara());
            contenido.setText("" + mensa.getContenido());
        } else {
            Glide.with(getContext())
                    .load(mensa.getImagenUsuarioRecibido())
                    .into(imagen);

            tDe.setText(mensa.getDe());

            contenido.setText("" + mensa.getContenido());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_info_mensaje, container, false);

        tDe = root.findViewById(R.id.textDe);
        contenido = root.findViewById(R.id.para);
        imagen = root.findViewById(R.id.autor);

        mostrarDatos(id);


        return root;
    }
}