package com.example.lenovo.ap2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class ChangePassword extends AppCompatActivity {
    Button changePassword;
    EditText newPassword;
    EditText oldPassowrd;
    User user;
    String Token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Intent i=getIntent();
        user=(User)i.getSerializableExtra("user");
        Token=i.getStringExtra("token");

        changePassword=findViewById(R.id.changePassworduser);
        newPassword=findViewById(R.id.newpassword);
        oldPassowrd=findViewById(R.id.oldpassword);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest= new StringRequest(Request.Method.POST, Constants.CHANGEPASSWORD_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.contains("Congratulations! Password Changed!") ){
                            Toast.makeText(ChangePassword.this,response,Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(),Profile.class);
                            intent.putExtra("token",Token);
                            startActivity(intent);
                        }else{
                            Toast.makeText(ChangePassword.this,response,Toast.LENGTH_SHORT).show();
                        }



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error instanceof TimeoutError){
                            Toast.makeText(ChangePassword.this, "Timeout Error!!!", Toast.LENGTH_SHORT).show();
                        }
                        else if(error instanceof NoConnectionError){
                            Toast.makeText(ChangePassword.this, "No Connection Error!!!", Toast.LENGTH_SHORT).show();
                        }
                        else if(error instanceof AuthFailureError){
                            Toast.makeText(ChangePassword.this, "Authentication Failure Error!!!", Toast.LENGTH_SHORT).show();
                        }
                        else if(error instanceof NetworkError){
                            Toast.makeText(ChangePassword.this, "Network Error!!!", Toast.LENGTH_SHORT).show();
                        }
                        else if(error instanceof ServerError){
                            Toast.makeText(ChangePassword.this, "Server Error!!!", Toast.LENGTH_SHORT).show();
                        }
                        else if(error instanceof ParseError){
                            Toast.makeText(ChangePassword.this, "JSON Parse Error!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }){
                    @Override
                    protected Map<String,String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("code",Token);
                        params.put("prevpass", oldPassowrd.getText().toString());
                        params.put("newpass",newPassword.getText().toString());
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("User-Agent", "EStore");
                        return headers;
                    }
                };

                MySingleton.getInstance(ChangePassword.this).addToRequestQueue(stringRequest);
            }
        });

    }
}
