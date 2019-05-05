package com.example.lenovo.ap2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button Home;
    private Button Cart;
    private Button Wishlist;
    private Button FAQ;
    private Button Profile;
    private Button Contact;
    public String Token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i=getIntent();
        Token=i.getStringExtra("token");
        Home=(Button)findViewById(R.id.home);
        Cart=(Button)findViewById(R.id.cart);
        Wishlist=(Button)findViewById(R.id.wishlist);
        FAQ=(Button)findViewById(R.id.faq);
        Profile=(Button)findViewById(R.id.profile);
        Contact=(Button)findViewById(R.id.contact);

        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Home.class);
                intent.putExtra("token",Token);
                startActivity(intent);
            }
        });

        Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Cart.class);
                intent.putExtra("token",Token);
                startActivity(intent);
            }
        });

        Wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Wishlist.class);
                intent.putExtra("token",Token);
                startActivity(intent);
            }
        });

        FAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),FAQ.class);
                intent.putExtra("token",Token);
                startActivity(intent);
            }
        });

        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Profile.class);
                intent.putExtra("token",Token);
                startActivity(intent);
            }
        });

        Contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Contact.class);
                intent.putExtra("token",Token);
                startActivity(intent);
            }
        });


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
