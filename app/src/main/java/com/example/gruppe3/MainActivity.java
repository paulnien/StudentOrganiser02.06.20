package com.example.gruppe3;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        findViewById(R.id.link1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked_btn("http://www.w-hs.de");
            }
        });

        findViewById(R.id.link2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked_btn("http://www.google.de");
            }
        });


        return true;
    }
    public void clicked_btn(String url)
    {
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        intent.setPackage("com.android.chrome");
        startActivity(intent);
    //});
        //int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        //return super.onOptionsItemSelected(item);
    }

    public void calendar_input (String title, String description, String location, String eventTimeZone, long calID, int start_year, int start_month, int start_date, int start_hour, int start_minute, int end_year, int end_month, int end_date, int end_hour, int end_minute, boolean allDay, boolean weekly)
    {
        //insert methoden aufruf:   calendar_input("Allday test123", "Testeintrag allday", "testlocation", "America/Los_Angeles", 6, 2020, 7, 20, 14, 15, 2020,7,20, 14,45, true, true);
        long startMillis = 0;
        long endMillis = 0;
        int allday=0;
        Calendar beginTime = Calendar.getInstance();
        if(allDay)
        {
            beginTime.set(start_year, (start_month-1), start_date, 0,0);
            allday=1;
        }
        else
        {
            beginTime.set(start_year, (start_month-1), start_date, start_hour, start_minute);
        }
        startMillis = beginTime.getTimeInMillis();

        Calendar endTime = Calendar.getInstance();
        if(allDay)
        {
            endTime.set(end_year, (end_month-1), (end_date+1), 0, 0);
        }
        else
        {
            endTime.set(end_year, (end_month-1), end_date, end_hour, end_minute);
        }
        endMillis = endTime.getTimeInMillis();


        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.TITLE, title);
        values.put(CalendarContract.Events.DESCRIPTION, description);
        values.put(CalendarContract.Events.EVENT_LOCATION, location);
        values.put(CalendarContract.Events.CALENDAR_ID, calID);         // Calendar ID 6 für Events
        values.put(CalendarContract.Events.EVENT_TIMEZONE, eventTimeZone);
        values.put(CalendarContract.Events.ALL_DAY, allday);
        if(weekly)
        {
            values.put(CalendarContract.Events.RRULE, "FREQ=WEEKLY");
        }

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) // App-Permission in den Einstellungen ändern
        {
            return;
        }
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
    }
}
