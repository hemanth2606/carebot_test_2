package com.example.carebot.signFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.preference.Preference;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carebot.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class LoginFragment extends Fragment {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");
    TextInputLayout textInputPassword;
    TextInputLayout textInputEmail;
    Button loginButton;
    FirebaseAuth fAuth;
    TextView forgotPassword;
    CheckBox rememberMe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = view.findViewById(R.id.createAccount);
        NavController navController = Navigation.findNavController(view);
        textInputPassword = view.findViewById(R.id.loginPassword);
        textInputEmail = view.findViewById(R.id.loginEmail);
        loginButton = view.findViewById(R.id.loginButton);
        ///////////////////////////////////////////
        rememberMe = view.findViewById(R.id.rememberMe);
        SharedPreferences preferences = getActivity().getSharedPreferences("checkbox", Context.MODE_PRIVATE);
        String checkbox = preferences.getString("rememberMe","");
        if(checkbox.equals("True")){
            navController.navigate(R.id.action_loginFragment_to_mainFragment);
            
        }
        else if(checkbox.equals("false")){
            Toast.makeText(getActivity(), "Please Sign IN", Toast.LENGTH_SHORT).show();
        }
        //////////////////////////////

        forgotPassword = view.findViewById(R.id.forgotPassword);
        fAuth = FirebaseAuth.getInstance();
        ///////////////////////////////
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_loginFragment_to_forgotPasswordFragment);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_loginFragment_to_signUpFragment);
            }
        });
        //////////////////////////////////////
        rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    SharedPreferences preferences = getActivity().getSharedPreferences("checkbox", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("rememberMe","true");
                    editor.apply();
                    Toast.makeText(getActivity(), "Checked", Toast.LENGTH_SHORT).show();



                }
                else if (!compoundButton.isChecked()){
                    SharedPreferences preferences = getActivity().getSharedPreferences("checkbox", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("rememberMe","false");
                    editor.apply();
                    Toast.makeText(getActivity(), "Unchecked", Toast.LENGTH_SHORT).show();

                }
                else{

                }

            }
        });
        ///////////////////////////////
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email= textInputEmail.getEditText().getText().toString().trim();
                String Password= textInputPassword.getEditText().getText().toString().trim();
                if(!validateEmail() | !validatePassword()){
                    Toast.makeText(getActivity(), "Enter valid credentials", Toast.LENGTH_SHORT).show();
                }
                else{
                    fAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getActivity(), "Login Successful",Toast.LENGTH_SHORT).show();
                                navController.navigate(R.id.action_loginFragment_to_mainFragment);

                            }
                            else{
                                Toast.makeText(getActivity(), "Error!:"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
    private boolean validatePassword() {
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()) {
            textInputPassword.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            textInputPassword.setError("Password too weak");
            return false;
        } else {
            textInputPassword.setError(null);
            return true;
        }
    }
    private boolean validateEmail() {
        String emailInput = textInputEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            textInputEmail.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            textInputEmail.setError("Please enter a valid email address");
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }
}