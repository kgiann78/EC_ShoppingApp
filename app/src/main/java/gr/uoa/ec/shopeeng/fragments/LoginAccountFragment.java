package gr.uoa.ec.shopeeng.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import gr.uoa.ec.shopeeng.R;
import gr.uoa.ec.shopeeng.models.User;
import gr.uoa.ec.shopeeng.requests.LoginRequest;
import gr.uoa.ec.shopeeng.requests.RegisterRequest;


public class LoginAccountFragment extends Fragment {
    Button loginButton;
    Button registerButton;
    AutoCompleteTextView username;
    EditText password;
    private Context applicationContext;


    public LoginAccountFragment() {
    }

    public static LoginAccountFragment newInstance(Context applicationContext) {
        LoginAccountFragment loginAccountFragment = new LoginAccountFragment();
        loginAccountFragment.setApplicationContext(applicationContext);
        return loginAccountFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_account, container, false);
        loginButton = (Button) view.findViewById(R.id.loginButton);
        registerButton = (Button) view.findViewById(R.id.registerButton);
        username = (AutoCompleteTextView) view.findViewById(R.id.login_username);
        password = (EditText) view.findViewById(R.id.login_password);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginRequest registerRequest = new LoginRequest(applicationContext, getFragmentManager(), username.getText().toString(),
                        password.getText().toString());
                AsyncTask<String, Void, User> response = registerRequest.execute();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterAccountFragment registerAccountFragment = new RegisterAccountFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, registerAccountFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    public Context getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
    }
}