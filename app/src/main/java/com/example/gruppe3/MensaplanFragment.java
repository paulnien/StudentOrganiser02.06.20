package com.example.gruppe3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class MensaplanFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_Mensa_to_Start).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                NavHostFragment.findNavController(MensaplanFragment.this)
                        .navigate(R.id.action_Mensa_to_Start);
            }
        });
// hier folgt jetzt ein Versuch, einen Link in einem extra Fenster zu öffnen.
        // Testebene Start

        /*

        public class MainActivity extends AppCompatActivity {

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.fragment_second);
            }

            public void clicked_btn(View view)
            {
                Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.w-hs.de"));
                //intent.setPackage("com.android.chrome");
                startActivity(intent);
            }


*/



        // Testebene ENDE
        // Diese beiden Klammern waren schon da.
    }
}