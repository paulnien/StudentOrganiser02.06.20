package com.example.gruppe3;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Calendar;

public class KalenderFragment extends Fragment
{

    //Funktion Deklaration
    EditText textfeld_titel;
    EditText textfeldTerminbeschreibung;
    EditText textfeldTerminort;
    CalendarView calendarView;
    Switch swt_daily;
    Switch swt_weekly;
    //variablen CalendarView
    int jahr,monat,tag;
    boolean is_clickedDay=false;
    //variablen Switches
    boolean daily_isChecked=false;
    boolean weekly_isChecked=false;
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_third_previous).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                NavHostFragment.findNavController(KalenderFragment.this)
                        .navigate(R.id.action_ThirdFragment_to_FirstFragment);
            }
        });

        //title,description,location Textfield
        textfeld_titel =(EditText)view.findViewById(R.id.textfeld_titel);
        textfeldTerminbeschreibung =(EditText)view.findViewById(R.id.textfeldTerminbeschreibung);
        textfeldTerminort =(EditText)view.findViewById(R.id.textfeldTerminort);
        //CalendarView

        calendarView=(CalendarView) view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth)
            {
                jahr=year;
                monat=month+1;
                tag=dayOfMonth;
                is_clickedDay=true;
                Log.d("DebugVariablen", "Variablen: "+String.valueOf(jahr)+" "+String.valueOf(monat)+" "+String.valueOf(tag));
            }
        });
        //TimePicker


        //daily_handler
        final Switch swt_daily = (Switch) view.findViewById(R.id.swt_daily);
        swt_daily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
               if(!daily_isChecked)
               {
                   daily_isChecked=true;
               }
               else
               {
                   daily_isChecked=false;
               }
               Log.d("Debug dailyHandler: ",""+daily_isChecked);
            }
        });

        //weekly_handler
        final Switch swt_weekly = (Switch) view.findViewById(R.id.swt_weekly);
        swt_weekly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(!weekly_isChecked)
                {
                    weekly_isChecked=true;
                }
                else
                {
                    weekly_isChecked=false;
                }
                Log.d("Debug weeklyHandler: ",""+weekly_isChecked);
            }
        });

        //Event erstellen handleButton
        view.findViewById(R.id.createNewEvent).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(is_clickedDay)
                {
                    calendar_input(textfeld_titel.getText().toString(), textfeldTerminbeschreibung.getText().toString(), textfeldTerminort.getText().toString(), "America/Los_Angeles", 6, jahr, monat, tag, 14, 15, jahr, monat, tag, 14, 45, daily_isChecked, weekly_isChecked);
                    is_clickedDay=false;
                    textfeld_titel.getText().clear();
                    textfeldTerminbeschreibung.getText().clear();
                    textfeldTerminort.getText().clear();
                    swt_daily.setChecked(false);
                    swt_weekly.setChecked(false);
                }
            }
        });
    }

    public void calendar_input(String title, String description, String location, String eventTimeZone, long calID,int start_year, int start_month, int start_date, int start_hour, int start_minute, int end_year, int end_month, int end_date, int end_hour, int end_minute, boolean allDay, boolean weekly)
    {
        //insert methoden aufruf:   calendar_input("Allday test123", "Testeintrag allday", "testlocation", "America/Los_Angeles", 6, 2020, 7, 20, 14, 15, 2020,7,20, 14,45, true, true);

        long startMillis;
        long endMillis;
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

        ContentResolver cr = getContext().getContentResolver();
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

        if (ActivityCompat.checkSelfPermission(getContext().getApplicationContext(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) // App-Permission in den Einstellungen ändern
        {
            return;
        }
        Log.d("Debug insert-method: ","Vor insert Command: \r\n title: "+title+"\r\n description: "+description+"\r\n location: "+location+"\r\n eventTimeZone: "+eventTimeZone+"\r\n calendarID: "+calID+"\r\n Jahr: "+start_year+"\r\n Monat: "+start_month+"\r\n Tag: "+start_date+"\r\n Stunde: "+start_hour+" \r\n Minute: "+start_minute+"\r\n AllDayEvent: "+allDay+"\r\n weekly: "+weekly);
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
    }
}
