package com.example.lenovo.ap2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Wishlist extends AppCompatActivity {

    public String Token;
    ListView listView;
    Button Logout;

    ArrayList<Item> itemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        Logout=(Button)findViewById(R.id.logoutcartwishlist);
        Intent i=getIntent();
        Token=i.getStringExtra("token");
        listView = (ListView) findViewById(R.id.wishlistList);
        itemList = new ArrayList<>();
        loadWishlist();
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest= new StringRequest(Request.Method.POST, Constants.LOGOUT_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.contains("Logged Out!!!") ){
                            Toast.makeText(Wishlist.this,response,Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(),Login.class);
                            intent.putExtra("token",response);
                            finish();
                            startActivity(intent);
                        }else{
                            Toast.makeText(Wishlist.this,response,Toast.LENGTH_SHORT).show();
                        }



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error instanceof TimeoutError){
                            Toast.makeText(Wishlist.this, "Timeout Error!!!", Toast.LENGTH_SHORT).show();
                        }
                        else if(error instanceof NoConnectionError){
                            Toast.makeText(Wishlist.this, "No Connection Error!!!", Toast.LENGTH_SHORT).show();
                        }
                        else if(error instanceof AuthFailureError){
                            Toast.makeText(Wishlist.this, "Authentication Failure Error!!!", Toast.LENGTH_SHORT).show();
                        }
                        else if(error instanceof NetworkError){
                            Toast.makeText(Wishlist.this, "Network Error!!!", Toast.LENGTH_SHORT).show();
                        }
                        else if(error instanceof ServerError){
                            Toast.makeText(Wishlist.this, "Server Error!!!", Toast.LENGTH_SHORT).show();
                        }
                        else if(error instanceof ParseError){
                            Toast.makeText(Wishlist.this, "JSON Parse Error!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }){
                    @Override
                    protected Map<String,String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("code", Token);
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("User-Agent", "EStore");
                        return headers;
                    }
                };

                MySingleton.getInstance(Wishlist.this).addToRequestQueue(stringRequest);
            }
        });

    }
    private void loadWishlist() {
        //getting the progressbar
        /*final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);*/

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.WISHLIST_URL+"?code="+Token,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        // progressBar.setVisibility(View.INVISIBLE);


                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named tutorial inside the object
                            //so here we are getting that json array
                            JSONArray itemArray = obj.getJSONArray("item");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < itemArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject item = itemArray.getJSONObject(i);

                                //creating a tutorial object and giving them the values from json object
                                Item tutorial = new Item(Integer.parseInt(item.getString("id")),item.getString("name"),Integer.parseInt(item.getString("stock")),Integer.parseInt(item.getString("rating")),item.getString("img"),Float.parseFloat(item.getString("price")));

                                //adding the tutorial to tutoriallist
                                itemList.add(tutorial);
                            }

                            //creating custom adapter object
                            WishListAdapter adapter = new WishListAdapter(Wishlist.this,itemList);

                            //adding the adapter to listview
                            listView.setAdapter(adapter);
                            /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    updateDetail(position);
                                }
                            });*/

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occur
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){

            /*@Override
            protected Map<String,String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("code",Token);
                return params;
            }*/
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

