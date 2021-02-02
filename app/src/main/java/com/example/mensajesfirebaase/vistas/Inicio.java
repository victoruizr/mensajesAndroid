package com.example.mensajesfirebaase.vistas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mensajesfirebaase.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;


public class Inicio extends Fragment {

    private FirebaseAuth firebaseAuth;
    private ImageButton bGoogle;
    private Button bLogin, bCrear;
    private EditText email, password;
    private GoogleSignInClient mGoogleSignInCLient;
    private int RC_SIGN_IN = 1234;

    @Override
    public void onStart() {
        try {
            super.onStart();
            if (firebaseAuth.getCurrentUser().getEmail() != null) {
                Navigation.findNavController(getView()).navigate(R.id.action_inicio_to_nav_home);
            }


        } catch (Exception e) {
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        firebaseAuth = FirebaseAuth.getInstance();

        crearRequestGoogle();


    }

    private void crearRequestGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInCLient = GoogleSignIn.getClient(getContext(), gso);
    }

    private void signInGoogle() {
        Intent signIn = mGoogleSignInCLient.getSignInIntent();
        startActivityForResult(signIn, RC_SIGN_IN);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        bGoogle = view.findViewById(R.id.loginGoogle);
        bCrear = view.findViewById(R.id.crearCuenta);
        bLogin = view.findViewById(R.id.bLogin);
        email = view.findViewById(R.id.tEmail);
        password = view.findViewById(R.id.tPassword);

        bGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInGoogle();
            }
        });

        bCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_inicio_to_registrarse);
            }

        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });

        return view;
    }

    private void Login() {
        if (!password.getText().toString().isEmpty() && !email.getText().toString().isEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener((Activity) getContext(),
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Navigation.findNavController(getView()).navigate(R.id.action_inicio_to_nav_home);
                                    } else {
                                        Toast.makeText(getContext(), "Logeado sin exito", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
        } else {
            Toast.makeText(getContext(), "Campos vacios", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = (GoogleSignInAccount) task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(getContext(), "Hola" + e.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener( new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Navigation.findNavController(getView()).navigate(R.id.action_inicio_to_nav_home);

                        } else {
                            Toast.makeText(getContext(),"Fallido",Toast.LENGTH_SHORT).show();
                        }
                    }


                });
    }
}
