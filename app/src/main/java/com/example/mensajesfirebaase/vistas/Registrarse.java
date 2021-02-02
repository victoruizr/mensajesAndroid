package com.example.mensajesfirebaase.vistas;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mensajesfirebaase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registrarse extends Fragment {

    private EditText password, email, name, phone;
    private Button bt;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();

    }

    private void Crear() {
        if (password.getText().toString() != "" && email.getText().toString() != "" && name.getText().toString() != "" && phone.getText().toString() != "") {
            firebaseAuth.createUserWithEmailAndPassword(
                    email.getText().toString(),
                    password.getText().toString()).addOnCompleteListener((Activity) getContext(),
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                String nombre = name.getText().toString();
                                String telefono = phone.getText().toString();

                                Bundle b = new Bundle();
                                b.putSerializable("nombre", nombre);
                                b.putSerializable("telefono", telefono);
                                Navigation.findNavController(getView()).navigate(R.id.action_registrarse_to_nav_home, b);
                            } else {
                                Toast.makeText(getContext(), "Creado sin exito", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        } else {
            Toast.makeText(getContext(), "Campos vacios", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_registrarse, container, false);

        password = root.findViewById(R.id.passwordRegistro);
        email = root.findViewById(R.id.emailRegistro);
        name = root.findViewById(R.id.et_nombreRegister);
        phone = root.findViewById(R.id.et_telefono);

        bt = root.findViewById(R.id.btn_Actualizar);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crear();
            }
        });



        return root;

    }
}