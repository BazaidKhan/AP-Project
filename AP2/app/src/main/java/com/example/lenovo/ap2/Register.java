package com.example.lenovo.ap2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
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

public class Register extends AppCompatActivity {

    EditText nametxt;
    EditText emailtxt;
    EditText passwordtxt;
    EditText phonetxt;
    String name;
    String email;
    String password;
    String phone;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nametxt=(EditText) findViewById(R.id.registerName);
        emailtxt=(EditText) findViewById(R.id.registeremail);
        passwordtxt=(EditText) findViewById(R.id.registerPasswaord);
        phonetxt=(EditText) findViewById(R.id.regphone);

        context=this.context;
    }
    public void register (View view){
        name=nametxt.getText().toString().trim();
        email=emailtxt.getText().toString().trim();
        password=passwordtxt.getText().toString().trim();
        phone=phonetxt.getText().toString().trim();

        if(name.isEmpty() || name.length() == 0 || name.equals("") || name == null)
        {
            Toast.makeText(Register.this,"Fill All Fields!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(email.isEmpty() || email.length() == 0 || email.equals("") || email == null)
        {
            Toast.makeText(Register.this,"Fill All Fields!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.isEmpty() || password.length() == 0 || password.equals("") || password == null)
        {
            Toast.makeText(Register.this,"Fill All Fields!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(phone.isEmpty() || phone.length() == 0 || phone.equals("") || phone == null)
        {
            Toast.makeText(Register.this,"Fill All Fields!",Toast.LENGTH_SHORT).show();
            return;
        }
        StringRequest stringRequest= new StringRequest(Request.Method.POST, Constants.REGISTER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("Please check your e-mail!!!") || response.contains("E-mail already exists!!!")){
                    Intent intent=new Intent(getApplicationContext(),Login.class);
                    startActivity(intent);
                }

                Toast.makeText(Register.this,response,Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error instanceof TimeoutError){
                    Toast.makeText(Register.this, "Timeout Error!!!", Toast.LENGTH_SHORT).show();
                }
                else if(error instanceof NoConnectionError){
                    Toast.makeText(Register.this, "No Connection Error!!!", Toast.LENGTH_SHORT).show();
                }
                else if(error instanceof AuthFailureError){
                    Toast.makeText(Register.this, "Authentication Failure Error!!!", Toast.LENGTH_SHORT).show();
                }
                else if(error instanceof NetworkError){
                    Toast.makeText(Register.this, "Network Error!!!", Toast.LENGTH_SHORT).show();
                }
                else if(error instanceof ServerError){
                    Toast.makeText(Register.this, "Server Error!!!", Toast.LENGTH_SHORT).show();
                }
                else if(error instanceof ParseError){
                    Toast.makeText(Register.this, "JSON Parse Error!!!", Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            protected Map<String,String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.KEY_NAME, name);
                params.put(Constants.KEY_EMAIL, email);
                params.put(Constants.KEY_PASSWORD, password);
                params.put("phone",phone);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("User-Agent", "EStore");
                return headers;
            }
        };

        MySingleton.getInstance(Register.this).addToRequestQueue(stringRequest);
    }
}
