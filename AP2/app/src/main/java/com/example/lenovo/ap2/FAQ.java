package com.example.lenovo.ap2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
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
import java.util.Map;

public class FAQ extends AppCompatActivity {

    public ListView listView;
    String Token;
    public ArrayList fqList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        Intent i = getIntent();
        Token=i.getStringExtra("token");
        listView = (ListView) findViewById(R.id.faqlist);
        fqList = new ArrayList<fq>();
        loadfq();
    }
    public void loadfq(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.FAQ_URL+"?code="+Token,
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
                            JSONArray itemArray = obj.getJSONArray("faq");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < itemArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject item = itemArray.getJSONObject(i);

                                //creating a tutorial object and giving them the values from json object
                                fq tutorial = new fq(item.getString("query"),Integer.parseInt(item.getString("user_id")));

                                //adding the tutorial to tutoriallist
                                fqList.add(tutorial);
                            }

                            //creating custom adapter object
                            FaqAdapter adapter = new FaqAdapter(FAQ.this,fqList);

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
