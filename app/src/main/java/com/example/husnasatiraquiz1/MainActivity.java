package com.example.husnasatiraquiz1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etinputname, etkodeitem, etjmlbarang;
    private RadioGroup rgmembership;
    private Button btotal;
    private TextView tvdiskon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etinputname = findViewById(R.id.etinputname);
        etkodeitem = findViewById(R.id.etkodeitem);
        etjmlbarang = findViewById(R.id.etjmlbarang);
        rgmembership= findViewById(R.id.rgmembership);
        btotal = findViewById(R.id.btotal);
        tvdiskon = findViewById(R.id.tvdiskon);

        btotal.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String customer = etinputname.getText().toString().trim();
        String kodeBarang = etkodeitem.getText().toString().trim();
        String jumlahBarang = etjmlbarang.getText().toString().trim();
        String jenisMember = "";

        int selectedRadioButtonId = rgmembership.getCheckedRadioButtonId();
        if (selectedRadioButtonId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            jenisMember = selectedRadioButton.getText().toString();
        }

        //  total harga
        int hargaBarang = 0;
        switch (kodeBarang) {
            case "SGS":
                hargaBarang = 12999999;
                break;
            case "IPX":
                hargaBarang = 5725300;
                break;
            case "AV4":
                hargaBarang = 1989123;
                break;
            default:
                Toast.makeText(this, "Kode barang tidak valid", Toast.LENGTH_SHORT).show();
                return;
        }

        int totalHarga = Integer.parseInt(jumlahBarang) * hargaBarang;

        // potongan harga utk yang berbelanja lebih dari 10 juta
        int diskon = 0;
        if (totalHarga > 10000000) {
            diskon += 100000;
        }
        switch (jenisMember) {
            case "Gold":
                diskon += (int) (totalHarga * 0.1);
                break;
            case "Silver":
                diskon += (int) (totalHarga * 0.05);
                break;
            case "Normal":
                diskon += (int) (totalHarga * 0.02);
                break;
        }

        // potongan harga asli bagi yang mendapat diskon
        int totalBayar = totalHarga - diskon;

        // eksplisit intent untuk detail item
        Intent intent = new Intent(this, detailActivity.class);
        intent.putExtra("customer", customer);
        intent.putExtra("codeItem", kodeBarang);
        intent.putExtra("jmlItem", jumlahBarang);
        intent.putExtra("membership", jenisMember);
        intent.putExtra("TotalOrder", totalHarga);
        intent.putExtra("discount", diskon);
        intent.putExtra("TotalPembayaran", totalBayar);

        // Memulai aktivitas DetailTransaksiActivity
        startActivity(intent);

    }
}