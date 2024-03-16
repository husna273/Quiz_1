package com.example.husnasatiraquiz1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.husnasatiraquiz1.R;

public class detailActivity extends AppCompatActivity {

    private TextView tvcustomer, tvNamaBarang, tvKodeBarang, tvJumlahBarang,
            tvTotalHarga, tvDiskon, tvTotalBayar, tvWelcome, tvMembershipType;
    private String[] codeofName = {"Samsung Galaxy S", "Iphone X", "Asus Vivobook 14"};
    private Button btnShare;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvcustomer = findViewById(R.id.tvcustomer);
        tvNamaBarang = findViewById(R.id.tvNamaBarang);
        tvKodeBarang = findViewById(R.id.tvKodeBarang);
        tvJumlahBarang = findViewById(R.id.tvJumlahBarang);
        tvTotalHarga = findViewById(R.id.tvTotalHarga);
        tvDiskon = findViewById(R.id.tvDiskon);
        tvTotalBayar = findViewById(R.id.tvTotalBayar);
        tvWelcome = findViewById(R.id.tvWelcome);
        tvMembershipType = findViewById(R.id.tvMembershipType);
        btnShare = findViewById(R.id.btnShare);

        // Data dari Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String customer = extras.getString("customer");
            String codeItem = extras.getString("codeItem");
//            String namaBarang = extras.getString("nameofItem");
            String jumlahBarang = extras.getString("jmlItem");
            int totalHarga = extras.getInt("TotalOrder");
            int diskon = extras.getInt("Discount");
            String jenisMember = extras.getString("membership");
            int totalBayar = extras.getInt("TotalPembayaran");

            // Nama barang berdasarkan kode barang
            String namaBarang = "";
            switch (codeItem) {
                case "SGS":
                    namaBarang = codeofName[0];
                    break;
                case "IPX":
                    namaBarang = codeofName[1];
                    break;
                case "AV4":
                    namaBarang = codeofName[2];
                    break;
            }

            tvWelcome.setText("Hai " + customer + " Welcome to the STORE"); // Menampilkan ucapan selamat datang
            tvMembershipType.setText("Membership : " + jenisMember); // Menampilkan jenis keanggotaan
            tvNamaBarang.setText("name of Item : " + namaBarang); // Menampilkan nama barang
            tvKodeBarang.setText("Code Item : " + codeItem);
            tvJumlahBarang.setText("Jumlah Item : " + jumlahBarang);
            tvTotalHarga.setText("Total order : Rp." + totalHarga);
            tvDiskon.setText("Discount : Rp." + diskon);
            tvTotalBayar.setText("Total pembayaran : Rp." + totalBayar);

            //implisit intent
            String finalNamaBarang = namaBarang;
            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareData(customer, finalNamaBarang, codeItem, jumlahBarang, jenisMember, totalHarga, diskon, totalBayar);
                }
            });
        }
    }

    private void shareData(String customer, String namaBarang, String kodeBarang, String jumlahBarang,
                           String jenisMember, int totalHarga, int diskon, int totalBayar) {
        Intent order = new Intent(Intent.ACTION_SEND);
        order.setType("text/plain");
        order.putExtra(Intent.EXTRA_SUBJECT, "Detail belanjaan Anda");
        String message = "Customer: " + customer + "\n" +
                "name of Item: " + namaBarang + "\n" +
                "Code Item: " + kodeBarang + "\n" +
                "Jumlah Item: " + jumlahBarang + "\n" +
                "Membership: " + jenisMember + "\n" +
                "Total Order: Rp. " + totalHarga + "\n" +
                "Discount: Rp." + diskon + "\n" +
                "Total Pembayaran: Rp. " + totalBayar;
        order.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(order, "Pilih aplikasi untuk mengirim"));
    }
}