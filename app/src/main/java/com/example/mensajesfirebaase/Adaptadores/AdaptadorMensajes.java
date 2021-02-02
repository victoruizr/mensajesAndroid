package com.example.mensajesfirebaase.Adaptadores;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mensajesfirebaase.R;
import com.example.mensajesfirebaase.modelos.Mensajes;

import java.util.ArrayList;

public class AdaptadorMensajes extends RecyclerView.Adapter<AdaptadorMensajes.AdaptadorViewHolder> {

    private ArrayList<Mensajes> listaEnviados = new ArrayList<>();
    private Context context;
    private int id;

    public AdaptadorMensajes(ArrayList<Mensajes> listaEnviados, Context context, int id) {
        this.listaEnviados = listaEnviados;
        this.context = context;
        this.id = id;
    }

    @NonNull
    @Override
    public AdaptadorMensajes.AdaptadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mensajes, parent, false);
        AdaptadorViewHolder avh = new AdaptadorViewHolder(itemView);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorMensajes.AdaptadorViewHolder holder, int position) {
        Mensajes mensaje = listaEnviados.get(position);

        holder.asunto.setText(mensaje.getAsunto());
        // holder.Contenido.setText(mensaje.getContenido());
        //


        if (id == 2131231035) {
            holder.deQuienEs.setText(mensaje.getDe());
            holder.Para.setText(mensaje.getPara());
            Glide.with(holder.itemView)
                    .load(mensaje.getImagenUsuarioRecibido())
                    .into(holder.imagen);
        } else {

            holder.deQuienEs.setText(mensaje.getPara());
            holder.Para.setText(mensaje.getDe());
            Glide.with(holder.itemView)
                    .load(mensaje.getImagenUsuarioEnviado())
                    .into(holder.imagen);


        }


        holder.imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putSerializable("Mensaje", mensaje);

                if (id == 2131231035) {
                    b.putSerializable("id", 1);
                    Navigation.findNavController(v).navigate(R.id.action_nav_home_to_infoMensaje, b);
                } else {
                    b.putSerializable("id", 0);

                    Navigation.findNavController(v).navigate(R.id.action_nav_gallery_to_infoMensaje, b);
                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return listaEnviados.size();
    }


    public class AdaptadorViewHolder extends RecyclerView.ViewHolder {
        private TextView deQuienEs, asunto, Para;
        private ImageView imagen;
        private ConstraintLayout ct;

        public AdaptadorViewHolder(@NonNull View itemView) {
            super(itemView);
            deQuienEs = itemView.findViewById(R.id.deQuienEs);
            asunto = itemView.findViewById(R.id.asunto);
            imagen = itemView.findViewById(R.id.imagenDeQuienEs);
            Para = itemView.findViewById(R.id.para);
            //ct = itemView.findViewById(R.id.cLayout);
        }
    }
}
