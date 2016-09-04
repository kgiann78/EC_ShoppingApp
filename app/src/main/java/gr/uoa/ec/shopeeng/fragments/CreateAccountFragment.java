package gr.uoa.ec.shopeeng.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import gr.uoa.ec.shopeeng.R;
import gr.uoa.ec.shopeeng.models.User;
import gr.uoa.ec.shopeeng.requests.RegisterRequest;

import java.util.concurrent.ExecutionException;

import static gr.uoa.ec.shopeeng.utils.Constants.*;


public class CreateAccountFragment extends Fragment {
    Button registerButton;
    AutoCompleteTextView username;
    EditText password;
    AutoCompleteTextView name;
    AutoCompleteTextView lastname;
    private Context applicationContext;


    public CreateAccountFragment() {
    }

    public static CreateAccountFragment newInstance(Context sapplicationContext) {
        CreateAccountFragment createAccountFragment = new CreateAccountFragment();
        createAccountFragment.setApplicationContext(sapplicationContext);
        return createAccountFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_create_account, container, false);
        registerButton = (Button) view.findViewById(R.id.registerButton);
        username = (AutoCompleteTextView) view.findViewById(R.id.register_username);
        password = (EditText) view.findViewById(R.id.register_password);
        name = (AutoCompleteTextView) view.findViewById(R.id.register_name);
        lastname = (AutoCompleteTextView) view.findViewById(R.id.register_surname);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterRequest registerRequest = new RegisterRequest(username.getText().toString(),
                        password.getText().toString(), name.getText().toString(), lastname.getText().toString());
                AsyncTask<String, Void, Object> response = registerRequest.execute();

                if (response.getStatus().equals(AsyncTask.Status.FINISHED)) {
                    try {
                        User user = (User) response.get();
                        if (user == null) {
                            Toast.makeText(getApplicationContext(),
                                    "Η εγγραφή σου απέτυχε.. Προσπάθησε ξανά!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Επιτυχής εγγραφή!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.e("Registration Error ", e.toString());
                        e.printStackTrace();
                    }
                }

            }
        });
        return view;
    }
    /*

    // TODO: Rename and change types and number of parameters
    public static CreateAccountFragment newInstance(String username, String password, String name, String lastname) {
        CreateAccountFragment fragment = new CreateAccountFragment();
        Bundle args = new Bundle();
        args.putString(USERNAME, username);
        args.putString(PASSWORD, password);
        args.putString(NAME, name);
        args.putString(LASTNAME, lastname);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(USERNAME);
            password = getArguments().getString(PASSWORD);
            name = getArguments().getString(NAME)
            lastname = getArguments().getString(LASTNAME);
        }
    }
*/

    public Context getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
    }
}