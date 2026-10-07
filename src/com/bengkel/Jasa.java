// File: Jasa.java
package com.bengkel;

// Class turunan spesifik (child class) dari ItemLayanan untuk layanan Jasa
public class Jasa extends ItemLayanan {
    // Deklarasi atribut spesifik yang hanya ada di Jasa
    private String kategori;
    private int estimasiWaktu; 

    // Constructor panjang untuk insialisasi penuh dalam satu baris
    public Jasa(String kode, String nama, double harga, String kategori, int estimasiWaktu) {
        // Melanjutkan inisialisasi properti dasar ke constructor parent class
        super(kode, nama, harga);
        // Menyetel sisa atribut yang dimiliki oleh class anak ini
        setKategori(kategori);
        setEstimasiWaktu(estimasiWaktu);
    }

    // Constructor prompt untuk pendaftaran jasa baru via console
    public Jasa() {
        // Trigger constructor interaktif dari parent class terlebih dahulu
        super(); 
        // Mengisi atribut spesifik melalui utility Input
        setKategori(Input.bacaString("Kategori = "));
        setEstimasiWaktu(Input.bacaInt("Estimasi Waktu (menit) = ", 0, Integer.MAX_VALUE));
    }

    // Setter untuk merubah kategori
    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
    // Setter dengan proteksi exception agar menit tidak pernah negatif
    public void setEstimasiWaktu(int estimasiWaktu) {
        if (estimasiWaktu < 0) {
            throw new IllegalArgumentException("Estimasi waktu tidak boleh negatif: " + estimasiWaktu);
        }
        this.estimasiWaktu = estimasiWaktu;
    }
    // Getter untuk membaca isi variabel kategori
    public String getKategori() {
        return kategori;
    }
    // Getter untuk membaca isi variabel estimasiWaktu
    public int getEstimasiWaktu() {
        return estimasiWaktu;
    }

    // Meng-override abstract method untuk mendefinisikan jati diri objek sebagai "Jasa"
    @Override
    public String getJenisItem() {
        return "Jasa";
    }
}