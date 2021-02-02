package com.example.mensajesfirebaase;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity{
    private TextView correo, nombre,phone;
    private ImageView imageProfile;
    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth firebaseAuth;
    private DrawerLayout drawer;
    private CardView cd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.modificarPerfil, R.id.cerrarSesion)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


    }

    public void changeNavHeaderData(String name, String imagen, String telefono) {
        try {
            NavigationView navigationView = findViewById(R.id.nav_view);
            View header = navigationView.getHeaderView(0);
            correo = header.findViewById(R.id.email);
            nombre = header.findViewById(R.id.nombreUsuario);
            phone = header.findViewById(R.id.email2);
            correo.setText(firebaseAuth.getCurrentUser().getEmail());
            nombre.setText(name);
            phone.setText(telefono);
            imageProfile = header.findViewById(R.id.imagen);
            imageProfile.setImageURI(Uri.parse(imagen));
            Glide.with(this)
                    .load(imagen)
                    .into(imageProfile);
        }catch(NullPointerException ne){}

        }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }


        @Override
        public boolean onSupportNavigateUp () {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                    || super.onSupportNavigateUp();
        }

}