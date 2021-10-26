package com.example.carebot.signFragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.ContactsContract;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.carebot.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPasswordFragment extends Fragment {
    private EditText forgotEmailText;
    private Button forgotPassword;
    private String forgotEmail;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        forgotEmailText = view.findViewById(R.id.forgetPasswordEmail);
        forgotPassword = view.findViewById(R.id.forgetPasswordButton);
        NavController navController = Navigation.findNavController(view);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotEmail = forgotEmailText.getText().toString().trim();
                if(forgotEmail.isEmpty()){
                    forgotEmailText.setError("field can't be empty");

                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(forgotEmail).matches()){
                    forgotEmailText.setError("invalid email id");

                }
                else{
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.sendPasswordResetEmail(forgotEmail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(getActivity(), "Check your email", Toast.LENGTH_SHORT).show();
                                        navController.navigate(R.id.action_forgotPasswordFragment_to_loginFragment);
                                    }
                                    else{
                                        Toast.makeText(getActivity(), "Error!:"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });
                }

            }
        });
    }
}