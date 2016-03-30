package com.example.app.kupangpublicarea;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        Button petaSPBU = (Button)findViewById(R.id.button);
        Button daftarSPBU = (Button)findViewById(R.id.button2);
        Button infoAplikasi = (Button)findViewById(R.id.button3);
        Button exit = (Button)findViewById(R.id.button4);

        petaSPBU.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(i);
            }
        });

        daftarSPBU.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(getApplicationContext(),SPBUListActivity.class);
                startActivity(i);
            }
        });

        infoAplikasi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(getApplicationContext(),AboutActivity.class);
                startActivity(i);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                moveTaskToBack(true);
            }
        });

    }
}
