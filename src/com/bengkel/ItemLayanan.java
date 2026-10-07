// File: ItemLayanan.java
package com.bengkel;

// Mendeklarasikan abstract class yang akan diturunkan ke part dan jasa
public abstract class ItemLayanan {
    // Variabel instance bersifat private untuk keamanan data
    private String kode;
    private String nama;
    private double harga;

    // Constructor lengkap dengan tiga parameter untuk bypass input manual
    public ItemLayanan(String kode, String nama, double harga) {
        // Menerapkan nilai menggunakan setter agar tetap melewati validasi
        setKode(kode);
        setNama(nama);
        setHarga(harga);
    }

    // Constructor interaktif yang meminta user mengisi form di terminal
    public ItemLayanan() {
        setKode(Input.bacaString("Kode = "));
        setNama(Input.bacaString("Nama = "));
        setHarga(Input.bacaDouble("Harga = ", 0, Double.MAX_VALUE));
    }

    // Setter sederhana untuk variabel kode
    public void setKode(String kode) {
        this.kode = kode;
    }
    // Setter sederhana untuk variabel nama
    public void setNama(String nama) {
        this.nama = nama;
    }
    // Setter harga dengan penjagaan exception (mencegah harga minus)
    public void setHarga(double harga) {
        if (harga < 0) {
            // Melempar exception spesifik saat aturan dilanggar
            throw new IllegalArgumentException("Harga tidak boleh negatif: " + harga);
        }
        this.harga = harga;
    }

    // Getter untuk menarik data kode item
    public String getKode() {
        return kode;
    }
    // Getter untuk menarik data nama item
    public String getNama() {
        return nama;
    }
    // Getter untuk menarik besaran harga
    public double getHarga() {
        return harga;
    }

    // Deklarasi abstract method yang wajib di-override di sub-class
    public abstract String getJenisItem();
}