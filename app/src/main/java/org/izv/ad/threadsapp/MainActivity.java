package org.izv.ad.threadsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;

import org.izv.ad.threadsapp.view.ModeloVista;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tvText = findViewById(R.id.tvText);
        ModeloVista modeloVista = new ViewModelProvider(this).get(ModeloVista.class);
        modeloVista.getResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvText.append(s + "\n");
            }
        });
    }
}