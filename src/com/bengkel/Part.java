// File: Part.java
package com.bengkel;

// Mendirikan class Part sebagai child class turunan dari parent ItemLayanan
public class Part extends ItemLayanan {
    // Variabel enkapsulasi private khusus untuk suku cadang
    private String merk;
    private int stok;

    // Constructor eksplisit untuk pengisian data via argumen lengkap
    public Part(String kode, String nama, double harga, String merk, int stok) {
        // Mewariskan pengisian atribut dasar ke super class
        super(kode, nama, harga);
        // Mengalokasikan nilai parameter yang tersisa ke setternya masing-masing
        setMerk(merk);
        setStok(stok);
    }

    // Constructor input terminal jika tidak ada nilai yang di-pass
    public Part() {
        // Menjalankan input loop atribut parent
        super(); 
        // Melengkapi input loop untuk atribut child
        setMerk(Input.bacaString("Merk = "));
        setStok(Input.bacaInt("Stok = ", 0, Integer.MAX_VALUE));
    }

    // Setup mutator (setter) untuk field brand/merk part
    public void setMerk(String merk) {
        this.merk = merk;
    }
    // Setup setter stok yang memiliki penjagaan exception anti nilai negatif
    public void setStok(int stok) {
        if (stok < 0) {
            throw new IllegalArgumentException("Stok tidak boleh negatif: " + stok);
        }
        this.stok = stok;
    }
    // Accessor method (getter) pembaca nama merk
    public String getMerk() {
        return merk;
    }
    // Accessor method untuk membaca jumlah inventory tersisa
    public int getStok() {
        return stok;
    }

    // Method fungsional untuk mengurasi jumlah stok barang seiring transaksi
    public void kurangiStok(int jumlah) {
        // Memastikan barang yang dikurangi logis (minimal 1)
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah harus lebih dari 0");
        }
        // Exception ditarik jika perintaan lebih besar dari jumlah barang gudang
        if (jumlah > stok) {
            throw new IllegalArgumentException("Stok " + getNama() + " tidak cukup (sisa " + stok + ")");
        }
        // Kalkulasi mutasi pengurangan barang yang lolos cek keamanan
        stok = stok - jumlah;
    }

    // Overriding kewajiban klasifikasi dari abstract item parent
    @Override
    public String getJenisItem() {
        return "Part";
    }
}