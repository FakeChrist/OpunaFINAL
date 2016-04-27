package aspx.ukindex.ac.brighton.httpswww.opuna2;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText enFirstname = (EditText)findViewById(R.id.enFirstname);
        final EditText enLastname = (EditText)findViewById(R.id.enLastname);
        final EditText enAge = (EditText)findViewById(R.id.enAge);
        final EditText enSchool = (EditText)findViewById(R.id.enSchool);
        final EditText enStype = (EditText)findViewById(R.id.enStype);
        final EditText enEmail = (EditText)findViewById(R.id.enEmail);
        final EditText enUsername = (EditText)findViewById(R.id.enUsername);
        final EditText enPassword = (EditText)findViewById(R.id.enPassword);
        final Button bRegister = (Button) findViewById (R.id.bRegister);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Firstname = enFirstname.getText().toString();
                final String Lastname = enLastname.getText().toString();
                final int Age = Integer.parseInt(enAge.getText().toString());
                final String School = enSchool.getText().toString();
                final String Stype = enStype.getText().toString();
                final String Email = enEmail.getText().toString();
                final String Username = enUsername.getText().toString();
                final String Password = enPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success){
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);

                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Register Completed")
                                        .create()
                                        .show();
                                RegisterActivity.this.startActivity(intent);
                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Register Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(Firstname, Lastname, Age, School, Stype, Email, Username, Password,responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}
