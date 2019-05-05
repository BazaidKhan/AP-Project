package com.example.lenovo.ap2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {
    Button changePhone;
    Button changePassword;
    Button changeUsername;
    String Token;
    User user;
    TextView usrname;
    TextView Email;
    TextView Phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        usrname=findViewById(R.id.username);
        Email=findViewById(R.id.email);
        Phone=findViewById(R.id.phone);
        changePassword=findViewById(R.id.changePassword);
        changePhone=findViewById(R.id.changePhone);
        changeUsername=findViewById(R.id.changeUsername);
        Intent i=getIntent();
        Token=i.getStringExtra("token");
        loadUser();

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ChangePassword.class);
                intent.putExtra("user",user);
                intent.putExtra("token",Token);
                startActivity(intent);
            }
        });
        changePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ChangePhone.class);
                intent.putExtra("user",user);
                intent.putExtra("token",Token);
                startActivity(intent);
            }
        });
        changeUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ChangeUsername.class);
                intent.putExtra("user",user);
                intent.putExtra("token",Token);
                startActivity(intent);
            }
        });

    }
    public void loadUser(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.PROFILE_URL+"?code="+Token,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        // progressBar.setVisibility(View.INVISIBLE);
                        String currentString = response;
                        String[] separated = currentString.split(":");

                        String currentString2 = separated[3];
                        String separated2 = currentString2.replace("{}"," ");
                        separated2.trim();




                                //creating a tutorial object and giving them the values from json object
                                user= new User(Integer.parseInt(separated[0]),separated[1],separated[2],separated2);

                                //adding the tutorial to tutoriallist

                           // }
                            usrname.setText(user.name);
                            Email.setText(user.email);
                            Phone.setText(user.phone);



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occur
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("User-Agent", "EStore");
                return headers;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
}
