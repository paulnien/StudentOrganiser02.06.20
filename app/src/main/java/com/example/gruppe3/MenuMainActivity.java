package com.example.gruppe3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;



public class MenuMainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.menu.menu_main);
        // Toolbar toolbar = findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

        findViewById(R.id.link1).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                clicked_btn("http://www.w-hs.de");
            }
        });

        findViewById(R.id.link2).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                clicked_btn("http://www.google.de");
            }
        });
    }
    public void clicked_btn(String url)
    {
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        intent.setPackage("com.android.chrome");
        startActivity(intent);
    }
}
