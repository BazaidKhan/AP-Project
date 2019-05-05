package com.example.lenovo.ap2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private TextView register;
    EditText Email;
    EditText Password;
    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Email=(EditText) findViewById(R.id.loginEmail);
        Password=(EditText) findViewById(R.id.loginPasswaord);
        register=(TextView)findViewById(R.id.loginRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Register.class);

                startActivity(intent);
            }
        });
    }



    public void login(View view){
        email=Email.getText().toString().trim();
        password=Password.getText().toString().trim();
        if(email.isEmpty() || email.length() == 0 || email.equals("") || email == null)
        {
            Toast.makeText(Login.this,"Fill All Fields!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.isEmpty() || password.length() == 0 || password.equals("") || password == null)
        {
            Toast.makeText(Login.this,"Fill All Fields!",Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest stringRequest= new StringRequest(Request.Method.POST, Constants.LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("Email not verified!!!") || response.contains("Invalid Email or Password!!!")){
                    Toast.makeText(Login.this,response,Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("token",response);
                    finish();
                    startActivity(intent);
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error instanceof TimeoutError){
                    Toast.makeText(Login.this, "Timeout Error!!!", Toast.LENGTH_SHORT).show();
                }
                else if(error instanceof NoConnectionError){
                    Toast.makeText(Login.this, "No Connection Error!!!", Toast.LENGTH_SHORT).show();
                }
                else if(error instanceof AuthFailureError){
                    Toast.makeText(Login.this, "Authentication Failure Error!!!", Toast.LENGTH_SHORT).show();
                }
                else if(error instanceof NetworkError){
                    Toast.makeText(Login.this, "Network Error!!!", Toast.LENGTH_SHORT).show();
                }
                else if(error instanceof ServerError){
                    Toast.makeText(Login.this, "Server Error!!!", Toast.LENGTH_SHORT).show();
                }
                else if(error instanceof ParseError){
                    Toast.makeText(Login.this, "JSON Parse Error!!!", Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            protected Map<String,String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.KEY_EMAIL, email);
                params.put(Constants.KEY_PASSWORD, password);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("User-Agent", "EStore");
                return headers;
            }
        };

        MySingleton.getInstance(Login.this).addToRequestQueue(stringRequest);
    }
}
