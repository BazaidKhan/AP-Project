package com.example.lenovo.ap2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class Home extends AppCompatActivity {
    GridView listView;
    public String Token;
    ArrayList<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent i = getIntent();
        Token=i.getStringExtra("token");
        listView = (GridView) findViewById(R.id.listView);
        itemList = new ArrayList<>();
        //this method will fetch and parse the data
        loadTutorialList();
    }

    private void loadTutorialList() {
        //getting the progressbar
        /*final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);*/

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.HOME_URL,
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
                            HomeAdapter adapter = new HomeAdapter(Home.this,itemList);

                            //adding the adapter to listview
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    updateDetail(position);
                                }
                            });

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
    public void updateDetail(int i) {
        Intent intent=new Intent(getApplicationContext(),Detail.class);
        intent.putExtra("item",itemList.get(i));
        intent.putExtra("token",Token);
        startActivity(intent);
    }


}
