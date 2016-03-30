package com.example.app.kupangpublicarea;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class SPBUListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_spbu);

        ArrayList<Phone> list = new ArrayList<Phone>();
        list.add(new Phone("1. SPBU Naikoten 1", "Jl. Soeharto No. 70, Naikoten I, Kupang, NTT"));
        list.add(new Phone("2. SPBU Oepura", "Jl. HR. Horo No. 25, Oepura, Kupang, NTT"));
        list.add(new Phone("3. SPBU Oebufu", "Jl. WJ Lalamentik, Oebobo, Kupang, NTT"));
        list.add(new Phone("4. SPBU Oesapa", "Jl. Piet A Tallo, Oesapa, Kupang, NTT"));
        list.add(new Phone("5. SPBU Pulau Indah", "Jl. Pulau Indah No.54, Oesapa, Kupang, NTT"));
        list.add(new Phone("6. SPBU Pasir Panjang", "Jl. Raya Timor No.128, Pasir Panjang, Kupang, NTT"));


        ListAdapter adapter = new ListAdapter(this, list);

        ListView listView = (ListView) findViewById(R.id.id_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                Toast.makeText(SPBUListActivity.this, "DAFTAR SPBU Kupang",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
