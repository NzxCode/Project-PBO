// File: Kendaraan.java
package com.bengkel;

// Mendeklarasikan Kendaraan sebagai abstract class yang bertindak sebagai parent
public abstract class Kendaraan {
    // Koleksi properti private yang menjadi kerangka dasar seluruh kendaraan
    private String noPolisi;
    private String merk;
    private String tipe;
    private int tahun;
    private String warna;
    private int km;
    private String noRangka;
    private String noMesin;

    // Constructor lengkap untuk inject data kendaraan secara terprogram
    public Kendaraan(String noPolisi, String merk, String tipe, int tahun,
                     String warna, int km, String noRangka, String noMesin) {
        // Semua proses inisialisasi diarahkan ke fungsi setter demi validasi
        setNoPolisi(noPolisi);
        setMerk(merk);
        setTipe(tipe);
        setTahun(tahun);
        setWarna(warna);
        setKm(km);
        setNoRangka(noRangka);
        setNoMesin(noMesin);
    }

    // Constructor kedua (default) untuk setup objek interaktif dari pengguna terminal
    public Kendaraan() {
        // Bertanya dan melakukan set untuk setiap atribut dasar
        setNoPolisi(Input.bacaString("No Polisi = "));
        setMerk(Input.bacaString("Merk = "));
        setTipe(Input.bacaString("Tipe = "));
        setTahun(Input.bacaInt("Tahun = ", 1900, 2100));
        setWarna(Input.bacaString("Warna = "));
        setKm(Input.bacaInt("KM = ", 0, Integer.MAX_VALUE));
        setNoRangka(Input.bacaString("No Rangka = "));
        setNoMesin(Input.bacaString("No Mesin = "));
    }

    // Setter atribut noPolisi
    public void setNoPolisi(String noPolisi) {
        this.noPolisi = noPolisi;
    }
    // Setter atribut merk
    public void setMerk(String merk) {
        this.merk = merk;
    }
    // Setter atribut tipe
    public void setTipe(String tipe) {
        this.tipe = tipe;
    }
    // Setter untuk tahun produksi dengan penolakan data jika tidak wajar
    public void setTahun(int tahun) {
        if (tahun < 1900) {
            throw new IllegalArgumentException("Tahun tidak valid: " + tahun);
        }
        this.tahun = tahun;
    }
    // Setter atribut warna
    public void setWarna(String warna) {
        this.warna = warna;
    }
    // Setter jarak tempuh dengan blokade data di bawah angka nol
    public void setKm(int km) {
        if (km < 0) {
            throw new IllegalArgumentException("KM tidak boleh negatif: " + km);
        }
        this.km = km;
    }
    // Setter atribut noRangka
    public void setNoRangka(String noRangka) {
        this.noRangka = noRangka;
    }
    // Setter atribut noMesin
    public void setNoMesin(String noMesin) {
        this.noMesin = noMesin;
    }

    // Rentetan public getter untuk mengakses masing-masing properties objek
    public String getNoPolisi() {
        return noPolisi;
    }
    public String getMerk() {
        return merk;
    }
    public String getTipe() {
        return tipe;
    }
    public int getTahun() {
        return tahun;
    }
    public String getWarna() {
        return warna;
    }
    public int getKm() {
        return km;
    }
    public String getNoRangka() {
        return noRangka;
    }
    public String getNoMesin() {
        return noMesin;
    }

    // Abstract method agar child class merumuskan sendiri jenis kendaraannya
    public abstract String getJenisKendaraan();
}