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
import gr.uoa.ec.shopeeng.requests.RegisterRequest;


public class RegisterAccountFragment extends Fragment {
    Button registerButton;
    AutoCompleteTextView username;
    EditText password;
    AutoCompleteTextView name;
    AutoCompleteTextView lastname;
    private Context applicationContext;


    public RegisterAccountFragment() {
    }

    public static RegisterAccountFragment newInstance(Context applicationContext) {
        RegisterAccountFragment registerAccountFragment = new RegisterAccountFragment();
        registerAccountFragment.setApplicationContext(applicationContext);
        return registerAccountFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_account, container, false);
        registerButton = (Button) view.findViewById(R.id.registerButton);
        username = (AutoCompleteTextView) view.findViewById(R.id.register_username);
        password = (EditText) view.findViewById(R.id.register_password);
        name = (AutoCompleteTextView) view.findViewById(R.id.register_name);
        lastname = (AutoCompleteTextView) view.findViewById(R.id.register_surname);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterRequest registerRequest = new RegisterRequest(getApplicationContext(), getFragmentManager(), username.getText().toString(),
                        password.getText().toString(), name.getText().toString(), lastname.getText().toString());
                AsyncTask<String, Void, User> response = registerRequest.execute();
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