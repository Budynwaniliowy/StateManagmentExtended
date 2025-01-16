package com.example.statemanagmentextended;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private StanModel viewModel;
    private Switch swMotyw;
    private TextView tvLicznik, tvOpcja;

    public static class StanModel extends ViewModel {
        public int licznik = 0;
        public boolean ciemny = false;
        public boolean opcjaWidoczna = false;

        public void zwiekszLicznik() {
            licznik++;
        }

        public void przelaczMotyw() {
            ciemny = !ciemny;
        }

        public void przelaczOpcje() {
            opcjaWidoczna = !opcjaWidoczna;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(StanModel.class);

        swMotyw = findViewById(R.id.switch2);
        CheckBox cbOpcja = findViewById(R.id.checkBox);
        tvLicznik = findViewById(R.id.textViewCount);
        tvOpcja = findViewById(R.id.textViewOption);
        Button btnLicznik = findViewById(R.id.buttonIncrement);

        aktualizujWidok();

        btnLicznik.setOnClickListener(v -> {
            viewModel.zwiekszLicznik();
            aktualizujLicznik();
        });

        swMotyw.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.przelaczMotyw();
            aktualizujMotyw();
        });

        cbOpcja.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.przelaczOpcje();
            aktualizujOpcje();
        });
    }

    private void aktualizujWidok() {
        aktualizujLicznik();
        aktualizujOpcje();
        aktualizujMotyw();
        swMotyw.setChecked(viewModel.ciemny);
    }

    private void aktualizujLicznik() {
        tvLicznik.setText("Licznik: " + viewModel.licznik);
    }

    private void aktualizujOpcje() {
        tvOpcja.setVisibility(viewModel.opcjaWidoczna ? View.VISIBLE : View.GONE);
    }

    private void aktualizujMotyw() {
        AppCompatDelegate.setDefaultNightMode(
                viewModel.ciemny ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );
        String motyw = viewModel.ciemny ? "Ciemny Motyw" : "Jasny Motyw";
        Toast.makeText(this, motyw, Toast.LENGTH_SHORT).show();
    }
}
